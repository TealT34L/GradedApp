import java.io.Serializable;
import java.util.ArrayList;

import org.openqa.selenium.WebElement;

public class Assignment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3156058131135601315L;
	private int[] dueDate = new int[3];
	private int[] dateAssigned = new int[3];
	private String title;
	private String category;
	private double scorePoints;
	private double totalPoints;
	private double percentage;
	private boolean hasScorePoints = false;
	private boolean hasTotalPoints = false;
	
	public Assignment(ArrayList<WebElement> datasOfRow) {
		
		String[] dateDue = datasOfRow.get(0).getText().split("/");
		String[] assignedDate = datasOfRow.get(1).getText().split("/");
		try {
			for (int x = 0; x < 3; x++) {
				dueDate[x] = Integer.parseInt(dateDue[x]);
				dateAssigned[x] = Integer.parseInt(assignedDate[x]);
			}
		} catch(NumberFormatException e ) {
			
		}
		
		title = datasOfRow.get(2).getText();
		category = datasOfRow.get(3).getText();
		
		
		try {
			scorePoints = Double.parseDouble(datasOfRow.get(4).getText());
			hasScorePoints = true;
		} catch(NumberFormatException e) {
			hasScorePoints = false;
		}		
		try {
			totalPoints = Double.parseDouble(datasOfRow.get(5).getText());
			hasTotalPoints = true;
		} catch(NumberFormatException e) {
			hasTotalPoints = false;
		}
		if (hasScorePoints && hasTotalPoints) {
			percentage = (scorePoints/totalPoints)*100;
		}
	}

	public String getTitle() {
		return title;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getDueDate() {
		return dueDate[0] + "/" + dueDate[1] + "/" + dueDate[2];
	}
	
	public String getDateAssigned() {
		return dateAssigned[0] + "/" + dateAssigned[1] + "/" + dateAssigned[2];
	}

	public double getPercentage() {
		return percentage;
	}
	
	public double getScorePoints() {
		return scorePoints;
	}
	
	public double getTotalPoints() {
		return totalPoints;
	}
	
	public boolean hasScorePoints() {
		return hasScorePoints;
	}
	
	public boolean hasTotalPoints() {
		return hasTotalPoints;
	}
	
}
