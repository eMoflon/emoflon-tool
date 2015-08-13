package org.moflon.mosl.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GuidGenerator {
	private static Long counter = 0l;
	private static String prefix1 = "00000000";
   private static String prefix2 = "0000";
   
   public static void init(String projectName) {
      String hashValue = sha1(projectName);
      prefix1 = hashValue.substring(hashValue.length()-8);
      prefix2 = hashValue.substring(0, 4);
   }
	
	public static String generateGuid(String originalGuid) {
	   if (originalGuid != null && originalGuid.length() > 0) {
	      return originalGuid;
	   }
		return String.format("{%s-%s-0000-0000-%012x}", prefix1, prefix2, counter++).toUpperCase();
	}
	
	private static String sha1(String s) {
	   try {
	        MessageDigest md = MessageDigest.getInstance( "SHA1" );
	        md.update( s.getBytes() );
	        return new BigInteger( 1, md.digest() ).toString(16);
	    }
	    catch (NoSuchAlgorithmException e) {
	        // handle error case to taste
	    }
	   return null;
	}
}
