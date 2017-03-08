package com.greco.caller.plugin.main;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;

import com.greco.caller.plugin.model.JenkinsCallerModel;

public abstract class AbstractJenkinsMojo
  extends AbstractMojo
{
  @Component
  protected MavenProject project;
  @Component
  protected MavenProjectHelper projectHelper;
  @Parameter(defaultValue="${plugin.artifacts}")
  protected List<Artifact> pluginArtifacts;
  @Parameter(defaultValue="localhost")
  protected String host;
  @Parameter
  protected String port;
  @Parameter
  protected String connectorType;
  @Parameter
  protected String server;
  @Parameter
  protected String user;
  @Parameter
  protected String password;
  @Parameter
  private String protocolo;
  @Parameter(required=true)
  private String job;
  @Parameter(required=true)
  private String buildToken;
  @Parameter
  private String jenkinsHome;
  
  protected Set<JenkinsCallerModel> getJenkinsCallerModels()
  {
   
    JenkinsCallerModel model = getJenkinsCallerModel();
    if (!model.isValid())
    {
      getLog().info("los parametros de entrada no son validos'");
      return null;
    }
    getLog().info("unico target: " + model.getHost());
    Set<JenkinsCallerModel> models = new HashSet<JenkinsCallerModel>(1);
    models.add(model);
    return models;
  }
  
  protected JenkinsCallerModel getJenkinsCallerModel()
  {
	  JenkinsCallerModel model = new JenkinsCallerModel().setBuildToken(this.buildToken).setConnectorType(this.connectorType).setHost(this.host).setJenkinsHome(this.jenkinsHome).setJob(this.job).setPort(this.port).setProtocolo(this.protocolo).setPassword(this.password).setUser(this.user);
    
    return model;
  }


}
