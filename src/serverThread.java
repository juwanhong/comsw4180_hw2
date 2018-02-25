import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.X509Certificate;


public class serverThread extends Thread {
	
	protected SSLSocket threadSocket;
	protected String[] serverArgs;
	
	public serverThread(Socket serverSocket, String[] serverArgs) {
		this.threadSocket = (SSLSocket) serverSocket;
		this.serverArgs = serverArgs;
	}
	
	public void run() {
		try {
			threadSocket.setNeedClientAuth(true);
			SSLSession threadSession = threadSocket.getSession();
			X509Certificate X509Certificate = threadSession.getPeerCertificateChain()[0];
			
			DataInputStream in = new DataInputStream(threadSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(threadSocket.getOutputStream());
			
			// keystore
			String keystorePath = serverArgs[2];
			FileInputStream keystoreIn = new FileInputStream(keystorePath);
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			keystore.load(keystoreIn,null);
			
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
