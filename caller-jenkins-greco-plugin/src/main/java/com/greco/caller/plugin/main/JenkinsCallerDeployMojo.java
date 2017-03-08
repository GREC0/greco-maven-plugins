package com.greco.caller.plugin.main;


import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.ExceptionUtils;
import org.codehaus.plexus.util.StringUtils;

import com.greco.caller.plugin.impl.JenkinsServiceScriptImpl;
import com.greco.caller.plugin.model.JenkinsCallerModel;

@Mojo(name="deploy", defaultPhase=LifecyclePhase.PRE_INTEGRATION_TEST, requiresDirectInvocation=true, threadSafe=true)
public class JenkinsCallerDeployMojo
  extends AbstractJenkinsMojo
{
  @Parameter
  protected String parallel;
  
  public void execute()
    throws MojoExecutionException, MojoFailureException
  {
    getLog().info("caller-jenkins-greco-plugin - calling Jenkins");
    Set<JenkinsCallerModel> models = getJenkinsCallerModels();
    if ((null == models) || (models.isEmpty()))
    {
      getLog().info("[SKIPPED DEPLOYMENT] empty target server configured, please check your configuration");
      return;
    }
    boolean parallelDeploy = StringUtils.isEmpty(this.parallel) ? false : models.size() > 1 ? true : Boolean.valueOf(this.parallel).booleanValue();
    ExecutorService executor;
    if (parallelDeploy)
    {
      executor = Executors.newFixedThreadPool(models.size());
      for (final JenkinsCallerModel model : models) {
        executor.execute(new Runnable()
        {
          public void run()
          {
            JenkinsCallerDeployMojo.this.execute(model);
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
      for (JenkinsCallerModel model : models) {
        execute(model);
      }
    }
  }
  
  private void execute(JenkinsCallerModel model)
  {
	  
	getLog().info("============================================================");
	getLog().info("[MODEL CALLER JENKINS GRECO PLUGIN]: ");
	getLog().info("[model.getJenkinsHome()]: "+model.getJenkinsHome());
	getLog().info("[model.getHost()]: "+model.getHost());
	getLog().info("[model.getJob()]: "+model.getJob());
	getLog().info("[model.getPort()]: "+model.getPort());
	getLog().info("[model.getBuildToken()]: "+model.getBuildToken());
	getLog().info("[model.getProtocolo()]: "+model.getProtocolo());
	getLog().info("[model.getUser()]: "+model.getUser());
	getLog().info("[model.getPassword()]: "+model.getPassword());


    getLog().info("============================================================");
    try
    {
      getLog().info("======================    Calling jenkins job    ========================");
      new JenkinsServiceScriptImpl(model, this.project.getBuild().getDirectory()).Caller();
      getLog().info("====================    post-steps    ======================");
    }
    catch (RuntimeException e)
    {
 
      getLog().error("##############  Exception occurred during calling to Jenkins  ###############");
      getLog().error(ExceptionUtils.getFullStackTrace(e));
    }
    catch (Throwable t)
    {
  
      getLog().error("##############  Exception occurred during calling to Jenkins  ###############");
      getLog().error(ExceptionUtils.getFullStackTrace(t));
    }
  }
  

}
