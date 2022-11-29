package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.ExcelUtils;
import pages.LogInPage;

public class LogInPageTest extends TestCase {
public String logInLink="http://localhost:8081/DrunkinDonut/pages/account/login.php";
public LogInPage lp= new LogInPage(driver);
public ExcelUtils excelUtils= new ExcelUtils();

@BeforeMethod
public void navigateToLogInPage() {
	driver.navigate().to(logInLink);
}

@DataProvider(name = "Log In Successfully Data")
public String[][] logInSuccessfullyData() throws IOException {
	String[][] data= excelUtils.getDataFromExcel("LogInSuccessfully");
	return data;

}
@Test(description = "Validate Log In Successfully", groups = "main case", dataProvider = "Log In Successfully Data")
public void logInSuccessfully(String email, String password) {
	sendKeys(lp.txtEmail, email);
	sendKeys(lp.txtPassword, password);
	clickOnElement(lp.btnLogIn);
String currentURL= driver.getCurrentUrl();
assertTrue(currentURL.contains("DrunkinDonut/index.php"));
}

@DataProvider(name = "Log In Using Incorrect Data", indices = {0,1,2})
public String[][] incorrectData() throws IOException{
	String[][] data= excelUtils.getDataFromExcel("LogInFail");
	return data;
}
@Test(description = "Validate Log In Fail When Provide Incorrect Email/Phone or Password", groups = "validate case", dataProvider = "Log In Using Incorrect Data")
public void logInFailUsingIncorrectData(String email, String password, String expectedMessage) {
	sendKeys(lp.txtEmail, email);
	sendKeys(lp.txtPassword, password);
	clickOnElement(lp.btnLogIn);
	String actualMessage= driver.findElement(lp.lblErrorMessage).getText();
	assertEquals(actualMessage, expectedMessage);

}

@DataProvider(name = "Log In When Leaving Field Blank Data", indices = {3,4})
public String[][] fieldBlankData() throws IOException{
	String[][] data= excelUtils.getDataFromExcel("LogInFail");
	return data;
}

@Test(description = "Validate Log In Fail When Leaving Compulsory Field Blank", groups = "validate case", dataProvider = "Log In When Leaving Field Blank Data")
public void logInFailLeavingFieldBlank(String email, String password, String expectedMessage) {
	By[] locator= new By[]{lp.txtEmail, lp.txtPassword};
	String[] value= new String[] {email, password};
	getHtml5ValidationFromASeriesOfFields(locator, value, lp.btnLogIn, expectedMessage);
}


@Test(description = "Validate Password Is Hidden When Being Typed", groups = "validate case")
public void validatePasswordIsHidden() {
	sendKeys(lp.txtPassword, "123456");
	String type=driver.findElement(lp.txtPassword).getAttribute("type");
	assertEquals(type, "password");
}

@Test(description = "Validate Password Is In Text Form When Click On Show Password", groups = "validate case")
public void validatePasswordIsNotHidden() {
	sendKeys(lp.txtPassword, "123456");
	clickOnElement(lp.checkboxShowPassword);
	String type=driver.findElement(lp.txtPassword).getAttribute("type");
	assertEquals(type, "text");
}
}


