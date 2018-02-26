import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.net.ssl.SSLSocket;


public class serverThread extends Thread {
	
	protected SSLSocket threadSocket;
	protected String[] serverArgs;
	
	public serverThread(Socket serverSocket, String[] serverArgs) {
		this.threadSocket = (SSLSocket) serverSocket;
		this.serverArgs = serverArgs;
	}
	
	public void run() {
		try {
			// initialize input/output stream
			DataInputStream in = new DataInputStream(threadSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(threadSocket.getOutputStream());
			
			// read length of file and then read file from input stream
			int lengthFile = in.readInt();
			byte[] file = new byte[lengthFile];
			in.read(file);
			
			// write file to curpath/server/file
			Path path = Paths.get(Paths.get("").toAbsolutePath().toString() + "/server/file");
			Files.write(path,file);
			
			System.out.println(">> File saved to ./server/file.");
			
			System.out.println(">> Listening for client connection...");
			
			
		} catch (IOException e) {
			System.out.println("IO error - retry.");
			e.printStackTrace();
		}
	}
}
