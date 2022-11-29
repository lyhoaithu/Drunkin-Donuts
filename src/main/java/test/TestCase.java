package test;

import org.junit.After;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import common.TestBase;
import pages.LogInPage;

public class TestCase extends TestBase{
@BeforeMethod(alwaysRun = true)
public void openTheBroswer() {
	openBrowser("edge");
}

@AfterMethod(alwaysRun = true)
public void closeBrowser() {
	driver.quit();
}



public void postLogIn(String email, String password) {
	LogInPage lp = new LogInPage(driver);
	driver.navigate().to("http://localhost:8081/DrunkinDonut/user/pages/account/login.php");
	sendKeys(lp.txtEmail, email);
	sendKeys(lp.txtPassword, password);
	clickOnElement(lp.btnLogIn);
}

}

