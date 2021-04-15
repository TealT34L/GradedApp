import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class UserThread extends Thread {
	
	private Socket socket;
	//private InputStream in;
	private OutputStream out;
	
	private ObjectInputStream inpS;
	private ObjectOutputStream outS;
	
	private Student student;
	
	private boolean validUser = false;
	
	public UserThread(Socket socket) throws IOException {
		this.socket = socket;

		this.out = socket.getOutputStream();

		this.student = new Student();
		this.inpS = new ObjectInputStream(socket.getInputStream());
		this.outS = new ObjectOutputStream(out);
	}
	
	@Override
	public void run() {
		System.out.println("new thread");
		while(!this.isInterrupted()) {
			boolean inter = false;
			try {
				if (inpS.available() != 0) {
					int inByte = inpS.readInt();
					System.out.println(inByte + " inByte");
					//sendData
					if (inByte == 0 && validUser) {
						student.refresh();
						student.getOldClasses();
						student.close();
						outS.writeObject((student.getClasses()));
						outS.writeObject((student.getOldClasses()));
						System.out.println("Classes Read");
					}
					//close
					if (inByte == 1) {
						System.out.println("Closing Thread " + socket.getInetAddress());
						inter = true;
					}
					//validate
					if (inByte == 2) {
						while (inpS.available() == 0) {
							try {
								Thread.sleep(5);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (!validUser) {
							String user = inpS.readUTF();
							String pass = inpS.readUTF();
							System.out.println(user + " " + pass);
							validUser = student.validate(user, pass);
							System.out.println("User and Pass: " + user + "\t" + pass + "\t" + "Validation:" + validUser);
							outS.writeBoolean(validUser);
							outS.flush();
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (inter) {
				
				this.interrupt();
				
				System.out.println(this.isInterrupted() + " " + "Finished Thread");
				
				break;
			}
			//System.out.println(this.isInterrupted());
			
		}
		System.out.println("done");
		
	}

}
