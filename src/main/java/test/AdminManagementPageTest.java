package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.ExcelUtils;
import pages.AdminManagementPage;
import pages.LogInPage;

public class AdminManagementPageTest extends TestCase {
	public AdminManagementPage amp = new AdminManagementPage(driver);
	public String pagePath = "http://localhost:8081/DrunkinDonut/admin/pages/account/manage-account.php";
	public ExcelUtils excelUtils = new ExcelUtils();



	@Test(description = "Validate navigate to Admin Account Management page", groups = {"main case", "Admin Login"}, priority = 1)
	public void navigateToAdminAccountManagementPage() {
		hoverMouse(amp.iconUser);
		clickOnElement(amp.linktextManagement);
		clickOnElement(amp.iconAccountManage);
		String currentURL= driver.getCurrentUrl();
		assertTrue(currentURL.contains("account/manage-account"));
	}
	
	@Test(description = "Verify Displayed Information", groups = {"validate case", "Admin Login"}, priority = 2)
	public void verifyDisplayedInformation() throws ClassNotFoundException, SQLException {
		driver.navigate().to(pagePath);
		String[] command= {"SELECT AccountID from `account` order by AccountID;", "SELECT AccName from `account` order by AccountID;", "SELECT AccPhoneNo from `account` order by AccountID;","SELECT AccEmail from `account` order by AccountID;","SELECT  RoleName from `role`INNER JOIN `account`on role.RoleID= account.RoleID order by account.AccountID;"};
		int[] columnIndex= {1,1,1,1,1};
		By[] locator= {amp.lblID, amp.lblName, amp.lblPhone, amp.lblEmail, amp.lblRole};
		validateWebDisplayedResult(locator, command, columnIndex);
	}

	@DataProvider(name = "Search Successfully Data")
	public String[][] searchSuccessfullyData() throws IOException {
		String[][] data = excelUtils.getDataFromExcel("SearchSuccessfully");
		return data;

	}

	@Test(description = "Validate Search Account Successfully By Name And Email", groups = { "validate case",
			"Admin Login" }, priority = 3, dataProvider = "Search Successfully Data")
	public void validateSearchSuccessfully(String value, String command) throws ClassNotFoundException, SQLException {
		driver.navigate().to(pagePath);
		sendKeys(amp.txtSearchBox, value);
		clickOnElement(amp.iconSearch);
		By[] locator = { amp.lblID, amp.lblName, amp.lblPhone, amp.lblEmail, amp.lblRole };
		String actualResult = getDisplayedInformationFromWebTable(locator);
		String[] expectedResultArr = new String[locator.length];
		for (int i = 0; i < locator.length; i++) {
			expectedResultArr[i] = getDataFromDataBase(command, i+1);
		}

		String expectedResult = Arrays.deepToString(expectedResultArr).replace("]", "").replace("[", "");
		assertEquals(actualResult, expectedResult);
	}

	@DataProvider(name = "Search Fail Data", indices = { 0, 1, 2 })
	public String[][] searchFailData() throws IOException {
		String[][] data = excelUtils.getDataFromExcel("SearchFailed");
		return data;
	}

	@Test(description = "Validate Search Account Fail Using ID, PhoneNo and Role", groups = { "validate case",
			"Admin Login" }, priority = 4, dataProvider = "Search Fail Data")
	public void validateSearchFail(String value, String expectedResult) throws ClassNotFoundException, SQLException {
		driver.navigate().to(pagePath);
		sendKeys(amp.txtSearchBox, value);
		clickOnElement(amp.iconSearch);
		String actualResult = driver.findElement(amp.lblSearchMessage).getText();
		assertEquals(actualResult, expectedResult);
	}

//	@DataProvider(name = "Search With No Information Found Data", indices = { 3 })
//	public String[][] searchWithNoResultData() throws IOException {
//		String[][] data = excelUtils.getDataFromExcel("SearchFailed");
//		return data;
//	}
//
//	@Test(description = "Validate Search With No Information Found", groups = { "validate case",
//			"Admin Login" }, priority = 3, dataProvider = "Search With No Information Found Data")
//	public void validateSearchWithNoResultFound(String value, String expectedResult)
//			throws ClassNotFoundException, SQLException {
//		driver.navigate().to(pagePath);
//		sendKeys(amp.txtSearchBox, value);
//		clickOnElement(amp.iconSearch);
//		String actualResult = driver.findElement(amp.lblSearchMessage).getText();
//		assertEquals(actualResult, expectedResult);
//	}

//	@DataProvider(name = "Search With Blank Field Data", indices = { 4 })
//	public String[][] searchWithBlankFieldData() throws IOException {
//		String[][] data = excelUtils.getDataFromExcel("SearchFailed");
//		return data;
//	}
//
//	@Test(description = "Search With Field Blankk", groups = { "validate case",
//			"Admin Login" }, priority = 5, dataProvider = "Search With Blank Field Data")
//	public void validateSearchBlankField(String value, String expectedResult)
//			throws ClassNotFoundException, SQLException {
//		driver.navigate().to(pagePath);
//		sendKeys(amp.txtSearchBox, value);
//		clickOnElement(amp.iconSearch);
//		String actualResult = getHtml5ValidationMessage(amp.txtSearchBox);
//		assertEquals(actualResult, expectedResult);
//	}
}
