package org.magnum.mobilecloud.integration.test;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * This is an example of an HTTP client that does not properly
 * validate SSL certificates that are used for HTTPS. You should
 * NEVER use a client like this in a production application. Self-signed
 * certificates are ususally only OK for testing purposes, such as
 * this use case. 
 * 
 * @author jules
 *
 */
public class UnsafeHttpsClient {

	public static HttpClient createUnsafeClient() {
		final SSLContextBuilder builder = new SSLContextBuilder();
		
		try {
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		} catch (NoSuchAlgorithmException | KeyStoreException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		final SSLContext sslContext = buildSSLContext(builder);
		
		final SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);
		return HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();
	}
	
	private static SSLContext buildSSLContext(SSLContextBuilder builder) {
		try {
			return builder.build();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
