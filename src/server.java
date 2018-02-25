import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class server {

	public static void Server(String[] serverArgs) {
		int portNumber = Integer.parseInt(serverArgs[1]);
		try {
			// Create serverSocket
			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(portNumber);
			System.out.println(">> Listening for client connection...");

			while(true) {
				// Listen for client connections
				SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
				// Start serverThread
				serverThread serverThread = new serverThread(clientSocket, serverArgs);
				serverThread.start();
				
			}
			
		}
		catch (IOException e) {
			System.out.println("Socket connection error - retry with different port number.");
		}
	}
}
