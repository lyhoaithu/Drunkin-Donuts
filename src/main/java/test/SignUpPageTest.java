package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.ExcelUtils;
import pages.SignUpPage;

public class SignUpPageTest extends TestCase{
public ExcelUtils excelUtils= new ExcelUtils();
public SignUpPage sup= new SignUpPage(driver);
public String pagePath= "http://localhost:8081/DrunkinDonut/user/pages/account/register.php";

@Test(description = "Navigate To Register Page", groups = "main case")
public void navigateToRegisterPage() {
	driver.navigate().to("http://localhost:8081/DrunkinDonut/index.php");
	hoverMouse(sup.iconUser);
	clickOnElement(sup.linktextLogIn);
	clickOnElement(sup.linktextRegister);
	String currentURL= driver.getCurrentUrl();
	assertTrue(currentURL.contains(pagePath));
}

@DataProvider(name = "Successfull Data")
public String[][] successfullyData() throws IOException {
	String[][] data=excelUtils.getDataFromExcel("SignUpSuccessfully");
	return data;
}

@Test(description = "Validate Sign Up Successfully", dataProvider = "Successfull Data", groups="main case", priority = 1)
public void signUpSuccessfully(String name, String password, String email, String phone, String expectedMessage) {
	driver.navigate().to(pagePath);
	sendKeys(sup.txtName, name);
	sendKeys(sup.txtPassword, password);
	sendKeys(sup.txtEmail, email);
	sendKeys(sup.txtPhoneNumber, phone);
	clickOnElement(sup.btnCreateAccount);
	String actualMessage= driver.findElement(sup.lblExpectedSuccessMessage).getText();
	assertEquals(expectedMessage, actualMessage);
}

@DataProvider(name = "Invalid Data", indices = {0,1,2,3,4,5,6,7,8,9})
public String[][] invalidData() throws IOException{
String[][] data=excelUtils.getDataFromExcel("SignUpFail");
return data;
}

@Test(description = "Validate Sign Up Fail Using Invalid Data", dataProvider = "Invalid Data", groups="validate case", priority = 2)
public void signUpFailUsingInvalidData(String name, String password, String email, String phone, String expectedMessage) {
	driver.navigate().to(pagePath);
	sendKeys(sup.txtName, name);
	sendKeys(sup.txtPassword, password);
	sendKeys(sup.txtEmail, email);
	sendKeys(sup.txtPhoneNumber, phone);
	clickOnElement(sup.btnCreateAccount);
	String actualMessage= driver.findElement(sup.lblExpectedErrorMessage).getText();
	assertEquals(expectedMessage, actualMessage);
}

@DataProvider(name = "Field Blank Data", indices = {14,15,16,17})
public String[][] fieldBlankData() throws IOException{
String[][] data=excelUtils.getDataFromExcel("SignUpFail");
return data;
}

@Test(description = "Validate Sign Up Fail When Leaving Compulsory Field Blank", dataProvider = "Field Blank Data", groups="validate case", priority = 3)
public void signUpFailLeavingFieldBlank(String name, String password, String email, String phone, String expectedMessage) {
	driver.navigate().to(pagePath);
	By[] locator= {sup.txtName, sup.txtPassword, sup.txtEmail, sup.txtPhoneNumber};
	String [] value= {name, password, email, phone};
	getHtml5ValidationFromASeriesOfFields(locator, value, sup.btnCreateAccount, expectedMessage);
}

@DataProvider(name = "Registered Data", indices = {18,19})
public String[][] registeredData() throws IOException{
String[][] data=excelUtils.getDataFromExcel("SignUpFail");
return data;
}

@Test(description = "Validate Sign Up Fail When Using Registered Phonenumber/Email", dataProvider = "Registered Data", groups="validate case", priority = 4)
public void signUpFailUsingRegisteredData(String name, String password, String email, String phone, String expectedMessage) {
	driver.navigate().to(pagePath);
	sendKeys(sup.txtName, name);
	sendKeys(sup.txtPassword, password);
	sendKeys(sup.txtEmail, email);
	sendKeys(sup.txtPhoneNumber, phone);
	clickOnElement(sup.btnCreateAccount);
	String actualMessage= driver.findElement(sup.lblExpectedErrorMessage).getText();
	assertEquals(expectedMessage, actualMessage);
}

@Test(description = "Validate Navigating To Log In Page When Clicking On Return", priority = 5)
public void clickOnReturn() {
	driver.navigate().to(pagePath);
	sendKeys(sup.txtName, "testing");
	sendKeys(sup.txtPassword, "Abc#012");
	sendKeys(sup.txtEmail, "testing@gmail.com");
	sendKeys(sup.txtPhoneNumber, "0123456784");
	clickOnElement(sup.linktextReturn);
	String currentURL= driver.getCurrentUrl();
	assertTrue(currentURL.contains("login"));
}

@Test(description = "Validate Password Is Hidden By Dot Icon When Typed", priority = 6)
public void passwordIsHidden() {
	driver.navigate().to(pagePath);
	sendKeys(sup.txtPassword, "Abc#012");
	String actualType=driver.findElement(sup.txtPassword).getAttribute("type");
	assertEquals(actualType, "password");
}

@Test(description = "Validate Password Is Shown In Text Form When Clicking On Show Password", priority = 7)
public void passwordIsNotHidden() {
	driver.navigate().to(pagePath);
	sendKeys(sup.txtPassword, "Abc#012");
	clickOnElement(sup.chbShowPassword);
	String actualType=driver.findElement(sup.txtPassword).getAttribute("type");
	assertEquals(actualType, "text");
}
}
