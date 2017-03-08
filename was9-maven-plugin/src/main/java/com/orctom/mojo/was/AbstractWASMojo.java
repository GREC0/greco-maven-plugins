package com.orctom.mojo.was;


import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.codehaus.plexus.configuration.PlexusConfiguration;
import org.codehaus.plexus.util.StringUtils;

import com.orctom.mojo.was.model.WebSphereModel;
import com.orctom.mojo.was.utils.PropertiesUtils;

public abstract class AbstractWASMojo
  extends AbstractMojo
{
  @Component
  protected MavenProject project;
  @Component
  protected MavenProjectHelper projectHelper;
  @Parameter(defaultValue="${plugin.artifacts}")
  protected List<Artifact> pluginArtifacts;
  @Parameter(defaultValue="${project.basedir}/was-maven-plugin.properties")
  protected File deploymentsPropertyFile;
  @Parameter(required=true)
  protected String wasHome;
  @Parameter(defaultValue="${project.build.finalName}")
  protected String applicationName;
  @Parameter(defaultValue="localhost")
  protected String host;
  @Parameter
  protected String port;
  @Parameter
  protected String connectorType;
  @Parameter
  protected String cluster;
  @Parameter
  protected String cell;
  @Parameter
  protected String node;
  @Parameter
  protected String server;
  @Parameter
  protected String virtualHost;
  @Parameter
  protected String user;
  @Parameter
  protected String password;
  @Parameter
  protected String contextRoot;
  @Parameter
  protected String sharedLibs;
  @Parameter
  protected String sharedDatasources;// BY GRECO
  @Parameter
  protected boolean parentLast;
  @Parameter
  protected boolean webModuleParentLast;
  @Parameter(defaultValue="${project.artifact.file}")
  protected File packageFile;
  @Parameter(defaultValue="false")
  protected boolean failOnError;
  @Parameter
  protected String script;
  @Parameter
  protected String scriptArgs;
  @Parameter
  protected boolean verbose;
  @Parameter
  protected PlexusConfiguration[] preSteps;
  @Parameter
  protected PlexusConfiguration[] postSteps;
  @Parameter
  protected String profileName; // BY GRECO
  
  @Parameter(defaultValue="nows")
  protected String type; // BY GRECO
  
  protected Set<WebSphereModel> getWebSphereModels()
  {
    String deployTargets = System.getProperty("deploy_targets");
    if (StringUtils.isNotBlank(deployTargets))
    {
      if ((null != this.deploymentsPropertyFile) && (this.deploymentsPropertyFile.exists()))
      {
        this.project.getBasedir();
        Map<String, Properties> propertiesMap = PropertiesUtils.loadSectionedProperties(this.deploymentsPropertyFile, getProjectProperties());
        if (propertiesMap.size() >= 1)
        {
          getLog().info("Multi targets: " + deployTargets);
          return getWebSphereModels(deployTargets, propertiesMap);
        }
      }
      if (null == this.deploymentsPropertyFile) {
        getLog().info("Property config file: " + this.deploymentsPropertyFile + " not configured.");
      }
      if (!this.deploymentsPropertyFile.exists()) {
        getLog().info("Property config file: " + this.deploymentsPropertyFile + " doesn't exist.");
      }
      getLog().info("Single target not properly configured.");
      return null;
    }
    WebSphereModel model = getWebSphereModel();
    if (!model.isValid())
    {
      getLog().info("Single target not properly configured. Missing 'cell' or 'cluster' or 'server' or 'node'");
      return null;
    }
    getLog().info("Single target: " + model.getHost());
    Set<WebSphereModel> models = new HashSet<WebSphereModel>(1);
    models.add(model);
    return models;
  }
  
  protected WebSphereModel getWebSphereModel()
  {
    WebSphereModel model = new WebSphereModel().setWasHome(this.wasHome).setApplicationName(this.applicationName).setHost(this.host).setPort(this.port).setConnectorType(this.connectorType).setCluster(this.cluster).setCell(this.cell).setNode(this.node).setServer(this.server).setVirtualHost(this.virtualHost).setContextRoot(this.contextRoot).setSharedLibs(this.sharedLibs).setSharedDatasources(this.sharedDatasources).setParentLast(this.parentLast).setWebModuleParentLast(this.webModuleParentLast).setUser(this.user).setPassword(this.password).setPackageFile(this.packageFile.getAbsolutePath()).setScript(this.script).setScriptArgs(this.scriptArgs).setFailOnError(this.failOnError).setVerbose(this.verbose).setProfileName(this.profileName).setType(this.type);
    
    model.setProperties(getProjectProperties());
    
    return model;
  }
  
  protected Set<WebSphereModel> getWebSphereModels(String deployTargetStr, Map<String, Properties> propertiesMap)
  {
    Set<String> deployTargets = new HashSet<String>();
    Collections.addAll(deployTargets, StringUtils.split(deployTargetStr, ","));
    
    Set<WebSphereModel> models = new HashSet<WebSphereModel>();
    for (String deployTarget : deployTargets)
    {
      Properties props = (Properties)propertiesMap.get(deployTarget);
      if ((null == props) || (props.isEmpty()))
      {
        getLog().info("[SE SALTA LA CONFIGURACION] " + deployTarget + ", no hay un archivo de configuracion de properties");
      }
      else
      {
        updateApplicationNameWithSuffix(props);
        
        WebSphereModel model = new WebSphereModel().setWasHome(this.wasHome).setApplicationName(getPropertyValue("applicationName", props)).setHost(getPropertyValue("host", props)).setPort(getPropertyValue("port", props)).setConnectorType(getPropertyValue("connectorType", props)).setCluster(getPropertyValue("cluster", props)).setCell(getPropertyValue("cell", props)).setNode(getPropertyValue("node", props)).setServer(getPropertyValue("server", props)).setVirtualHost(getPropertyValue("virtualHost", props)).setContextRoot(getPropertyValue("contextRoot", props)).setSharedLibs(getPropertyValue("sharedLibs", props)).setSharedDatasources(getPropertyValue("sharedDatasources", props)).setParentLast(Boolean.valueOf(getPropertyValue("parentLast", props)).booleanValue()).setWebModuleParentLast(Boolean.valueOf(getPropertyValue("webModuleParentLast", props)).booleanValue()).setUser(getPropertyValue("user", props)).setPassword(getPropertyValue("password", props)).setPackageFile(this.packageFile.getAbsolutePath()).setScript(this.script).setScriptArgs(this.scriptArgs).setFailOnError(this.failOnError).setVerbose(this.verbose).setProfileName(getPropertyValue("profileName", props)).setType(this.type);
        
        model.setProperties(props);
        if (model.isValid()) {
          models.add(model);
        }
      }
    }
    return models;
  }
  
  private void updateApplicationNameWithSuffix(Properties props)
  {
    String appNameSuffix = getPropertyValue("applicationNameSuffix", props);
    if (StringUtils.isNotEmpty(appNameSuffix))
    {
      String appName = getPropertyValue("applicationName", props);
      props.setProperty("applicationName", appName + "_" + appNameSuffix);
    }
  }
  
  protected String getPropertyValue(String propertyName, Properties props)
  {
    String value = props.getProperty(propertyName);
    if (isValueNotResolved(value))
    {
      value = PropertiesUtils.resolve(value, props);
      props.setProperty(propertyName, value);
    }
    return value;
  }
  
  private boolean isValueNotResolved(String value)
  {
    return (StringUtils.isNotEmpty(value)) && (value.contains("{{")) && (value.contains("}}"));
  }
  
  private Properties getProjectProperties()
  {
    Properties properties = new Properties(this.project.getProperties());
    setProperty(properties, "applicationName", this.applicationName);
    setProperty(properties, "host", this.host);
    setProperty(properties, "port", this.port);
    setProperty(properties, "connectorType", this.connectorType);
    setProperty(properties, "cluster", this.cluster);
    setProperty(properties, "cell", this.cell);
    setProperty(properties, "node", this.node);
    setProperty(properties, "server", this.server);
    setProperty(properties, "virtualHost", this.virtualHost);
    setProperty(properties, "user", this.user);
    setProperty(properties, "password", this.password);
    setProperty(properties, "contextRoot", this.contextRoot);
    setProperty(properties, "sharedLibs", this.sharedLibs);
    setProperty(properties, "sharedDatasources", this.sharedDatasources);
    setProperty(properties, "parentLast", String.valueOf(this.parentLast));
    setProperty(properties, "webModuleParentLast", String.valueOf(this.webModuleParentLast));
    setProperty(properties, "packageFile", this.packageFile.getAbsolutePath());
    setProperty(properties, "failOnError", String.valueOf(this.failOnError));
    setProperty(properties, "script", this.script);
    setProperty(properties, "scriptArgs", this.scriptArgs);
    setProperty(properties, "verbose", String.valueOf(this.verbose));
    setProperty(properties, "profileName", String.valueOf(this.profileName));
    setProperty(properties, "type", this.type);
    
    properties.setProperty("basedir", this.project.getBasedir().getAbsolutePath());
    properties.setProperty("project.basedir", this.project.getBasedir().getAbsolutePath());
    properties.setProperty("version", this.project.getVersion());
    properties.setProperty("project.version", this.project.getVersion());
    properties.setProperty("project.build.directory", this.project.getBuild().getDirectory());
    properties.setProperty("project.build.outputDirectory", this.project.getBuild().getOutputDirectory());
    properties.setProperty("project.build.finalName", this.project.getBuild().getFinalName());
    properties.setProperty("project.name", this.project.getName());
    properties.setProperty("groupId", this.project.getGroupId());
    properties.setProperty("project.groupId", this.project.getGroupId());
    properties.setProperty("artifactId", this.project.getArtifactId());
    properties.setProperty("project.artifactId", this.project.getArtifactId());
    return properties;
  }
  
  private void setProperty(Properties properties, String key, String value)
  {
    if (StringUtils.isNotEmpty(value)) {
      properties.setProperty(key, value);
    }
  }
}
