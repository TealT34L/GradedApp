package com.example.gradedapp.hac;

import java.io.Serializable;

public class Assignment implements Serializable{
	
	private static final long serialVersionUID = 3156058131135601315L;
	private int[] dueDate = new int[3];
	private int[] dateAssigned = new int[3];
	private String title;
	private String category;
	private double scorePoints;
	private double totalPoints;
	private double percentage;
	private boolean hasScorePoints;
	private boolean hasTotalPoints;
	
	public Assignment(String str) {
		String[] split = str.split(" ");
		String[] dateDue = split[0].split("/");
		String[] assignedDate = split[1].split("/");
		try {
			for (int x = 0; x < 3; x++) {
				dueDate[x] = Integer.parseInt(dateDue[x]);
				dateAssigned[x] = Integer.parseInt(assignedDate[x]);
			}
		} catch(NumberFormatException e ) {
			
		}
		
		if (str.contains("Minor Grades")) {
			category = "Minor Grade";
		} else if (str.contains("Major Grades")) {
			category = "Major Grade";
		} else if (str.contains("Non-graded")) {
			category = "Non-graded";
		} else {
			category = "NULL";
		}
		
		if (dateAssigned[0] != 0) {
			title = str.substring(21, str.indexOf(category));
		} else {
			title = str.substring(12, str.indexOf(category));
		}
		try {
			scorePoints = Double.parseDouble(split[split.length-2]);
			totalPoints = Double.parseDouble(split[split.length-1]);
			percentage = 100 * (scorePoints/totalPoints);
		} catch(NumberFormatException e ) {
			scorePoints = -1;
			totalPoints = -1;
			percentage = -1;
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
