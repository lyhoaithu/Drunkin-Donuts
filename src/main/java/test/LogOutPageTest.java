package test;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import pages.LogInPage;
import pages.LogOutPage;

public class LogOutPageTest extends TestCase{
	public LogInPage lp= new LogInPage(driver);
	public LogOutPage lop= new LogOutPage(driver);
@Test(description = "Validate Logging Out", groups = "main case")
public void validateLogOut() {
	driver.navigate().to("http://localhost:8081/DrunkinDonut/pages/account/login.php");
	sendKeys(lp.txtEmail, "0123456789");
	sendKeys(lp.txtPassword, "123456");
	clickOnElement(lp.btnLogIn);
	hoverMouse(lop.iconUser);
	clickOnElement(lop.linktextLogOut);
	String currentURL= driver.getCurrentUrl();
	assertTrue(currentURL.contains("index"));

}
}
