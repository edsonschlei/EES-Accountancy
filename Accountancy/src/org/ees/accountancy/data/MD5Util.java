package org.ees.accountancy.data;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	/**
	 * Transform the informed string in a encoded string with the md5 algorithm.
	 *  
	 * @param decoded
	 * @return
	 */
	public static String encode(String decoded) {
		try {
			byte[] bytes = decoded.getBytes("UTF-8");
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] digest2 = digest.digest(bytes);
			return new String(digest2);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
