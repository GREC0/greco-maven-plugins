package com.greco.caller.plugin.http;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ConstantesHttp {
	
	
	 public static final int OK = 200;
	 public static final int CREATED = 201;
	 public static final int ACCEPTED = 202;
	 public static final int NON_AUTHORITIVE_INFORMATION = 203;
	 public static final int NO_CONTENT	= 204;
	 public static final int RESET_CONTENT = 205;
	 public static final int PARTIAL_CONTENT = 206;
	 public static final int MOVED_PERMANENTLY = 301;
	 public static final int FOUND = 302;
	 public static final int SEE_OTHER = 303;
	 public static final int NOT_MODIFIED = 304;
	 public static final int USE_PROXY = 305;
	 public static final int TEMPORARY_REDIRECT = 307;
	 public static final int BAD_REQUEST = 400;
	 public static final int UNAUTHORIZED = 401;
	 public static final int PAYMENT_REQUIRED = 402;
	 public static final int FORBIDDEN = 403;
	 public static final int NOT_FOUND = 404;
	 public static final int METHOD_NOT_ALLOWED = 405;
	 public static final int NOT_ACCEPTABLE = 406;
	 public static final int PROXY_AUTHENTICATION_REQUIRED = 407;
	 public static final int REQUEST_TIMEOUT = 408;
	 public static final int CONFLICT = 409;
	 public static final int GONE = 410;
	 public static final int LENGTH_REQUIRED = 411;
	 public static final int PRECONDITION_FAILED = 412;
	 public static final int REQUEST_ENTITY_TOO_LARGE = 413;
	 public static final int REQUEST_URI_TOO_LONG = 414;
	 public static final int UNSUPPORTED_MEDIA_TYPE = 415;
	 public static final int REQUESTED_RANGE_NOT_SATIFIABLE = 416;
	 public static final int EXPECTATION_FAILED = 417;
	 public static final int INTERNAL_SERVER_ERROR = 500;
	 public static final int NOT_IMPLEMENTED = 501;
	 public static final int BAD_GATEWAY = 502;
	 public static final int SERVICE_UNAVAILABLE = 503;
	 public static final int GATEWAY_TIMEOUT = 504;
	 public static final int HTTP_VERSION_NOT_SUPPORTED = 505;
	
	
	
	
	 public static final String CODE_200 = "OK";
	 public static final String CODE_201 = "Created";
	 public static final String CODE_202 = "Accepted";
	 public static final String CODE_203 = "Non-Authoritative Information";
	 public static final String CODE_204 = "No Content";
	 public static final String CODE_205 = "Reset Content";
	 public static final String CODE_206 = "Partial Content";
	 public static final String CODE_301 = "Moved Permantly";
	 public static final String CODE_302 = "Found";
	 public static final String CODE_303 = "See Other";
	 public static final String CODE_304 = "Not Modified";
	 public static final String CODE_305 = "Use Proxy";
	 public static final String CODE_307 = "Temporary Redirect";
	 public static final String CODE_400 = "Bad Request";
	 public static final String CODE_401 = "Unauthorized";
	 public static final String CODE_402 = "Payment Required";
	 public static final String CODE_403 = "Forbidden";
	 public static final String CODE_404 = "Not Found";
	 public static final String CODE_405 = "Method Not Allowed";
	 public static final String CODE_406 = "Not Acceptable";
	 public static final String CODE_407 = "Proxy Authentication Required";
	 public static final String CODE_408 = "Request Timeout";
	 public static final String CODE_409 = "Conflict";
	 public static final String CODE_410 = "Gone";
	 public static final String CODE_411 = "Length Required";
	 public static final String CODE_412 = "Precondition Failed";
	 public static final String CODE_413 = "Request Entity Too Large";
	 public static final String CODE_414 = "Request-URI Too Long";
	 public static final String CODE_415 = "Unsupported Media Type";
	 public static final String CODE_416 = "Requested Range Not Satisfiable";
	 public static final String CODE_417 = "Expectation Failed";
	 public static final String CODE_500 = "Internal Server Error";
	 public static final String CODE_501 = "Not Implemented";
	 public static final String CODE_502 = "Bad Gateway";
	 public static final String CODE_503 = "Service Unavailable";
	 public static final String CODE_504 = "Gateway Timeout";
	 public static final String CODE_505 = "HTTP Version Not Supported";
	 
	 public static final Map<Integer, String> CODIGOS_VALOR;
	    static
	    {
	    	CODIGOS_VALOR = new HashMap<Integer, String>();
	    	CODIGOS_VALOR.put(OK, CODE_200);
	    	CODIGOS_VALOR.put(CREATED, CODE_201);
	    	CODIGOS_VALOR.put(ACCEPTED, CODE_202);
	    	CODIGOS_VALOR.put(NON_AUTHORITIVE_INFORMATION, CODE_203);
	    	CODIGOS_VALOR.put(NO_CONTENT, CODE_204);
	    	CODIGOS_VALOR.put(RESET_CONTENT, CODE_205);
	    	CODIGOS_VALOR.put(PARTIAL_CONTENT, CODE_206);
	    	CODIGOS_VALOR.put(MOVED_PERMANENTLY, CODE_301);
	    	CODIGOS_VALOR.put(FOUND, CODE_302);
	    	CODIGOS_VALOR.put(SEE_OTHER, CODE_303);
	    	CODIGOS_VALOR.put(NOT_MODIFIED, CODE_304);
	    	CODIGOS_VALOR.put(USE_PROXY, CODE_305);
	    	CODIGOS_VALOR.put(TEMPORARY_REDIRECT, CODE_307);
	    	CODIGOS_VALOR.put(BAD_REQUEST, CODE_400);
	    	CODIGOS_VALOR.put(UNAUTHORIZED, CODE_401);
	    	CODIGOS_VALOR.put(PAYMENT_REQUIRED, CODE_402);
	    	CODIGOS_VALOR.put(FORBIDDEN, CODE_403);
	    	CODIGOS_VALOR.put(NOT_FOUND, CODE_404);
	    	CODIGOS_VALOR.put(METHOD_NOT_ALLOWED, CODE_405);
	    	CODIGOS_VALOR.put(NOT_ACCEPTABLE, CODE_406);
	    	CODIGOS_VALOR.put(PROXY_AUTHENTICATION_REQUIRED, CODE_407);
	    	CODIGOS_VALOR.put(REQUEST_TIMEOUT, CODE_408);
	    	CODIGOS_VALOR.put(CONFLICT, CODE_409);
	    	CODIGOS_VALOR.put(GONE, CODE_410);
	    	CODIGOS_VALOR.put(LENGTH_REQUIRED, CODE_411);
	    	CODIGOS_VALOR.put(PRECONDITION_FAILED, CODE_412);
	    	CODIGOS_VALOR.put(REQUEST_ENTITY_TOO_LARGE, CODE_413);
	    	CODIGOS_VALOR.put(REQUEST_URI_TOO_LONG, CODE_414);
	    	CODIGOS_VALOR.put(UNSUPPORTED_MEDIA_TYPE, CODE_415);
	    	CODIGOS_VALOR.put(REQUESTED_RANGE_NOT_SATIFIABLE, CODE_416);
	    	CODIGOS_VALOR.put(EXPECTATION_FAILED, CODE_417);
	    	CODIGOS_VALOR.put(INTERNAL_SERVER_ERROR, CODE_500);
	    	CODIGOS_VALOR.put(NOT_IMPLEMENTED, CODE_501);
	    	CODIGOS_VALOR.put(BAD_GATEWAY, CODE_502);
	    	CODIGOS_VALOR.put(SERVICE_UNAVAILABLE, CODE_503);
	    	CODIGOS_VALOR.put(GATEWAY_TIMEOUT, CODE_504);
	    	CODIGOS_VALOR.put(HTTP_VERSION_NOT_SUPPORTED, CODE_505);
	    }
	 
	 
	
	 
//	 public static final String CODE_201 = "Created";
//	 public static final String CODE_202 = "Accepted";
//	 public static final String CODE_203 = "Non-Authoritative Information";
//	 public static final String CODE_204 = "No Content";
//	 public static final String CODE_205 = "Reset Content";
//	 public static final String CODE_206 = "Partial Content";
//	 public static final String CODE_301 = "Moved Permantly";
//	 public static final String CODE_302 = "Found";
//	 public static final String CODE_303 = "See Other";
//	 public static final String CODE_304 = "Not Modified";
//	 public static final String CODE_305 = "Use Proxy";
//	 public static final String CODE_307 = "Temporary Redirect";
//	 public static final String CODE_400 = "Bad Request";
//	 public static final String CODE_401 = "Unauthorized";
//	 public static final String CODE_402 = "Payment Required";
//	 public static final String CODE_403 = "Forbidden";
//	 public static final String CODE_404 = "Not Found";
//	 public static final String CODE_405 = "Method Not Allowed";
//	 public static final String CODE_406 = "Not Acceptable";
//	 public static final String CODE_407 = "Proxy Authentication Required";
//	 public static final String CODE_408 = "Request Timeout";
//	 public static final String CODE_409 = "Conflict";
//	 public static final String CODE_410 = "Gone";
//	 public static final String CODE_411 = "Length Required";
//	 public static final String CODE_412 = "Precondition Failed";
//	 public static final String CODE_413 = "Request Entity Too Large";
//	 public static final String CODE_414 = "Request-URI Too Long";
//	 public static final String CODE_415 = "Unsupported Media Type";
//	 public static final String CODE_416 = "Requested Range Not Satisfiable";
//	 public static final String CODE_417 = "Expectation Failed";
//	 public static final String CODE_500 = "Internal Server Error";
//	 public static final String CODE_501 = "Not Implemented";
//	 public static final String CODE_502 = "Bad Gateway";
//	 public static final String CODE_503 = "Service Unavailable";
//	 public static final String CODE_504 = "Gateway Timeout";
//	 public static final String CODE_505 = "HTTP Version Not Supported";
	
	
	
	public static final String PREPRODUCCION ="V";
	public static final String PRODUCCION ="E";
	
	
	
	public static final List<Integer> STATUS_SERVER_CODES= new ArrayList<Integer>(Arrays.asList(OK, CREATED, ACCEPTED, NON_AUTHORITIVE_INFORMATION, NO_CONTENT, RESET_CONTENT, PARTIAL_CONTENT, MOVED_PERMANENTLY, FOUND, SEE_OTHER, NOT_MODIFIED, USE_PROXY, TEMPORARY_REDIRECT, BAD_REQUEST, UNAUTHORIZED, PAYMENT_REQUIRED, FORBIDDEN,
			NOT_FOUND, METHOD_NOT_ALLOWED, NOT_ACCEPTABLE, PROXY_AUTHENTICATION_REQUIRED, REQUEST_TIMEOUT, CONFLICT, GONE, LENGTH_REQUIRED, PRECONDITION_FAILED, REQUEST_ENTITY_TOO_LARGE, REQUEST_URI_TOO_LONG, UNSUPPORTED_MEDIA_TYPE,
			REQUESTED_RANGE_NOT_SATIFIABLE, EXPECTATION_FAILED, INTERNAL_SERVER_ERROR, NOT_IMPLEMENTED, BAD_GATEWAY, SERVICE_UNAVAILABLE, GATEWAY_TIMEOUT, HTTP_VERSION_NOT_SUPPORTED));
	
	
	public static final List<String> STATUS_SERVER_STATUS= new ArrayList<String>(Arrays.asList(CODE_200, CODE_201, CODE_202, CODE_203, CODE_204, CODE_205, CODE_206, CODE_301, CODE_302, CODE_303, CODE_304, CODE_305, CODE_307, CODE_400, CODE_401, CODE_402, CODE_403, CODE_404, 
			CODE_405, CODE_406, CODE_407, CODE_408, CODE_409, CODE_410, CODE_411, CODE_412, CODE_413, CODE_414, CODE_415, CODE_416, CODE_417, CODE_500, CODE_501, CODE_502, CODE_503, CODE_504, CODE_505));
	
	
	

}
