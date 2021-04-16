package com.example.gradedapp;

import com.example.gradedapp.hac.Assignment;
import com.example.gradedapp.hac.ClassRoom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

	private boolean valid = false;
	private boolean hasRefreshed = false;

	private ArrayList<ClassRoom> classes;
	private ArrayList<ClassRoom> oldClasses;
	private double writtenGPA = -1.0;

	private Socket socket;
	private ObjectOutputStream pw;
	private ObjectInputStream oin;
	private String username;
	private String password;

	public Client(String u, String p) throws IOException {
		username = u;
		password = p;
	}

	public boolean validate() throws IOException{
		pw.writeInt(2);
		
		pw.writeUTF(username);
		pw.writeUTF(password);
		pw.flush();
		valid = oin.readBoolean();
		return valid;
	}
	
	public boolean getValidity() {return valid;}

	public boolean hasRefreshed(){return hasRefreshed;}

	public void refresh() throws UnknownHostException, IOException, ClassNotFoundException {
		if (valid) {
			//Scanner in = new Scanner(System.in);
			pw.writeInt(0);
			pw.flush();
			classes = (ArrayList<ClassRoom>) oin.readObject();
			oldClasses = (ArrayList<ClassRoom>) oin.readObject();
			hasRefreshed = true;
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


	public void setup() throws IOException {
		socket = new Socket("192.168.2.91", 1195);
		pw = new ObjectOutputStream(socket.getOutputStream());
		oin = new ObjectInputStream(socket.getInputStream());
		System.out.println("Connected");
	}
}
