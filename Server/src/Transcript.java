import java.io.Serializable;
import java.util.ArrayList;

import com.example.gradedapp.hac.ClassRoom;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Transcript implements Serializable {
	
	private static final long serialVersionUID = 3738109042284609900L;
	
	String yearsStr = "";
	int grade;
	String building = "";
	ArrayList<ClassRoom> semesterClasses = new ArrayList<>();
	
	public Transcript(ArrayList<WebElement> years) {
		for (int x = 0; x < years.size(); x++) {
			WebElement element = years.get(x);
			ArrayList<WebElement> time = (ArrayList<WebElement>) element.findElements(By.tagName("table"));
		
			WebElement e = time.get(0);
			yearsStr = e.findElement(By.id("plnMain_rpTranscriptGroup_lblYearValue_" + x)).getText();
			grade = Integer.parseInt(e.findElement(By.id("plnMain_rpTranscriptGroup_lblGradeValue_" + x)).getText());
			building = e.findElement(By.id("plnMain_rpTranscriptGroup_lblBuildingValue_" + x)).getText();
			
			e = time.get(1);
			
			ArrayList<WebElement> classes = (ArrayList<WebElement>) e.findElements(By.className("sg-asp-table-data-row"));
			for (int y = 0; y < classes.size(); y++) {
				ArrayList<WebElement> strs = (ArrayList<WebElement>) classes.get(y).findElements(By.tagName("td"));
				if (!strs.get(2).getText().equals(" ") && !strs.get(2).getText().equals("P")) {
					semesterClasses.add(new ClassRoom((strs.get(0).getText() + " " +strs.get(1).getText()), Integer.parseInt(strs.get(2).getText())));
				}
				if (!strs.get(3).getText().equals(" ") && !strs.get(2).getText().equals("P")) {
					semesterClasses.add(new ClassRoom((strs.get(0).getText() + " " +strs.get(1).getText()), Integer.parseInt(strs.get(3).getText())));
				}
			}
		}
	}

	public ArrayList<ClassRoom> getSemesterClasses() {
		return semesterClasses;
	}
}
