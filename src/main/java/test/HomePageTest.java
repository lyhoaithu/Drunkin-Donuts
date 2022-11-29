package test;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import pages.HomePage;

public class HomePageTest extends TestCase{
	public String homePageLink= "http://localhost:8081/DrunkinDonut/index.php";
@Test (description = "Navigate To Log In Page")
public void navigateToLogInPage() {
	HomePage hp= new HomePage(driver);
	driver.navigate().to(homePageLink);
	hoverMouse(hp.iconUser);
	clickOnElement(hp.linktextLogIn);
	String currentURL= driver.getCurrentUrl();
	assertTrue(currentURL.contains("account/login"));
}
}
