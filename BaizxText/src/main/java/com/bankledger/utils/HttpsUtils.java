package com.bankledger.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsUtils {

	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final Integer DEFAULT_CONNECT_TIMEOUT = 5000;

	private static final Integer DEFAULT_READ_TIMEOUT = 100000;

	public static String get(String url) throws Exception {
		return get(url, null, null);
	}

	public static String get(String url, Map<String, String> params) throws Exception {
		return get(url, null, params);
	}

	public static String get(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		String mapURLEncodingString = getMapURLEncodingString(params);
		url = mapURLEncodingString.length() > 0 ? url + "?" + mapURLEncodingString : url;
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			conn = getHttpURLConnection(url, headers);
			is = conn.getInputStream();
			return getInputStreamString(is);
		} catch (Exception e) {
			throw e;
		} finally {
			is.close();
			conn.disconnect();
		}
	}

	public static String post(String url, Map<String, String> params) throws Exception {
		return post(url, null, params);
	}

	public static String post(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		String mapURLEncodingString = getMapURLEncodingString(params);
		return post(url, headers, mapURLEncodingString);
	}

	public static String post(String url, String body) throws Exception {
		return post(url, null, body);
	}

	public static byte[] post_bytes(String url, Map<String, String> headers, Map<String, String> params) throws Exception{
		String body = getMapURLEncodingString(params);
		HttpURLConnection conn = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			conn = getHttpURLConnection(url, headers);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			os = conn.getOutputStream();
			os.write(body.getBytes(DEFAULT_CHARSET));
			is = conn.getInputStream();
			
			byte[] buf = new byte[1024];
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			
			int readed = 0;
			while( (readed = is.read(buf)) > 0 ){
				byteArrayOutputStream.write(buf, 0, readed);
			}
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
				os.close();
			}
			conn.disconnect();
		}
		
	}
	
	public static String post(String url, Map<String, String> headers, String body) throws Exception {
		HttpURLConnection conn = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			conn = getHttpURLConnection(url, headers);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			os = conn.getOutputStream();
			os.write(body.getBytes(DEFAULT_CHARSET));
			is = conn.getInputStream();
			return getInputStreamString(is);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
				os.close();
			}
			conn.disconnect();
		}
		
	}

	private static HttpURLConnection getHttpURLConnection(String url, Map<String, String> headers) throws Exception {

		HttpURLConnection conn = null;
		if (url.startsWith("https")) {
			javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
			javax.net.ssl.TrustManager tm = new MyTrustManager();
			trustAllCerts[0] = tm;
			javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, null);
			javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		}
		conn = (HttpURLConnection) new URL(url).openConnection();

		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
		conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
		conn.setReadTimeout(DEFAULT_READ_TIMEOUT);

		return conn;
	}

	private static String getInputStreamString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	private static String getMapURLEncodingString(Map<String, String> params) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			String key = null;
			String val = null;
			for (Entry<String, String> param : params.entrySet()) {
				key = URLEncoder.encode(param.getKey(), DEFAULT_CHARSET);
				val = URLEncoder.encode(param.getValue(), DEFAULT_CHARSET);
				sb.append(key + "=" + val);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		String url = "https://192.168.0.6:8443/rpcserver/JSONRPC/v1/users/hehe";
		String body = "json";
		String result = post(url, body);
	}

	/**
	 * 通过代替默认的证书管理器,忽略证书来访问HTTPS请求.
	 */
	static class MyTrustManager implements TrustManager, X509TrustManager {

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
			return;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
			return;
		}

	}

}
