import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class server {

	public static void Server(String[] serverArgs) {
		
		try {
			int portNumber = Integer.parseInt(serverArgs[1]);
			System.out.println(">> Listening for client connection...");
			
			// load key/cert from JKS file. It was generated with password 1234567890
			String keystorePath = serverArgs[2];
			String keyPassword = "1234567890";
			FileInputStream keystoreIn = new FileInputStream(keystorePath);
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(keystoreIn,keyPassword.toCharArray());
			
			// initialize keymanagerfactory with SunX509 instance
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(keyStore, keyPassword.toCharArray());
			
			// initialize context with TLSv1.2
			SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			sslContext.init(kmf.getKeyManagers(), null, null);
			
			// instantiate a ssl server socket with the context and port number
			SSLServerSocket serverSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(portNumber);
			serverSocket.setNeedClientAuth(true);
			
			while(true) {
				// Listen for client connections
				SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
				System.out.println(">> Client connected!");
				// Start serverThread
				serverThread serverThread = new serverThread(clientSocket, serverArgs);
				serverThread.start();
			}
			
		}
		catch (NumberFormatException e) {
			System.out.println("Port number error - retry with correct port number.");
		} catch (FileNotFoundException e) {
			System.out.println("Keystore file not found - retry with correct path.");
		} catch (IOException e) {
			System.out.println("Socket connection error - retry with different port number.");
		} catch (KeyManagementException e) {
			System.out.println("Key manager error - retry.");
		} catch (UnrecoverableKeyException e) {
			System.out.println("Unrecoverable key error - retry with different key pair.");
		} catch (CertificateException e) {
			System.out.println("Certificate error - retry with new certificate.");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No such algorithm for key pair - retry with different key pair.");
		} catch (KeyStoreException e) {
			System.out.println("No key found error - recheck the key path.");
		}
	}
}
