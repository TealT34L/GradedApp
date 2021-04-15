import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerRunner {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		System.out.println("Server Start");
		Scanner kb = new Scanner(System.in);
		ServerSocket server = new ServerSocket(1195);
		ArrayList<UserThread> threads = new ArrayList<>();
		
		do {
			Socket socket = server.accept();
			threads.add(new UserThread(socket));
			System.out.println("NEW SOCKET " + threads.size());
			threads.get(threads.size()-1).start();
			ArrayList<UserThread> toRem = new ArrayList<>();
			for (UserThread x : threads) {
				if(x.isInterrupted()) {toRem.add(x);}
			}
			for (UserThread x : toRem) {
				threads.remove(x);
			}
			
		} while(true);
		
		
		//server.close();
		//kb.close();
	}
    
}