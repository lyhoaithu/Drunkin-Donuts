package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage extends Page {

	public SignUpPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public By iconUser = By.xpath("//a[@class='fa-solid fa-user ']");
	public By linktextLogIn = By.xpath("//a[contains(@href,'login')]");
	public By linktextRegister = By.xpath("//a[contains(@href,'register')]");
	public By txtName = By.name("user");
	public By txtPassword = By.name("password");
	public By txtEmail = By.name("email");
	public By txtPhoneNumber = By.name("telephone");
	public By btnCreateAccount = By.xpath("//input[@type='submit']");
	public By chbShowPassword = By.xpath("//input[@type='checkbox']");
	public By linktextReturn = By.xpath("//a[@onclick='history.back()']");
	public By lblExpectedErrorMessage= By.className("error");
	public By lblExpectedSuccessMessage= By.className("success");
}
