package com.bankledger.utils;

import java.security.MessageDigest;

public class Hash256 {

	// byte——>String
	/*
	 * public static String bytesToHexString(byte[] src){ StringBuilder
	 * stringBuilder = new StringBuilder(""); if (src == null || src.length <=
	 * 0) { return null; } for (int i = 0; i < src.length; i++) { int v = src[i]
	 * & 0xFF; String hv = Integer.toHexString(v); if (hv.length() < 2) {
	 * stringBuilder.append(0); } stringBuilder.append(hv); } return
	 * stringBuilder.toString(); }
	 */

	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp);
		}
		return sb.toString();
	}

	// HexString——>byte
	public static String getSHA256Hash(String toBesign) {
		String rs = "";
		MessageDigest md = null;
		try {
			byte[] bt = toBesign.getBytes("UTF-8");
			String encName = "SHA-256";
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			byte[] d = md.digest();
			md.update(d);
			byte[] temp = md.digest();
			byte[] result = new byte[temp.length];
			int j = 0;
			for (int i = temp.length - 1; i >= 0; i--) {
				result[j++] = temp[i];
			}
			rs = bytesToHexString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
}
