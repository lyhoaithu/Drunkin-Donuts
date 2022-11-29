package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangePasswordPage extends Page{

	public ChangePasswordPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
public By iconUser=By.xpath("//a[@class='fa-solid fa-user ']");
public By linkTextChangePassword=By.xpath("//a[contains(@href,'change-password')]");
public By txtOldPassword= By.name("old_pass");
public By txtNewPassword= By.name("new_pass");
public By txtConfirmationPassword= By.name("new_pass1");
public By chbShowPassword= By.xpath("//input[@type='checkbox']");
public By btnChangePassword=By.xpath("//input[@type='submit']");
public By linktextReturn= By.xpath("//a[@onclick='history.back()']");
public By lblErrorMessage=By.className("error");
}
