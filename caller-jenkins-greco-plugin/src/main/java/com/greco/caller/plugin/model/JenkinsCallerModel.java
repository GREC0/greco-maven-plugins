package com.greco.caller.plugin.model;

import org.codehaus.plexus.util.StringUtils;

public class JenkinsCallerModel
{
  private String protocolo = "http";
  private String host="localhost";
  private String port = "8080";
  private String connectorType = "SOAP";
  private String user;
  private String password;
  private String job;
  private String buildToken;
  private String jenkinsHome;
  
  public JenkinsCallerModel()
  {
    this.jenkinsHome = System.getProperty("JENKINS_HOME");
    if (null == this.jenkinsHome) {
      this.jenkinsHome = System.getenv("JENKINS_HOME");
    }
  }
  
  
  
  
  public String getProtocolo() {
	return this.protocolo;
}




public JenkinsCallerModel setProtocolo(String protocolo) {
	if (StringUtils.isNotBlank(protocolo)) {
		this.protocolo = protocolo;
	}
	return this;
}




public String getJob() {
	return this.job;
}




public JenkinsCallerModel setJob(String job) {
	if (StringUtils.isNotBlank(job)) {
		this.job = job;
	}
	return this;
}



public String getBuildToken() {
	return this.buildToken;
}




public JenkinsCallerModel setBuildToken(String buildToken) {
	if (StringUtils.isNotBlank(buildToken)) {
		this.buildToken = buildToken;
	}
	return this;
}




public String getJenkinsHome() {
	return this.jenkinsHome;
}




public JenkinsCallerModel setJenkinsHome(String jenkinsHome) {
	if (StringUtils.isNotBlank(jenkinsHome)) {
		this.jenkinsHome = jenkinsHome;
	}
	return this;
}




  
  public String getHost()
  {
    return this.host;
  }
  
  public JenkinsCallerModel setHost(String host)
  {
    this.host = host;
    return this;
  }

public String getPort()
  {
    return this.port;
  }
  
  public JenkinsCallerModel setPort(String port)
  {
    this.port = port;
    return this;
  }
  
  public String getConnectorType()
  {
    return this.connectorType;
  }
  
  public JenkinsCallerModel setConnectorType(String connectorType)
  {
    if (StringUtils.isNotBlank(connectorType)) {
      this.connectorType = connectorType;
    }
    return this;
  }
  
  public String getUser()
  {
    return this.user;
  }
  
  public JenkinsCallerModel setUser(String user)
  {
    this.user = user;
    return this;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public JenkinsCallerModel setPassword(String password)
  {
    this.password = password;
    return this;
  }

  
  public boolean isValid()
  {
    return (StringUtils.isNotBlank(this.host)) && (StringUtils.isNotBlank(this.job)) && (StringUtils.isNotBlank(this.buildToken)) && (StringUtils.isNotBlank(this.user) && (StringUtils.isNotBlank(this.password)));
  }
}
