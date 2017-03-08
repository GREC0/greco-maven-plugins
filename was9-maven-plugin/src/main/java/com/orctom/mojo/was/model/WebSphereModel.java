package com.orctom.mojo.was.model;

import java.util.Properties;
import org.codehaus.plexus.util.StringUtils;

public class WebSphereModel
{
  private String wasHome;
  private String applicationName;
  private String host;
  private String port = "8880";
  private String connectorType = "SOAP";
  private String cluster;
  private String cell;
  private String node;
  private String server;
  private String virtualHost;
  private String user;
  private String password;
  private String contextRoot;
  private String sharedLibs;
  private String sharedDatasources;
  private boolean parentLast;
  private boolean webModuleParentLast;
  private String packageFile;
  private String script;
  private String scriptArgs;
  private boolean failOnError;
  private boolean verbose;
  private Properties properties;
  private String profileName;
  private String type;
  
  public WebSphereModel()
  {
    this.wasHome = System.getProperty("WAS_HOME");
    if (null == this.wasHome) {
      this.wasHome = System.getenv("WAS_HOME");
    }
  }
  
  public String getWasHome()
  {
    return this.wasHome;
  }
  
  public WebSphereModel setWasHome(String wasHome)
  {
    if (StringUtils.isNotBlank(wasHome)) {
      this.wasHome = wasHome;
    }
    return this;
  }
  
  public String getApplicationName()
  {
    if ((null == this.applicationName) && (null != this.packageFile)) {
      this.applicationName = this.packageFile.substring(this.packageFile.lastIndexOf("."));
    }
    return this.applicationName;
  }
  
  public WebSphereModel setApplicationName(String applicationName)
  {
    this.applicationName = applicationName;
    return this;
  }
  
  public String getHost()
  {
    return this.host;
  }
  
  public WebSphereModel setHost(String host)
  {
    this.host = host;
    return this;
  }

  
  
  public String getProfileName() {
	return profileName;
}

public WebSphereModel setProfileName(String profileName) {
	this.profileName = profileName;
	return this;
}

public String getType() {
	return type;
}

public WebSphereModel setType(String type) {
	this.type = type;
	return this;
}

public String getPort()
  {
    if (null == this.port) {
      if (null != this.cluster) {
        this.port = "8879";
      } else {
        this.port = "8880";
      }
    }
    return this.port;
  }
  
  public WebSphereModel setPort(String port)
  {
    this.port = port;
    return this;
  }
  
  public String getConnectorType()
  {
    return this.connectorType;
  }
  
  public WebSphereModel setConnectorType(String connectorType)
  {
    if (StringUtils.isNotBlank(connectorType)) {
      this.connectorType = connectorType;
    }
    return this;
  }
  
  public String getCluster()
  {
    return this.cluster;
  }
  
  public WebSphereModel setCluster(String cluster)
  {
    this.cluster = cluster;
    return this;
  }
  
  public String getCell()
  {
    return this.cell;
  }
  
  public WebSphereModel setCell(String cell)
  {
    this.cell = cell;
    return this;
  }
  
  public String getNode()
  {
    return this.node;
  }
  
  public WebSphereModel setNode(String node)
  {
    this.node = node;
    return this;
  }
  
  public String getServer()
  {
    return this.server;
  }
  
  public WebSphereModel setServer(String server)
  {
    this.server = server;
    return this;
  }
  
  public String getVirtualHost()
  {
    return this.virtualHost;
  }
  
  public WebSphereModel setVirtualHost(String virtualHost)
  {
    this.virtualHost = virtualHost;
    return this;
  }
  
  public String getUser()
  {
    return this.user;
  }
  
  public WebSphereModel setUser(String user)
  {
    this.user = user;
    return this;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public WebSphereModel setPassword(String password)
  {
    this.password = password;
    return this;
  }
  
  public String getContextRoot()
  {
    return this.contextRoot;
  }
  
  public WebSphereModel setContextRoot(String contextRoot)
  {
    this.contextRoot = contextRoot;
    return this;
  }
  
  public String getSharedLibs()
  {
    return this.sharedLibs;
  }
  
  public WebSphereModel setSharedLibs(String sharedLibs)
  {
    this.sharedLibs = sharedLibs;
    return this;
  }
  
  
  
  public String getSharedDatasources() {
	return this.sharedDatasources;
}

public WebSphereModel setSharedDatasources(String sharedDatasources) {
	this.sharedDatasources = sharedDatasources;
	return this;
}

public boolean isParentLast()
  {
    return this.parentLast;
  }
  
  public WebSphereModel setParentLast(boolean parentLast)
  {
    this.parentLast = parentLast;
    return this;
  }
  
  public boolean isWebModuleParentLast()
  {
    return this.webModuleParentLast;
  }
  
  public WebSphereModel setWebModuleParentLast(boolean webModuleParentLast)
  {
    this.webModuleParentLast = webModuleParentLast;
    return this;
  }
  
  public String getPackageFile()
  {
    return this.packageFile;
  }
  
  public WebSphereModel setPackageFile(String packageFile)
  {
    this.packageFile = packageFile;
    return this;
  }
  
  public String getScript()
  {
    return this.script;
  }
  
  public WebSphereModel setScript(String script)
  {
    this.script = script;
    return this;
  }
  
  public String getScriptArgs()
  {
    return this.scriptArgs;
  }
  
  public WebSphereModel setScriptArgs(String scriptArgs)
  {
    this.scriptArgs = scriptArgs;
    return this;
  }
  
  public boolean isFailOnError()
  {
    return this.failOnError;
  }
  
  public WebSphereModel setFailOnError(boolean failOnError)
  {
    this.failOnError = failOnError;
    return this;
  }
  
  public boolean isVerbose()
  {
    return this.verbose;
  }
  
  public WebSphereModel setVerbose(boolean verbose)
  {
    this.verbose = verbose;
    return this;
  }
  
  public Properties getProperties()
  {
    return this.properties;
  }
  
  public void setProperties(Properties properties)
  {
    this.properties = properties;
  }
  
  public String toString()
  {
    return "WebSphereModel{wasHome='" + this.wasHome + '\'' + ", applicationName='" + this.applicationName + '\'' + ", host='" + this.host + '\'' + ", profileName='" + this.profileName+ '\'' + ", port='" + this.port + '\'' + ", connectorType='" + this.connectorType + '\'' + ", cluster='" + this.cluster + '\'' + ", cell='" + this.cell + '\'' + ", node='" + this.node + '\'' + ", server='" + this.server + '\'' + ", virtualHost='" + this.virtualHost + '\'' + ", user='" + this.user + '\'' + ", password='" + this.password + '\'' + ", contextRoot='" + this.contextRoot + '\'' + ", sharedLibs='" + this.sharedLibs +", sharedDatasources='" + this.sharedDatasources+ '\'' + ", parentLast='" + this.parentLast + '\'' + ", webModuleParentLast='" + this.webModuleParentLast + '\'' + ", packageFile='" + this.packageFile + '\'' + ", script='" + this.script + '\'' + ", scriptArgs='" + this.scriptArgs + '\'' + ", failOnError=" + this.failOnError + ", verbose=" + this.verbose + ", properties=" + this.properties + ", type=" + this.type + '}';
  }
  
  public boolean isValid()
  {
    return (StringUtils.isNotBlank(this.cluster)) || (StringUtils.isNotBlank(this.cell)) || (StringUtils.isNotBlank(this.server)) || (StringUtils.isNotBlank(this.node));
  }
}
