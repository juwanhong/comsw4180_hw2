
public class tls {

	public static void main(String[] args) {
		String mode = args[0];
		
		switch(mode) {
		case "-s":
			server.Server(args);
			break;
			
		case "-c":
			client.Client(args);
			break;
			
		default:
			break;
		}
	}
}
