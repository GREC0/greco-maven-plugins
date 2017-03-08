package com.orctom.mojo.was.model;

public class WebSphereServiceException
  extends RuntimeException
{
	
private static final long serialVersionUID = 1L;

public WebSphereServiceException(String message)
  {
    super(message);
  }
  
public WebSphereServiceException(Throwable cause)
  {
    super(cause);
  }
  
public WebSphereServiceException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
