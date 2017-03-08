package com.greco.caller.plugin.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Entity;

import com.greco.caller.plugin.html.WebViewHtmlCode;
import com.greco.caller.plugin.http.ConstantesHttp;
import com.greco.caller.plugin.model.JenkinsCallerModel;
import com.greco.caller.plugin.model.Security_AES;



public class HttpClientImpl {
	public HttpResponse callingJob(JenkinsCallerModel args) {

		HttpResponse response = null;
		
		// Credentials
		String username = args.getUser();
		System.out.println("user = "+username);
//		String password = new String(Base64.getDecoder().decode(args.getPassword().getBytes())); // old version base 64
		String password = Security_AES.decrypt(args.getPassword(), username);
		System.out.println("password decode = "+password);
		// Jenkins url
		String jenkinsUrl =args.getProtocolo()+"://"+args.getHost()+":"+args.getPort();
		System.out.println("jenkinsUrl = "+jenkinsUrl);
		// Build name
		String jobName = args.getJob();
		System.out.println("jobName = "+jobName);
		// Build token
		String buildToken = args.getBuildToken();
		System.out.println("buildToken = "+buildToken);
		// httpclient
		DefaultHttpClient client = new DefaultHttpClient();

		client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(username, password));

		BasicScheme basicAuth = new BasicScheme();
		BasicHttpContext context = new BasicHttpContext();
		context.setAttribute("preemptive-auth", basicAuth);

		client.addRequestInterceptor(new PreemptiveAuth(), 0);

		String getUrl = jenkinsUrl + "/job/" + jobName + "/build?token=" + buildToken;
		System.out.println("buildToken = "+buildToken);
		System.out.println("url build = "+getUrl);
		HttpGet get = new HttpGet(getUrl);

		try {
			response = client.execute(get, context);
			printParameters(client);
			WebViewHtmlCode code = new WebViewHtmlCode();
			HttpEntity entity = response.getEntity();
			System.out.println("pintamos respuesta en el navegador por defecto");
			EntityUtils.consume(entity);
			if(!statusServer(response.getStatusLine().getStatusCode())){
				code.viewHtml(EntityUtils.toString(entity));
				System.out.println("[ERROR] : error en la respuesta de jenkins cod estado = "+String.valueOf(response.getStatusLine().getStatusCode()) + " cerrando ejecucion ");
				try {
					throw new HttpException("[ERROR] : error en la respuesta de jenkins cod estado = "+String.valueOf(response.getStatusLine().getStatusCode()) + " cerrando ejecucion ");
				} catch (HttpException e) {
					e.printStackTrace();
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}

	/**
	 * Preemptive authentication interceptor
	 *
	 */
	static class PreemptiveAuth implements HttpRequestInterceptor {

		/*
		 * (non-Javadoc)
		 *
		 * @see org.apache.http.HttpRequestInterceptor#process(org.apache.http.HttpRequest,
		 * org.apache.http.protocol.HttpContext)
		 */
		public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
			// Get the AuthState
			AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);

			// If no auth scheme available yet, try to initialize it preemptively
			if (authState.getAuthScheme() == null) {
				AuthScheme authScheme = (AuthScheme) context.getAttribute("preemptive-auth");
				CredentialsProvider credsProvider = (CredentialsProvider) context
						.getAttribute(ClientContext.CREDS_PROVIDER);
				HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
				if (authScheme != null) {
					Credentials creds = credsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost
							.getPort()));
					if (creds == null) {
						throw new HttpException("no se requiere de credenciales");
					}
					authState.setAuthScheme(authScheme);
					authState.setCredentials(creds);
				}
			}

		}

	}
	
	
