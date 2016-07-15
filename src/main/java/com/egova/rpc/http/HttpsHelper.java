package com.egova.rpc.http;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

class HttpsHelper {

	static HostnameVerifier verifier = null;

	static SSLSocketFactory socketFactory = null;

	private static class DefaultTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	}

	static {

		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());

			ctx.getClientSessionContext().setSessionTimeout(15);
			ctx.getClientSessionContext().setSessionCacheSize(1000);

			socketFactory = ctx.getSocketFactory();
		} catch (Exception e) {

		}

		verifier = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return false;
			}
		};

	}
}
