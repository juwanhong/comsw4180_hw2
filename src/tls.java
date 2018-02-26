
public class tls {

	public static void main(String[] args) {
		if(args.length != 6 && args.length != 3) {
			System.out.println("Input error!");
			System.out.println("Input format: ");
			System.out.println("    server: java tls -s [port] [key/cert path]");
			System.out.println("    client: java tls -c [key/cert path] [file path] [server address] [port]");
			
			return;
		}
		
		String mode = args[0];
		
		switch(mode) {
		
		// start server
		case "-s":
			if(args.length == 3) {
				server.Server(args);
			}
			else {
				System.out.println("Input error! Input format should be:");
				System.out.println("    server: java tls -s [port] [key/cert path]");
			}
			break;
		
		// start client
		case "-c":
			if(args.length == 6) {
				client.Client(args);
			}
			else {
				System.out.println("Input error! Input format should be:");
				System.out.println("    client: java tls -c [key/cert path] [file path] [server address] [port]");
			}
			break;
			
		default:
			System.out.println("Use [-s] for server and [-c] for client.");
			break;
		}
	}
}
