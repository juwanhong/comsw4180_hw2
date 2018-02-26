import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class client {

	public static void Client(String[] clientArgs) {
		try {
			String filePath = clientArgs[2];
			InetAddress serverIP = InetAddress.getByName(clientArgs[3]);
			int serverPort = Integer.parseInt(clientArgs[4]);
			
			// load keystore of JKS format
			String keyPassword = "1234567890";
			String keystorePath = clientArgs[1];
			FileInputStream keystoreIn = new FileInputStream(keystorePath);
			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(keystoreIn,keyPassword.toCharArray());
			
			// initialize key manager factory
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(keystore, keyPassword.toCharArray());
			
			// initialize context
			SSLContext context = SSLContext.getInstance("TLSv1.2");
			context.init(kmf.getKeyManagers(), null, new SecureRandom());

			// Open socket to server and input/output streams
			SSLSocketFactory sf = context.getSocketFactory();
			SSLSocket clientSocket = (SSLSocket) sf.createSocket(serverIP, serverPort);
			
			// send file
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			
			Path path = Paths.get(filePath);
			byte[] file = Files.readAllBytes(path);
			
			out.writeInt(file.length);
			out.write(file);
			
			in.close();
			out.close();
			clientSocket.close();
			
		} catch (NumberFormatException e) {
			System.out.println("IP address or Port number error - retry with correct input format.");
		} catch (FileNotFoundException e) {
			System.out.println("File not found - retry with correct path.");
		}catch (UnknownHostException e) {
			System.out.println("Host not found error.");
		} catch (IOException e) {
			System.out.println("IO error - retry.");
			e.printStackTrace();
		} catch (KeyStoreException e) {
			System.out.println("Keystore error - try with new keystore.");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No such algorithm for key pair - retry with different key pair.");
		} catch (CertificateException e) {
			System.out.println("Certificate error - retry with new certificate.");
		} catch (UnrecoverableKeyException e) {
			System.out.println("Unrecoverable key error - retry with different key pair.");
		} catch (KeyManagementException e) {
			System.out.println("Key manager error - retry.");
		}
		
	}
}
