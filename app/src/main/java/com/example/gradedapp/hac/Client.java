package com.example.gradedapp.hac;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

	private boolean valid = false;

	private ArrayList<ClassRoom> classes;
	private ArrayList<ClassRoom> oldClasses;
	private double writtenGPA = -1.0;

	private Socket socket;
	private ObjectOutputStream pw;
	private ObjectInputStream oin;

	public Client() throws IOException {
		this.socket = new Socket("192.168.2.176", 1195);
		this.pw = new ObjectOutputStream(socket.getOutputStream());
		this.oin = new ObjectInputStream(socket.getInputStream());
		System.out.println("Connected");

	}

	public boolean validate(String userName, String password) throws IOException{
		pw.writeInt(2);
		
		pw.writeUTF(userName);
		pw.writeUTF(password);
		pw.flush();
		this.valid = oin.readBoolean();
		return valid;
	}
	
	public boolean getValidity() {return valid;}

	public void refresh() throws UnknownHostException, IOException, ClassNotFoundException {
		if (valid) {
			//Scanner in = new Scanner(System.in);
			pw.writeInt(0);
			pw.flush();
			classes = (ArrayList<ClassRoom>) oin.readObject();
			oldClasses = (ArrayList<ClassRoom>) oin.readObject();
		}
	}
	
	public void closeThread() throws IOException {
		System.out.println("Closing Thread...");
		pw.writeInt(1);
		pw.flush();
		socket.close();
	}
	
	public void displayClasses() {
		if (valid) {
			for (ClassRoom room : classes) {
				System.out.println("\n" + room.getName() + "  " + room.getGrade() + " " + room.getAssignments().size());
				for (Assignment as : room.getAssignments()) {
					System.out.println(as.getTitle() + " " + as.getCategory() + " " + as.getPercentage());
				}
			}
			
			System.out.println("\n\n\n\n");
			for (ClassRoom room : oldClasses) {
				System.out.println("\n" + room.getName() + "  " + room.getGrade() + " " + room.getAssignments().size());
			}
		}
	}

	public ArrayList<ClassRoom> getClasses(){return classes;}

	public ArrayList<ClassRoom> getOldClasses(){return oldClasses;}


}
