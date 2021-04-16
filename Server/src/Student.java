import java.io.Serializable;
import java.util.ArrayList;

import com.example.gradedapp.hac.ClassRoom;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Student implements Serializable{
	
	//private static final long serialVersionUID = 1L;
	private ArrayList<ClassRoom> classrooms = new ArrayList<>();
	private Transcript transcript;
	private WebDriver driver;
	
	public Student() {

    	// get driver and open browser
		System.setProperty("webdriver.chrome.driver","Server\\libs\\chromedriver.exe");
		driver = new ChromeDriver();
		//driver.manage().window().setPosition(new Point(0, 0));
		String baseUrl = "https://hac.friscoisd.org/HomeAccess/Account/LogOn?ReturnUrl=%2fHomeAccess%2fHome%2fSchoolLinks";

        // launch Fire fox and direct it to the Base URL
        driver.get(baseUrl);
	}
	
	public boolean validate(String username, String password) {
        /*
        // get the actual value of the title
        String actualTitle = driver.getTitle();

        // print title
        System.out.println(actualTitle);
        */
        //get element
        WebElement element = driver.findElement(By.name("LogOnDetails.UserName"));
        element.clear();
        //logging
        
        //System.out.println("Please enter com.example.gradedapp.hac username");
        
        //element.sendKeys("DeJesus-Contreras.M.A");
        
        element.sendKeys(username);
        
        element = driver.findElement(By.name("LogOnDetails.Password"));
         
        //System.out.println("Please enter com.example.gradedapp.hac password");
        
        //element.sendKeys("2735704G");
        element.sendKeys(password);
        
        element.submit();
        WebElement failed = null;
        try {
        	failed = driver.findElement(By.className("validation-summary-errors"));
        	//System.out.println(failed.findElement(By.tagName("span")).getText());
        } catch(NoSuchElementException e) {}
        return failed == null;
	}
	
	public void refresh() {

        //opening classes tab

		driver.findElement(By.id("hac-Classes")).click();
		driver.findElement(By.id("hac-Classes")).click();
        //switching to grades frame
        
        WebElement frame = driver.findElement(By.id("sg-legacy-iframe"));
        driver.switchTo().frame(frame);
        //going to form and printing
        ArrayList<WebElement> fullPage = (ArrayList<WebElement>) driver.findElements(By.className("AssignmentClass"));
        System.out.println(fullPage.size() + " is size");
        for (WebElement c : fullPage) {
        	ClassRoom h;
        	try {
        		h = new ClassRoom(c.findElement(By.className("sg-header-heading")).getText(), c);
        	} catch(NoSuchElementException e) {
        		System.out.println("Error " + c.findElement(By.className("sg-header-heading")).getText());
        		e.printStackTrace();
        		h = new ClassRoom(c.findElement(By.className("sg-header-heading")).getText(), -1);
        	}
    		classrooms.add(h);

        }
        driver.switchTo().defaultContent();
        
        driver.findElement(By.id("hac-Grades")).click();
		driver.findElement(By.id("hac-nav-submenu-Grades-Transcript")).click();
		frame = driver.findElement(By.id("sg-legacy-iframe"));
		driver.switchTo().frame(frame);
		
		transcript = new Transcript((ArrayList<WebElement>) driver.findElements(By.className("sg-transcript-group")));
		
		
		driver.switchTo().defaultContent();
	}
	
	public ArrayList<ClassRoom> getClasses(){return classrooms;}
	
	public ArrayList<ClassRoom> getOldClasses(){
		
		return transcript.getSemesterClasses();
		
	}
	
	public void close() {
		driver.close();
	}
	
}
