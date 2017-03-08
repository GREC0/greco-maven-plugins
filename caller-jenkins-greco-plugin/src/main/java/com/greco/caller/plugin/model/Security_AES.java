package com.greco.caller.plugin.model;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Security_AES
{
   private static SecretKeySpec secretKey;
   private static byte[] key ;
   
  
   public static void setKey(String myKey)
   {
       MessageDigest sha = null;
       try 
       {
           key = myKey.getBytes("UTF-8");
           sha = MessageDigest.getInstance("SHA-1");
           key = sha.digest(key);
           key = (byte[])resizeArray(key, 16); // use only first 128 bit
           secretKey = new SecretKeySpec(key, "AES");
       } 
       catch (Exception e){
    	   e.printStackTrace();
       }
   }
      
   private static Object resizeArray (Object oldArray, int newSize) 
   {
   	   int oldSize = java.lang.reflect.Array.getLength(oldArray);
   	   Class<?> elementType = oldArray.getClass().getComponentType();
   	   Object newArray = java.lang.reflect.Array.newInstance(elementType,newSize);
   	   int preserveLength = Math.min(oldSize,newSize);
   	   if (preserveLength > 0)
   	   {
   	      System.arraycopy (oldArray,0,newArray,0,preserveLength);
   	   }
   	   return newArray; 
   	}
   
 

   public static String decrypt(String strToDecrypt, String key)
   {
       try
       {
    	   Security_AES.setKey(key);
           Cipher cipher = Cipher.getInstance("AES");
           cipher.init(Cipher.DECRYPT_MODE, secretKey);
           return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt.getBytes())));
        }
        catch (Exception e)
        {
        	throw new RuntimeException(e);
        }
    }
   
//   public static String encrypt(String strToEncrypt, String key)
//   {
//       try
//       {
//    	   Security_AES.setKey(key);
//           Cipher cipher = Cipher.getInstance("AES");
//           cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//           return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
//       }
//       catch (Exception e)
//       {
//          throw new RuntimeException(e);
//       }
//   }
//
//   	/** TEST */
//	public static void main(String args[])
//	{
//		final String encrypt = "pass";
//	    final String strPssword = "user";
//	   
//	    String encrypted = "ytZozwIz5calLkUOL0+A6Q==";
//	    System.out.println("Encrypted:" + encrypted);
//	    String decrypt = Security_AES.encrypt(encrypt, strPssword);
//	    System.out.println("Decrypted:" + Security_AES.decrypt(encrypted, strPssword));
//	}
}
