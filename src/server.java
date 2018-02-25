import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class server {

	public static void Server(String[] serverArgs) {
		int portNumber = Integer.parseInt(serverArgs[1]);
		try {
			// Create serverSocket
			//SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			//SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(portNumber);
			System.out.println(">> Listening for client connection...");
			
			// load key/cert
			String keystorePath = serverArgs[2];
			String keyPassword = "1234567890";
			FileInputStream keystoreIn = new FileInputStream(keystorePath);
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(keystoreIn,keyPassword.toCharArray());
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(keyStore, keyPassword.toCharArray());
			
			//context
			SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			sslContext.init(kmf.getKeyManagers(), null, null);
			
			SSLServerSocket serverSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(portNumber);

			while(true) {
				
				
				// Listen for client connections
				SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
				System.out.println(">> Client connected!");
				// Start serverThread
				serverThread serverThread = new serverThread(clientSocket, serverArgs);
				serverThread.start();
				
			}
			
		}
		catch (IOException e) {
			System.out.println("Socket connection error - retry with different port number.");
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
