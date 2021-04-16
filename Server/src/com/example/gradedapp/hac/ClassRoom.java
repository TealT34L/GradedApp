package com.example.gradedapp.hac;

import java.io.Serializable;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ClassRoom implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5354037195473564502L;
	private String name;
	private double grade;
	private ArrayList<Assignment> assignments = new ArrayList<>();

	public ClassRoom(String string, WebElement webElement) {
		
		name = string;
		
		ArrayList<WebElement> dataRows = (ArrayList<WebElement>) webElement.findElements(By.className("sg-asp-table-data-row"));
		
		for (WebElement w : dataRows) {
			ArrayList<WebElement> datasOfRow = (ArrayList<WebElement>) w.findElements(By.tagName("td"));
			assignments.add(new Assignment(datasOfRow));
		}
		if (!assignments.isEmpty()) {
			assignments.remove(assignments.size()-1);
		}
		
		if (!assignments.isEmpty()) {
			String percent = webElement.findElement(By.className("sg-header-square")).getText().split(" ")[webElement.findElement(By.className("sg-header-square")).getText().split(" ").length-1];
			grade = Double.parseDouble(percent.substring(0, percent.length()-2));
		} else {
			grade = -1;
		}
		
		boolean hasGrades = false;
		for (Assignment a : assignments) {
			if (a.hasScorePoints()) {
				hasGrades = true;
			}
		}
		if (!hasGrades) {grade = -1;}
	}
	
	public ClassRoom(String name, double grade) {
		this.name = name;
		this.grade = grade;
	}

	public String getName(){return name;}

	public double getGrade(){return grade;}

	public ArrayList<Assignment> getAssignments(){return assignments;}
	
}
