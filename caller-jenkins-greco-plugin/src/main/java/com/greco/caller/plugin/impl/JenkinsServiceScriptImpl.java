package com.greco.caller.plugin.impl;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.greco.caller.plugin.model.JenkinsCallerModel;
import com.greco.caller.plugin.service.IJenkinsService;

public class JenkinsServiceScriptImpl
  implements IJenkinsService
{
  private JenkinsCallerModel model;
  
  public JenkinsServiceScriptImpl(JenkinsCallerModel model, String targetDir)
  {
    System.out.println("Usando HttpClient 4.1.2 con preAuth");
    this.model = model;
  }
  
  
  
  public void Caller()
  {
    execute("caller");
  }
  
  private void execute(String action)
  {
	  
	   
	  HttpClientImpl client = new HttpClientImpl();
    	
    	HttpResponse response = null;
    	
    	if(action.equals("caller")){
    		response = client.callingJob(this.model);
    	}else{
    		System.err.println("la accion "+action+ " no esta implementada");
    	}
    	
    	if(response!=null){
    		Header[] headers = response.getAllHeaders();
    		for(Header headerReco : headers){
    			System.out.println(headerReco.getName()+"="+headerReco.getValue());
    		}
    			
    		response.getStatusLine();
    		
    	}
    
  }

}