//	 STATUS SERVER
	 public boolean statusServer(int status){
		 
		 boolean resultado = false;
		 
		 for(int statusPosibleRecorriendo : ConstantesHttp.STATUS_SERVER_CODES){
			 
			 if(statusPosibleRecorriendo==status){
				 
				 if(status==ConstantesHttp.OK || status==ConstantesHttp.ACCEPTED || status==ConstantesHttp.CREATED){
					 resultado=true;
				 }
				 
				 if(ConstantesHttp.CODIGOS_VALOR.containsKey(status)){
					 
					 System.out.println("----------------------------------------------------------------------------------------------------------------");
					 System.out.println("el servidor se encuentra en estado " + ConstantesHttp.CODIGOS_VALOR.get(status) + " con codigo de estado " + String.valueOf(statusPosibleRecorriendo));
					 System.out.println("----------------------------------------------------------------------------------------------------------------");
					
				 }
				
				 break;
			 }
			 
		 }
		 
		 return resultado;
		 
	 }
	 
	 private void printParameters(DefaultHttpClient client){
		 
		 if(client.getParams()!=null){
			 HttpParams params = client.getParams();
			 System.out.println("client.getParams() = "+params.toString());
		 }
		 
		 if(client.getCredentialsProvider()!=null){
			 System.out.println("client.getCredentialsProvider() = "+client.getCredentialsProvider().toString());
		 }
		 
		 
		 if( client.getCookieStore()!=null){
			 List<Cookie> cookiesStore = client.getCookieStore().getCookies();
			 if(cookiesStore!=null && !cookiesStore.isEmpty()){
				 for(Cookie cookReco : cookiesStore){
					 if(cookReco!=null){
						 System.out.println("cookReco.---  cookie ---"); 
						 System.out.println("cookReco.getComment= "+cookReco.getComment());
						 System.out.println("cookReco.getCommentURL= "+cookReco.getCommentURL());
						 System.out.println("cookReco.getDomain= "+cookReco.getDomain());
						 System.out.println("cookReco.getExpiryDate= "+String.valueOf(cookReco.getExpiryDate()));
						 System.out.println("cookReco.getName= "+cookReco.getName());
						 System.out.println("cookReco.getPath= "+cookReco.getPath());
						 System.out.println("cookReco.getPorts= "+String.valueOf(cookReco.getPorts()));
						 System.out.println("cookReco.getValue= "+cookReco.getValue());
						 System.out.println("cookReco.getVersion= "+String.valueOf(cookReco.getVersion()));
					 }
				 }
			 }
		 }
		 
		 if(client.getCookieSpecs()!=null){
			 List<String> cookiesSpecsNames = client.getCookieSpecs().getSpecNames();
			 
			 for(String stringReco : cookiesSpecsNames){
				 CookieSpec cook = client.getCookieSpecs().getCookieSpec(stringReco);
				 if(cook!=null){
					 System.out.println("cook.getVersion= "+String.valueOf(cook.getVersion()));
					 if(cook.getVersionHeader()!=null){
						 System.out.println("cook.getVersionHeader.getName()= "+cook.getVersionHeader().getName());
						 System.out.println("cook.getVersionHeader.getValue()= "+cook.getVersionHeader().getValue());
						 HeaderElement[] elements =  cook.getVersionHeader().getElements();
						 if(elements!=null && elements.length>0){
								 for(HeaderElement elementReco : elements){
									 if(elementReco!=null){
										 System.out.println("elementReco.getName()= "+elementReco.getName());
										 System.out.println("elementReco.getValue()= "+elementReco.getValue());
										 NameValuePair[] namePair = elementReco.getParameters();
										 
										 if(namePair!=null && namePair.length>0){
											 for(NameValuePair namePairReco : namePair){
											 if(namePairReco!=null){
												 System.out.println("namePairReco.getName()= "+namePairReco.getName());
												 System.out.println("namePairReco.getValue()= "+namePairReco.getValue());
											 }
										 }
									 }
								 }
							 }
						 }
					 }
				 }
			 }
		 }
		 
		 if(client.getAuthSchemes()!=null){
			 List<String> schemeNames = client.getAuthSchemes().getSchemeNames();
			 if(schemeNames!=null && !schemeNames.isEmpty()){
				 for(String stringReco : schemeNames){
					if(stringReco!=null && !stringReco.trim().equals("")){
						System.out.println("client.getAuthSchemes().getSchemeNamesReco().getName()= "+stringReco);
					}
				 }
			 }
			 
		 }
		 
	 }
}
