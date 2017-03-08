package com.orctom.mojo.was.service;

public abstract interface IWebSphereService
{
  public abstract void restartServer();
  
  public abstract void installApplication();
  
  public abstract void uninstallApplication();
  
  public abstract void startApplication();
  
  public abstract void stopApplication();
  
  public abstract void deploy();
}
