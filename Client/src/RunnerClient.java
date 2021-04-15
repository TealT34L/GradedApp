import java.io.IOException;
import java.util.Scanner;

public class RunnerClient {

	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
		Scanner kb = new Scanner(System.in);
		Client cl = new Client();
		while (!cl.getValidity()) {
			System.out.println("Enter your username then password");
			System.out.println(cl.validate(kb.nextLine(), kb.nextLine()));
		}
		System.out.println("F");
		cl.refresh();
		cl.displayClasses();
		cl.closeThread();
		
	}
	
}
