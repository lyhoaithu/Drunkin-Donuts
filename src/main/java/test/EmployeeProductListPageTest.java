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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.ExcelUtils;
import pages.EmployeeProductListPage;
import pages.LogInPage;

public class EmployeeProductListPageTest extends TestCase {
	public String path = "http://localhost:8081/DrunkinDonut/admin/pages/product/manage-product.php";
	public EmployeeProductListPage eplp = new EmployeeProductListPage(driver);
	public ExcelUtils excelUtils = new ExcelUtils();

	@BeforeMethod(description = "Log In", onlyForGroups = "Employee Log In")
	public void logIn() {
		LogInPage lp = new LogInPage(driver);
		driver.navigate().to("http://localhost:8081/DrunkinDonut/pages/account/login.php");
		sendKeys(lp.txtEmail, "iamyourjoy@gmail.com");
		sendKeys(lp.txtPassword, "123456");
		clickOnElement(lp.btnLogIn);
	}

	@Test(description = "Validate Navigating To Product Page By Clicking On The Icon At The Menubar", groups = {
			"main case", "Employee Log In" })
	public void clickOnIconAtMenubar() {
		driver.navigate().to("http://localhost:8081/DrunkinDonut/admin/index.php");
		clickOnElement(eplp.linktextProduct);
		String currentURL = driver.getCurrentUrl();
		assertTrue(currentURL.contains("product/manage-product"));
	}

	@Test(description = "Validate Navigating To Product Page By Clicking On The Product Card At Management Home Page", groups = {
			"main case", "Employee Log In" })
	public void clickOnIconCard() {
		driver.navigate().to("http://localhost:8081/DrunkinDonut/admin/index.php");
		clickOnElement(eplp.cardProduct);
		String currentURL = driver.getCurrentUrl();
		assertTrue(currentURL.contains("product/manage-product"));
	}

	@Test(description = "Validate Viewing Product Details", groups = { "main case", "Employee Log In" })
	public void viewingProductDetails() {
		driver.navigate().to(path);
		clickOnElement(eplp.iconView);
		String currentURL = driver.getCurrentUrl();
		assertTrue(currentURL.contains("view-product.php?pid=1"));
	}

	@Test(description = "Validate Displayed Information", groups = { "main case", "Employee Log In" })
	public void validateDisplayedInformation() throws ClassNotFoundException, SQLException {
		driver.navigate().to(path);
		String sqlCommand = "SELECT p.ProductID, c.CategoryName, p.ProductName, p.ProductQuantity FROM `product` p INNER JOIN categories c ON c.CategoryID=p.CategoryID order BY ProductID ASC limit 5;";
		By[] locatorList = { eplp.lblIDList, eplp.lblCatgoryNameList, eplp.lblProductNameList,
				eplp.lblQuantityList };
		ArrayList<String> actualDisplayedValue = new ArrayList<String>();
		ArrayList<String> expectedDisplayedValue = new ArrayList<String>();

		for (int i = 0; i < locatorList.length; i++) {
			List<WebElement> actualValue = driver.findElements(locatorList[i]);
			String[] actualValueArr = new String[actualValue.size()];

			for (int j = 0; j < actualValue.size(); j++) {
				actualValueArr[j] = actualValue.get(j).getText();
			}

			actualDisplayedValue.add(Arrays.deepToString(actualValueArr).replace("[", "").replace("]", ""));
			String databaseResult = getDataFromDataBase(sqlCommand, (i + 1));
			expectedDisplayedValue.add(databaseResult.replace("[", "").replace("]", ""));

		}
		String actualResultStr = Arrays.deepToString(actualDisplayedValue.toArray());
		String expectedResultStr = Arrays.deepToString(expectedDisplayedValue.toArray());
		assertEquals(actualResultStr, expectedResultStr);
	}

	@Test(description = "Validate Pagination", groups = { "main case", "Employee Log In" })
	public void validatePagination() {
		driver.navigate().to(path);
		List<WebElement> firstPageIDList = driver.findElements(eplp.lblIDList);
		String[] firstPageActualResult = new String[firstPageIDList.size()];
		for (int i = 0; i < firstPageIDList.size(); i++) {
			firstPageActualResult[i] = firstPageIDList.get(i).getText();
		}

		clickOnElement(eplp.btnPageNumber);
		List<WebElement> secondPageIDList = driver.findElements(eplp.lblIDList);
		String[] secondPageActualResult = new String[secondPageIDList.size()];
		for (int i = 0; i < secondPageIDList.size(); i++) {
			secondPageActualResult[i] = secondPageIDList.get(i).getText();
		}

		String firstPageIDStr = Arrays.deepToString(firstPageActualResult);
		String secondPageIDStr = Arrays.deepToString(firstPageActualResult);
		// Check if element in second Page is duplicated with first page
		int count = 0;
		for (int i = 0; i < firstPageIDList.size(); i++) {
			for (int j = 0; j < secondPageIDList.size(); j++) {
				if (firstPageActualResult[i].equals(secondPageActualResult[j])) {
					count = count + 1;
					System.out.println("Duplated Product Found: " + firstPageActualResult[i]);
				}
			}
		}
		boolean result = count == 0;
		assertEquals(result, true);
	}

