import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
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
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class client {

	public static void Client(String[] clientArgs) {
		try {
			String filePath = clientArgs[3];
			InetAddress serverIP = InetAddress.getByName(clientArgs[4]);
			int serverPort = Integer.parseInt(clientArgs[5]);
			
			// keystore
			String keystorePath = clientArgs[2];
			FileInputStream keystoreIn = new FileInputStream(keystorePath);
			KeyStore keystore = KeyStore.getInstance("JKS");
			keystore.load(keystoreIn,null);
			
			String keyPassword = "1234567890";
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
			kmf.init(keystore, keyPassword.toCharArray());
			
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
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
