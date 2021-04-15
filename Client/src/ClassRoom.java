import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class ClassRoom implements Serializable{
	
	
	private static final long serialVersionUID = -5354037195473564502L;
	String name;
	double grade;
	ArrayList<Assignment> assignments = new ArrayList<>();

	public ClassRoom(String text) {
		Scanner sc = new Scanner(text);
		name = sc.nextLine();
		if (sc.hasNextLine()) {
			sc.nextLine();
			String grades = sc.nextLine();
			String[] g = grades.split(" ");
			grade = Double.parseDouble(g[g.length-1].substring(0, g[g.length-1].length()-1));
			sc.nextLine();
			while (sc.hasNextLine()) {
				assignments.add(new Assignment(sc.nextLine()));
			}
		} else {
			grade = -1;
		}
		sc.close();
	}

}
