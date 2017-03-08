package com.greco.caller.plugin.model;

public class JenkinsServiceException
  extends RuntimeException
{
	
private static final long serialVersionUID = 1L;

public JenkinsServiceException(String message)
  {
    super(message);
  }
  
public JenkinsServiceException(Throwable cause)
  {
    super(cause);
  }
  
public JenkinsServiceException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
