package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogInPage extends Page {

	public LogInPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public By txtEmail = By.name("eot");
	public By txtPassword = By.name("password");
	public By checkboxShowPassword = By.xpath("//input[@type='checkbox']");
	public By btnLogIn = By.xpath("//input[@type='submit']");
	public By lblErrorMessage = By.className("error");
}
