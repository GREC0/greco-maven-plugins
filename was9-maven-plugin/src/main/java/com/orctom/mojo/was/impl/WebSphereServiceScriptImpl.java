package com.orctom.mojo.was.impl;

import com.orctom.mojo.was.Constants;
import com.orctom.mojo.was.model.WebSphereModel;
import com.orctom.mojo.was.model.WebSphereServiceException;
import com.orctom.mojo.was.service.IWebSphereService;
import com.orctom.mojo.was.utils.CommandUtils;

import java.io.File;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.cli.CommandLineTimeOutException;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.StreamConsumer;

public class WebSphereServiceScriptImpl
  implements IWebSphereService
{
  private WebSphereModel model;
  private String workingDir;
  private static final String TEMPLATE = "jython/websphere.py";
  private static final String TEMPLATE_WAR_PY = "jython/websphere_war.py";
  private static final String TEMPLATE_AXIS2_PY = "jython/websphere_axis2.py";
  private static final String TEMPLATE_EXT = "py";
  
  public WebSphereServiceScriptImpl(WebSphereModel model, String targetDir)
  {
    System.out.println("Using Jython 2.1");
    this.model = model;
    this.workingDir = CommandUtils.getWorkingDir(targetDir, "py");
  }
  
  public void restartServer()
  {
    execute("restartServer");
  }
  
  public void startServer()
  {
    execute("startServer");
  }
  
  public void stopServer()
  {
    execute("stopServer");
  }
  
  public void installApplication()
  {
    execute("installApplication");
  }
  
  public void uninstallApplication()
  {
    execute("uninstallApplication");
  }
  
  public void startApplication()
  {
    execute("startApplication");
  }
  
  public void stopApplication()
  {
    execute("stopApplication");
  }
  
  public void deploy()
  {
    execute("deploy");
  }
  
  private void execute(String task)
  {
    try
    {
    	File buildScript =null;
    	
    if(this.model.getType()!=null && !this.model.getType().equals("")){
    	
    	if(this.model.getType().equals(Constants.TYPE_NO_WS)){
    		System.out.println("tipo  de aplicacion sin Web Service");
    		buildScript = CommandUtils.getBuildScript(task, TEMPLATE_WAR_PY, this.model, this.workingDir, TEMPLATE_EXT);
    	}else if(this.model.getType().equals(Constants.TYPE_WS)){
    		System.out.println("tipo de aplicacion Web Service Axis2 1.6");
    		buildScript = CommandUtils.getBuildScript(task, TEMPLATE_AXIS2_PY, this.model, this.workingDir, TEMPLATE_EXT);
    	}else{
    		System.out.println("tipo de aplicacion no definido ejecutamos el script por defecto");
    		buildScript = CommandUtils.getBuildScript(task, TEMPLATE, this.model, this.workingDir, TEMPLATE_EXT);
    	}
      
    }else{
    	throw new WebSphereServiceException("No se reconoce el parametro type como uno definido : this.model.getType() = " + this.model.getType());
    }
    
    if(buildScript==null){
    	throw new WebSphereServiceException("el script template formado por el parametro type a generado un script NULL ");
    }
    
      Commandline commandLine = new Commandline();
      commandLine.setExecutable(CommandUtils.getExecutable(this.model.getWasHome(), "wsadmin").getAbsolutePath());
      commandLine.setWorkingDirectory(this.workingDir);
      commandLine.createArg().setLine("-conntype " + this.model.getConnectorType());
      commandLine.createArg().setLine("-host " + this.model.getHost());
      commandLine.createArg().setLine("-port " + this.model.getPort());
      if (StringUtils.isNotBlank(this.model.getUser()))
      {
        commandLine.createArg().setLine("-user " + this.model.getUser());
        
        if (StringUtils.isNotBlank(this.model.getPassword())) {
          commandLine.createArg().setLine("-password " + this.model.getPassword());
        }
        
      }
      
      // WAS 9.0 BY GRECO
      if (StringUtils.isNotBlank(this.model.getProfileName()))
      {
    	  commandLine.createArg().setLine("-profileName " + this.model.getProfileName());
      }
      commandLine.createArg().setLine("-lang jython");
      commandLine.createArg().setLine("-tracefile " + buildScript + ".trace");
      commandLine.createArg().setLine("-appendtrace true");
      commandLine.createArg().setLine("-f " + buildScript.getAbsolutePath());
      if (StringUtils.isNotEmpty(this.model.getScript())) {
        commandLine.createArg().setLine(this.model.getScriptArgs());
      } else {
        commandLine.createArg().setLine("-o " + task);
      }
      StringStreamConsumer outConsumer = new StringStreamConsumer();
      StringStreamConsumer errConsumer = new StringStreamConsumer();
      CommandUtils.executeCommand(commandLine, outConsumer, errConsumer, this.model.isVerbose());
      
      FileUtils.fileWrite(new File(buildScript + ".log"), outConsumer.getOutput());
      String error = errConsumer.getOutput();
      if (StringUtils.isNotEmpty(error)) {
        System.err.println(error);
      }
    }
    catch (CommandLineTimeOutException e)
    {
      throw new WebSphereServiceException("FALLO AL EJECUTAR LA TAREA " + task + "\n\tASEGURATE DE QUE WAS en remoto prot. SOAP o EL Deployment Manager estan ejecutandose.", e);
    }
    catch (Exception e)
    {
      throw new WebSphereServiceException("FALLO AL EJECUTAR LA TAREA : " + task, e);
    }
  }
  
  class StringStreamConsumer
    implements StreamConsumer
  {
    private String ls = System.getProperty("line.separator");
    private StringBuffer string = new StringBuffer();
    
    StringStreamConsumer() {}
    
    public void consumeLine(String line)
    {
      this.string.append(line).append(this.ls);
      System.out.println(line);
    }
    
    public String getOutput()
    {
      return this.string.toString();
    }
  }
}
