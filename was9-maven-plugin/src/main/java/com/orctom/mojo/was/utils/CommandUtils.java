package com.orctom.mojo.was.utils;


import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.orctom.mojo.was.model.WebSphereModel;
import com.orctom.mojo.was.model.WebSphereServiceException;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.StreamConsumer;

public class CommandUtils
{
  private static final String TIMESTAMP_FORMAT = "yyyyMMdd-HHmmss-SSS";
  
  public static String getWorkingDir(String targetFolder, String templateExt)
  {
    return targetFolder + "/" + "was-maven-plugin" + "/" + templateExt + "/";
  }
  
  public static File getExecutable(String wasHome, String name)
  {
    if (StringUtils.isBlank(wasHome)) {
      throw new WebSphereServiceException("WAS_HOME no esta setteado");
    }
    File binDir = new File(wasHome, "bin");
    System.out.println(binDir.getAbsolutePath());
    File[] candidates = binDir.listFiles(new FilenameFilter()
    {
      public boolean accept(File dir, String fileName)
      {
    	  System.out.println(fileName);
    	  return fileName.startsWith(name) && (fileName.endsWith("bat") || fileName.endsWith("sh"));
      }
    });
    if (candidates.length != 1) {
      throw new WebSphereServiceException("no se encuentra el archivo " + name + "[.sh|.bat], candidates: " + Arrays.toString(candidates));
    }
    File executable = candidates[0];
    System.out.println(name + " localizacion: " + executable.getAbsolutePath());
    
    return executable;
  }
  
  public static File getBuildScript(String task, String template, WebSphereModel model, String workingDir, String ext)
    throws IOException
  {
    if (StringUtils.isNotEmpty(model.getScript()))
    {
      if (model.getScript().startsWith("/")) {
        return new File(model.getScript());
      }
      return new File(workingDir, model.getScript());
    }
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile(template);
    
    StringBuilder buildFile = new StringBuilder(50);
    buildFile.append(task);
    if (StringUtils.isNotBlank(model.getHost())) {
      buildFile.append("-").append(model.getHost());
    }
    if (StringUtils.isNotBlank(model.getApplicationName())) {
      buildFile.append("-").append(model.getApplicationName());
    }
    buildFile.append("-").append(getTimestampString()).append(".").append(ext);
    
    File buildScriptFile = new File(workingDir, buildFile.toString());
    buildScriptFile.getParentFile().mkdirs();
    Writer writer = new FileWriter(buildScriptFile);
    mustache.execute(writer, model.getProperties()).flush();
    
    return buildScriptFile;
  }
  
  public static void executeCommand(Commandline commandline, StreamConsumer outConsumer, StreamConsumer errorConsumer, boolean isVerbose)
    throws CommandLineException
  {
    if (isVerbose) {
      System.out.println("ejecutando  comando :\n" + StringUtils.join(commandline.getShellCommandline(), " "));
    }
    int returnCode = CommandLineUtils.executeCommandLine(commandline, outConsumer, errorConsumer, 1800);
    
    String msg = "Codigo de Retorno: " + returnCode;
    if (returnCode != 0) {
      throw new WebSphereServiceException(msg);
    }
    System.out.println(msg);
  }
  
  public static String getTimestampString()
  {
    return new SimpleDateFormat("yyyyMMdd-HHmmss-SSS").format(new Date());
  }
}
