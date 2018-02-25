import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.Certificate;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class client {

	public static void Client(String[] clientArgs) {
		try {
			InetAddress serverIP = InetAddress.getByName(clientArgs[4]);
			int serverPort = Integer.parseInt(clientArgs[5]);
			
			// Open socket to server and input/output streams
			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket clientSocket = (SSLSocket) sf.createSocket(serverIP, serverPort);
			
			SSLSession clientSession = clientSocket.getSession();
			Certificate[] certificates = clientSession.getPeerCertificates();
			
			
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