	@DataProvider(name = "Search Successfully Data")
	public String[][] data() throws IOException {
		String[][] data = excelUtils.getDataFromExcel("SearchProductSuccessfully");
		return data;
	}

	@Test(description = "Validate Search Product Successfully", groups = { "main case",
			"Employee Log In" }, dataProvider = "Search Successfully Data")
	public void searchSuccessfully(String keyword, String command) throws ClassNotFoundException, SQLException {
		driver.navigate().to(path);
		sendKeys(eplp.txtSearch, keyword);
		clickOnElement(eplp.iconSearch);

		// Get actual result
		List<WebElement> idList = driver.findElements(eplp.lblIDList);
		String[] idArr = new String[idList.size()];
		for (int i = 0; i < idArr.length; i++) {
			idArr[i] = idList.get(i).getText();
		}
		String expectedResult = getDataFromDataBase(command, 1).replace("[", "").replace("]", "");
		String actualResult = Arrays.deepToString(idArr).replace("[", "").replace("]", "");
		assertEquals(actualResult, expectedResult);
	}

	@DataProvider(name = "Search No Result Found Data", indices = { 0, 1, 2 })
	public String[][] noResultdata() throws IOException {
		String[][] data = excelUtils.getDataFromExcel("SearchProductFailed");
		return data;
	}

	@Test(description = "Validate Search Product When No Result Found", groups = { "validate case",
			"Employee Log In" }, dataProvider = "Search No Result Found Data")
	public void searchNoResultFound(String keyword, String message) throws ClassNotFoundException, SQLException {
		driver.navigate().to(path);
		sendKeys(eplp.txtSearch, keyword);
		clickOnElement(eplp.iconSearch);
		String actualResult = driver.findElement(eplp.lblSearchMessage).getText();
		assertEquals(actualResult, message);
	}

	@DataProvider(name = "Search When Leaving Field Blank", indices = { 3 })
	public String[][] blankFieldData() throws IOException {
		String[][] data = excelUtils.getDataFromExcel("SearchProductFailed");
		return data;
	}

	@Test(description = "Validate Search Product When Leaving Field Blank", groups = { "validate case",
			"Employee Log In" }, dataProvider = "Search When Leaving Field Blank")
	public void searchLeavingFieldBlank(String keyword, String message) throws ClassNotFoundException, SQLException {
		driver.navigate().to(path);
		clickOnElement(eplp.iconSearch);
		String actualResult = getHtml5ValidationMessage(eplp.txtSearch);
		assertEquals(actualResult, message);
	}

	@DataProvider(name = "Edit Product Successfully Data")
	public String[][] editSuccessfulData() throws IOException {
		String[][] data = excelUtils.getDataFromExcel("EditQuantitySuccessfully");
		return data;
	}

	@Test(description = "Validate Edit Product Quantity Successfully", groups = { "main case",
			"Employee Log In"}, dataProvider = "Edit Product Successfully Data")
	
	public void editQuantitySuccessfully(String value) {
		driver.navigate().to(path);
		clickOnElement(eplp.iconEdit);
		WebDriverWait wait = new WebDriverWait(driver, 3);
		sendKeys(eplp.txtQuantity, value);
		clickOnElement(eplp.btnSave);
		String actualResult = driver.findElement(eplp.lblQuantityList).getText();
		assertEquals(actualResult, value);
	}

	
	@DataProvider(name = "Edit Product Invalid Data", indices = {0,1,2,3})
	public String[][] editInvalidData() throws IOException{
		String[][] data= excelUtils.getDataFromExcel("EditQuantityFailed");
		return data;
	}
	@Test(description = "Validate Edit Product Fail When Enter Invalid", groups= {"validate case", "Employee Log In"}, dataProvider = "Edit Product Invalid Data")
	public void editQuantityFailUsingInvalidData(String value, String expectedMessage) {
		driver.navigate().to(path);
		clickOnElement(eplp.iconEdit);
		sendKeys(eplp.txtQuantity, value);
		clickOnElement(eplp.btnSave);
		//String actualResult= driver.findElement(eplp.lblQuantityList).getText();
		//assertEquals(actualResult, value);
	}
	
	
	@Test(description = "Validate Edit Product Fail When Leaving Quantity Field Blank", groups = { "validate case",
			"Employee Log In" })
	public void editQuantityFailWhenLeavingFieldBlank() {
		driver.navigate().to(path);
		clickOnElement(eplp.iconEdit);
		driver.findElement(eplp.txtQuantity).clear();
		clickOnElement(eplp.btnSave);
		String actualResult = getHtml5ValidationMessage(eplp.txtQuantity);
		assertEquals(actualResult, "Please fill out this field.");
	}
	
	@Test(description = "Validate Edit Product Fail When Clicking On The Close Icon", groups = { "validate case",
			"Employee Log In" })
	public void editQuantityFailWhenNotClickingSaveButton() {
		driver.navigate().to(path);
		String beforeEdit= driver.findElement(eplp.lblQuantityList).getText();
		clickOnElement(eplp.iconEdit);
		sendKeys(eplp.txtQuantity, "100");
	clickOnElement(eplp.iconClose);
	String afterEdit= driver.findElement(eplp.lblQuantityList).getText();
		assertEquals(beforeEdit, afterEdit);
	}
}
