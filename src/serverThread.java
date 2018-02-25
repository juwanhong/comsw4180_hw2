import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
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
			DataInputStream in = new DataInputStream(threadSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(threadSocket.getOutputStream());
			
			int lengthFile = in.readInt();
			byte[] file = new byte[lengthFile];
			in.read(file);
			
			Path path = Paths.get(Paths.get("").toAbsolutePath().toString() + "/server/file");
			Files.write(path,file);
			
			System.out.println(">> File saved to ./server/file.");
			
			System.out.println(">> Listening for client connection...");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
