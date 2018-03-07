package org.moflon.core.ecore2mocaxmi.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.eclipse.emf.ecore.ENamedElement;

public class GuidGenerator {
	private static String prefix1 = "00000000";
	private static String prefix2 = "0000";

	public static void init(String projectName) {
		String hashValue = sha1(projectName);
		prefix1 = hashValue.substring(hashValue.length() - 8);
		prefix2 = hashValue.substring(0, 4);
	}

	public static String generateGuid(ENamedElement element, String path) {
		String hashClassName = sha1(element.getClass().getName());
		String hashName = sha1(element.getName());
		String prefix3 = hashClassName.substring(0, 4);
		String prefix4 = hashName.substring(0, 4);
		String pathHash = path != null ? sha1(path) : sha1("" + (Math.random() * 4096));
		String suffix = pathHash.substring(pathHash.length() - 12);
		String guid = String.format("{%s-%s-%s-%s-%s}", prefix1, prefix2, prefix3, prefix4, suffix).toUpperCase();
		return guid;
	}

	private static String sha1(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(s.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// handle error case to taste
		}
		return null;
	}
}
