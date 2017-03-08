package com.orctom.mojo.was;


import com.orctom.mojo.was.impl.WebSphereServiceScriptImpl;
import com.orctom.mojo.was.model.WebSphereModel;
import com.orctom.mojo.was.model.WebSphereServiceException;
import com.orctom.mojo.was.utils.AntTaskUtils;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.util.ExceptionUtils;
import org.codehaus.plexus.util.StringUtils;

@Mojo(name="deploy", defaultPhase=LifecyclePhase.PRE_INTEGRATION_TEST, requiresDirectInvocation=true, threadSafe=true)
public class WASDeployMojo
  extends AbstractWASMojo
{
  @Parameter
  protected String parallel;
  
  public void execute()
    throws MojoExecutionException, MojoFailureException
  {
    getLog().info("was-maven-plugin - deploy by --@grec0");
    Set<WebSphereModel> models = getWebSphereModels();
    if ((null == models) || (models.isEmpty()))
    {
      getLog().info("[SALTANDO DEPLOYMENT] target server VACIO, revisa la configuracion");
      return;
    }
    boolean parallelDeploy = StringUtils.isEmpty(this.parallel) ? false : models.size() > 1 ? true : Boolean.valueOf(this.parallel).booleanValue();
    ExecutorService executor;
    if (parallelDeploy)
    {
      executor = Executors.newFixedThreadPool(models.size());
      for (final WebSphereModel model : models) {
        executor.execute(new Runnable()
        {
          public void run()
          {
            WASDeployMojo.this.execute(model);
          }
        });
      }
      executor.shutdown();
      try
      {
        executor.awaitTermination(20L, TimeUnit.MINUTES);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      for (WebSphereModel model : models) {
        execute(model);
      }
    }
  }
  
  private void execute(WebSphereModel model)
  {
	  
	getLog().info("============================================================");
	getLog().info("[MODELO CONFIGURACION WAS 9.0]: ");
	getLog().info("============================================================");
	getLog().info("Nombre Aplicacion: "+model.getApplicationName());
	getLog().info("============================================================");
	getLog().info("Cell: "+model.getCell());
	getLog().info("============================================================");
	getLog().info("Cluster: "+model.getCluster());
	getLog().info("============================================================");
	getLog().info("Tipo Conexion: "+model.getConnectorType());
	getLog().info("============================================================");
	getLog().info("Context Root: "+model.getContextRoot());
	getLog().info("============================================================");
	getLog().info("Dir Was Home:  "+model.getWasHome());
	getLog().info("============================================================");
	getLog().info("Host: "+model.getHost());
	getLog().info("============================================================");
	getLog().info("Virtual: "+model.getVirtualHost());
	getLog().info("============================================================");
	getLog().info("[model.getPort()]: "+model.getPort());
	getLog().info("============================================================");
	getLog().info("Nombre Perfil: "+model.getProfileName());
	getLog().info("============================================================");
	getLog().info("Nodo "+model.getNode());
	getLog().info("============================================================");
	getLog().info("Servidor "+model.getServer());
	getLog().info("============================================================");
	getLog().info("Librerias: "+model.getSharedLibs());
	getLog().info("============================================================");
	getLog().info("Conexiones JNDI: "+model.getSharedDatasources());
	getLog().info("============================================================");
	getLog().info("directorio / Paquete: "+model.getPackageFile());
	getLog().info("============================================================");
	getLog().info("Script: "+model.getScript());
	getLog().info("============================================================");
	getLog().info("Parametros Script: "+model.getScriptArgs());
	getLog().info("============================================================");
	getLog().info("Usuario : "+model.getUser());
	getLog().info("============================================================");
	getLog().info("Password decrypt BASE64: "+model.getPassword());
	getLog().info("============================================================");
	getLog().info("============================================================");
	getLog().info("============================================================");
    getLog().info("============================================================");
    getLog().info("[DEPLOY] " + model.getHost() + " " + model.getApplicationName() +" suerte con  el despliegue!! :P --@grec0");
    getLog().info("============================================================");
    getLog().info("============================================================");
    getLog().info("============================================================");
    try
    {
      getLog().info("====================    pre-steps    =======================");
      executeAntTasks(model, this.preSteps);
      getLog().info("======================    deploy    ========================");
      new WebSphereServiceScriptImpl(model, this.project.getBuild().getDirectory()).deploy();
      getLog().info("====================    post-steps    ======================");
      executeAntTasks(model, this.postSteps);
    }
    catch (RuntimeException e)
    {
      if (this.failOnError) {
        throw e;
      }
      getLog().error("##############  Exception durante del despliegue remoto a Websphere 9.0  ###############");
      getLog().error(ExceptionUtils.getFullStackTrace(e));
    }
    catch (Throwable t)
    {
      if (this.failOnError) {
        throw new WebSphereServiceException(t);
      }
      getLog().error("##############  Exception durante del despliegue remoto a Websphere 9.0    ###############");
      getLog().error(ExceptionUtils.getFullStackTrace(t));
    }
  }
  
  private void executeAntTasks(WebSphereModel model, PlexusConfiguration[] targets)
    throws IOException, MojoExecutionException
  {
    if ((null == targets) || (0 == targets.length))
    {
      getLog().info("Saltando, no configurado.");
      return;
    }
    for (PlexusConfiguration target : targets) {
      AntTaskUtils.execute(model, target, this.project, this.projectHelper, this.pluginArtifacts, getLog());
    }
  }
}
