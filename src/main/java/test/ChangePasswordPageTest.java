package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.ExcelUtils;
import pages.ChangePasswordPage;
import pages.LogInPage;

public class ChangePasswordPageTest extends TestCase{
	
	
public String pagePath="http://localhost:8081/DrunkinDonut/user/pages/account/change-password.php";
public ChangePasswordPage cpp=new ChangePasswordPage(driver);
public ExcelUtils excelUtils= new ExcelUtils();

@BeforeMethod(description = "Log In For Validate Case",  onlyForGroups =  "Log In Validate")
public void preConLogIn() {
	LogInPage lp= new LogInPage(driver);
	driver.navigate().to("http://localhost:8081/DrunkinDonut/user/pages/account/login.php");
	sendKeys(lp.txtEmail, "0123456788");
	sendKeys(lp.txtPassword, "Pc#01a");
	clickOnElement(lp.btnLogIn);
}

@BeforeMethod(description = "Log In For First Successfully Case", onlyForGroups  = "first successfully")
public void firstSuccessfully() {
	LogInPage lp= new LogInPage(driver);
	driver.navigate().to("http://localhost:8081/DrunkinDonut/user/pages/account/login.php");
	sendKeys(lp.txtEmail, "0123456788");
	sendKeys(lp.txtPassword, "123456");
	clickOnElement(lp.btnLogIn);
}

@BeforeMethod(description = "Log In For Second Successfully Case", onlyForGroups  = "second successfully")
public void secondSuccessfully() {
	LogInPage lp= new LogInPage(driver);
	driver.navigate().to("http://localhost:8081/DrunkinDonut/user/pages/account/login.php");
	sendKeys(lp.txtEmail, "0123456788");
	sendKeys(lp.txtPassword, "Passchange#01abcabcabcabcabcabca");
	clickOnElement(lp.btnLogIn);
}

@Test(description = "Validate Navigating To Change Password Page", groups = {"main case", "first successfully"})
public void navigateToChangePassPage() {
hoverMouse(cpp.iconUser);
clickOnElement(cpp.linkTextChangePassword);
String currentURL= driver.getCurrentUrl();
assertTrue(currentURL.contains("change-password"));
}

@DataProvider (name = "Successfully First", indices = {0})
public String[][] firstSuccessfullyData() throws IOException {
	String [][]data= excelUtils.getDataFromExcel("ChangePasswordSuccessfully");
	return data;
}

@Test(description = "Validate Change Password Successfully", groups = {"first successfully", "main case"}, dataProvider = "Successfully First", priority = 1)
public void firstSuccessfully(String oldPass, String newPass, String confirmPass) {
	driver.navigate().to(pagePath);
	sendKeys(cpp.txtOldPassword, oldPass);
	sendKeys(cpp.txtNewPassword, newPass);
sendKeys(cpp.txtConfirmationPassword, confirmPass);
clickOnElement(cpp.btnChangePassword);
String currentURL=driver.getCurrentUrl();
assertTrue(currentURL.contains("login"));
}

@DataProvider (name = "Successfully Second", indices = {1})
public String[][] secondSuccessfullyData() throws IOException {
	String [][]data= excelUtils.getDataFromExcel("ChangePasswordSuccessfully");
	return data;
}

@Test(description = "Validate Change Password Successfully", groups = {"second successfully", "main case"}, dataProvider = "Successfully Second", priority = 2)
public void secondSuccessfully(String oldPass, String newPass, String confirmPass) {
	driver.navigate().to(pagePath);
	sendKeys(cpp.txtOldPassword, oldPass);
	sendKeys(cpp.txtNewPassword, newPass);
sendKeys(cpp.txtConfirmationPassword, confirmPass);
clickOnElement(cpp.btnChangePassword);
String currentURL=driver.getCurrentUrl();
assertTrue(currentURL.contains("login"));
}

@DataProvider(name = "Inmatching new pass and confirm pass", indices = {7})
public String[][] inmatchingData() throws IOException {
	String [][]data= excelUtils.getDataFromExcel("ChangePasswordFail");
	return data;
}

@Test(description = "Validate Change Password Fail When New and Confirm Password Don't Match", dataProvider = "Inmatching new pass and confirm pass", groups = {"validate case", "Log In Validate"}, priority = 3)
public void inmatchingNewAndConfirmPass(String oldPass, String newPass, String confirmPass, String expectedMessage) {
	driver.navigate().to(pagePath);
	sendKeys(cpp.txtOldPassword, oldPass);
sendKeys(cpp.txtNewPassword, newPass);
sendKeys(cpp.txtConfirmationPassword, confirmPass);
clickOnElement(cpp.btnChangePassword);
String actualMessage=driver.findElement(cpp.lblErrorMessage).getText();
assertEquals(expectedMessage, actualMessage);
}

@DataProvider(name = "Same New Pass Like Old Pass", indices = {8})
public String[][] samePassData() throws IOException {
	String [][]data= excelUtils.getDataFromExcel("ChangePasswordFail");
	return data;
}

@Test(description = "Validate Change Password Fail When New and Confirm Password Don't Match", dataProvider = "Same New Pass Like Old Pass", groups = {"validate case", "Log In Validate"}, priority = 4)
public void samePass(String oldPass, String newPass, String confirmPass, String expectedMessage) {
	driver.navigate().to(pagePath);
	sendKeys(cpp.txtOldPassword, oldPass);
sendKeys(cpp.txtNewPassword, newPass);
sendKeys(cpp.txtConfirmationPassword, confirmPass);
clickOnElement(cpp.btnChangePassword);
String actualMessage=driver.findElement(cpp.lblErrorMessage).getText();
assertEquals(expectedMessage, actualMessage);
}

@DataProvider(name = "Field Blank Data", indices = {3,4,5})
public String[][] fieldBlankData() throws IOException {
	String [][]data= excelUtils.getDataFromExcel("ChangePasswordFail");
	return data;
}

@Test(description = "Validate Change Password Fail When Leaving Compulsory Field Blank", dataProvider = "Field Blank Data", groups = {"validate case", "Log In Validate"}, priority = 5)
public void fieldBlank(String oldPass, String newPass, String confirmPass, String expectedMessage) {
	driver.navigate().to(pagePath);
	String [] value= {oldPass, newPass, confirmPass};
By [] locator= {cpp.txtOldPassword, cpp.txtNewPassword, cpp.txtConfirmationPassword};
getHtml5ValidationFromASeriesOfFields(locator, value, cpp.btnChangePassword, expectedMessage);
}

@DataProvider(name = "Invalid Data", indices = {0,1,2})
public String[][] invalidData() throws IOException {
	String [][]data= excelUtils.getDataFromExcel("ChangePasswordFail");
	return data;
}

@Test(description = "Validate Change Password Fail When Providing Invalid Data", dataProvider = "Invalid Data", groups = {"validate case", "Log In Validate"}, priority = 6)
public void invalid(String oldPass, String newPass, String confirmPass, String expectedMessage) {
	driver.navigate().to(pagePath);
	sendKeys(cpp.txtOldPassword, oldPass);
sendKeys(cpp.txtNewPassword, newPass);
sendKeys(cpp.txtConfirmationPassword, confirmPass);
clickOnElement(cpp.btnChangePassword);
String actualMessage= driver.findElement(cpp.lblErrorMessage).getText();
assertEquals(expectedMessage, actualMessage);
}

@DataProvider(name = "Incorrect Old Password Data", indices = {6})
public String[][] incorrectOldPass() throws IOException {
	String [][]data= excelUtils.getDataFromExcel("ChangePasswordFail");
	return data;
}

@Test(description = "Validate Change Password Fail When Providing Invalid Data", dataProvider = "Incorrect Old Password Data", groups = {"validate case", "Log In Validate"}, priority = 7)
public void incorrectOldPassword(String oldPass, String newPass, String confirmPass, String expectedMessage) {
	driver.navigate().to(pagePath);
	sendKeys(cpp.txtOldPassword, oldPass);
sendKeys(cpp.txtNewPassword, newPass);
sendKeys(cpp.txtConfirmationPassword, confirmPass);
clickOnElement(cpp.btnChangePassword);
String actualMessage= driver.findElement(cpp.lblErrorMessage).getText();
assertEquals(expectedMessage, actualMessage);
}

//The below TCs are fail due to the above TC is fail

@Test(description = "Validate Change Password Unsuccessfully And Go Back Home Page When Clicking Return", groups = {"validate case", "Log In Validate"}, priority = 8)
public void goBackHomePage() {
	driver.navigate().to(pagePath);
	sendKeys(cpp.txtOldPassword, "Pc#01a");
	sendKeys(cpp.txtNewPassword, "Passchange#01");
	sendKeys(cpp.txtConfirmationPassword, "Passchange#01");
	clickOnElement(cpp.linktextReturn);
	String currentURL= driver.getCurrentUrl();
	assertTrue(currentURL.contains("index"));
}

@Test(description = "Validate Password Is Hidden When Typed", groups = {"validate case", "Log In Validate"}, priority = 9)
public void passwordIsHidden() {
	driver.navigate().to(pagePath);
	sendKeys(cpp.txtOldPassword, "Pc#01");
	sendKeys(cpp.txtNewPassword, "123456");
	sendKeys(cpp.txtConfirmationPassword, "123456");
	String[] actualResult= {driver.findElement(cpp.txtOldPassword).getAttribute("type"), driver.findElement(cpp.txtNewPassword).getAttribute("type"), driver.findElement(cpp.txtConfirmationPassword).getAttribute("type")};
	for (int i=0; i<actualResult.length;i++) {
		assertEquals(actualResult[i], "password");
	}
}

@Test(description = "Validate Password Is Hidden When Typed", groups = {"validate case", "Log In Validate"}, priority = 10)
public void passwordIsNotHidden() {
	driver.navigate().to(pagePath);
	sendKeys(cpp.txtOldPassword, "Pc#01");
	sendKeys(cpp.txtNewPassword, "123456");
	sendKeys(cpp.txtConfirmationPassword, "123456");
	clickOnElement(cpp.chbShowPassword);
	String[] actualResult= {driver.findElement(cpp.txtOldPassword).getAttribute("type"), driver.findElement(cpp.txtNewPassword).getAttribute("type"), driver.findElement(cpp.txtConfirmationPassword).getAttribute("type")};
	for (int i=0; i<actualResult.length;i++) {
		assertEquals(actualResult[i], "text");
	}
}
}

