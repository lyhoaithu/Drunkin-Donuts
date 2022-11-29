package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import pages.UserProductListPage;

public class UserProductListPageTest extends TestCase {

	public UserProductListPage plp = new UserProductListPage(driver);
	public String path = "http://localhost:8081/DrunkinDonut/pages/product/product-list.php";

	@Test(description = "Validate Navigating To Product List Page", groups = "main case")
	public void navigatingToProductLisPage() {
		driver.navigate().to("http://localhost:8081/DrunkinDonut/index.php");
		clickOnElement(plp.linktextProducts);
		String currentURL = driver.getCurrentUrl();
		assertEquals(currentURL, path);
	}

	@Test(description = "Validate Navigating To Product Details Page By Clicking On Product Image", groups = "main case")
	public void clickOnProductImage() {
		driver.navigate().to(path);

		String clickedProductName = driver.findElement(plp.lblName).getText();
		clickOnElement(plp.imgProduct);
		String productAtDetailPage = driver.findElement(plp.lblProductDetailName).getText();
		String currentURL = driver.getCurrentUrl();
		assertTrue(currentURL.contains("view-product"));
		assertEquals(clickedProductName, productAtDetailPage);
	}

	@Test(description = "Validate Navigating To Product Details Page By Clicking On Product Name", groups = "main case")
	public void clickOnProductName() {
		driver.navigate().to(path);

		String clickedProductName = driver.findElement(plp.lblName).getText();
		clickOnElement(plp.lblName);
		String productAtDetailPage = driver.findElement(plp.lblProductDetailName).getText();
		String currentURL = driver.getCurrentUrl();
		assertTrue(currentURL.contains("view-product"));
		assertEquals(clickedProductName, productAtDetailPage);
	}

	@Test(description = "Validate Pagination", groups = "main case")
	public void pagination() {
		driver.navigate().to(path);
		List<WebElement> firstPageProductNameList = driver.findElements(plp.listProductName);
		String[] firstPageProductNameArr = new String[firstPageProductNameList.size()];
		for (int i = 0; i < firstPageProductNameList.size(); i++) {
			firstPageProductNameArr[i] = firstPageProductNameList.get(i).getText();
		}

		driver.navigate().to("http://localhost:8081/DrunkinDonut/pages/product/product-list.php?page=2");
		List<WebElement> secondPageProductNameList = driver.findElements(plp.listProductName);
		String[] secondPageProductNameArr = new String[firstPageProductNameList.size()];
		for (int i = 0; i < secondPageProductNameList.size(); i++) {
			secondPageProductNameArr[i] = secondPageProductNameList.get(i).getText();
		}
		String firstPageProductNameStr = Arrays.deepToString(firstPageProductNameArr).replace("[", "").replace("]", "");
		String secondPageProductNameStr = Arrays.deepToString(secondPageProductNameArr).replace("[", "").replace("]",
				"");

//Check if element in second Page is duplicated with first page
		int count = 0;
		for (int i = 0; i < firstPageProductNameList.size(); i++) {
			for (int j = 0; j < secondPageProductNameList.size(); j++) {
				if (firstPageProductNameArr[i].equals(secondPageProductNameArr[j])) {
					count = count + 1;
					System.out.println("Duplated Product Found: " + firstPageProductNameArr[i]);
				}
			}
		}
		boolean result = count == 0;
		assertEquals(result, true);
	}

	@Test(description = "Validate Sorting Products", groups = "main case")
	public void sortingProduct() throws ClassNotFoundException, SQLException {
		driver.navigate().to(path);
		String[] options = { "new", "low", "high" };
		String[] sqlCommands = { "SELECT ProductName FROM `product` order BY CreateDate DESC limit 12;",
				"SELECT ProductName FROM `product` order BY CreateDate ASC limit 12;",
				"SELECT ProductName FROM `product` order BY CreateDate DESC limit 12;" };

		for (int j = 0; j < options.length; j++) {
			selectDropdownBox(plp.ddSort, options[j]);
			List<WebElement> productNameList = driver.findElements(plp.listProductName);
			String[] productNameArr = new String[productNameList.size()];

			for (int i = 0; i < productNameList.size(); i++) {
				productNameArr[i] = productNameList.get(i).getText();
			}
			String expectedProductNameStr = getDataFromDataBase(sqlCommands[j], 1).replace("[", "").replace("]", "");
			String actualProductNameStr = Arrays.deepToString(productNameArr).replace("[", "").replace("]", "");
			assertEquals(actualProductNameStr, expectedProductNameStr);
		}
	}

	// Positive Fail due to the created date is almost alike
	@Test(description = "Validate Sorting Products By Category", groups = "main case")
	public void sortingProductByCategory() throws ClassNotFoundException, SQLException {
		driver.navigate().to(path);
		String[] sqlCommands = { "SELECT ProductName  FROM `product` WHERE CategoryID=1 ORDER BY Price DESC LIMIT 12;",
				"SELECT ProductName  FROM `product` WHERE CategoryID=4 ORDER BY Price DESC LIMIT 12;",
				"SELECT ProductName  FROM `product` WHERE CategoryID=2 ORDER BY Price DESC LIMIT 12;",
				"SELECT ProductName  FROM `product` WHERE CategoryID=3 ORDER BY Price DESC LIMIT 12;" };

		for (int j = 0; j < sqlCommands.length; j++) {
			clickOnElement(By.xpath("//div[@class='category']//a[" + (j + 2) + "]"));
			selectDropdownBox(plp.ddSort, "high");
			List<WebElement> productNameList = driver.findElements(plp.listProductName);
			String[] productNameArr = new String[productNameList.size()];

			for (int i = 0; i < productNameList.size(); i++) {
				productNameArr[i] = productNameList.get(i).getText();
			}
			String expectedProductNameStr = getDataFromDataBase(sqlCommands[j], 1).replace("[", "").replace("]", "");
			String actualProductNameStr = Arrays.deepToString(productNameArr).replace("[", "").replace("]", "");
			assertEquals(actualProductNameStr, expectedProductNameStr);
		}
	}

	@Test(description = "Search Product Successfully", groups = "main case")
	public void searchProductSuccessfully() throws ClassNotFoundException, SQLException {
		driver.navigate().to(path);
		sendKeys(plp.txtSearch, "Blue");
		clickOnElement(plp.iconSearch);
		List<WebElement> productNameList = driver.findElements(plp.listProductName);
		String[] productNameArr = new String[productNameList.size()];
		for (int i = 0; i < productNameArr.length; i++) {
			productNameArr[i] = productNameList.get(i).getText();
		}
		String expectedResult = getDataFromDataBase(
				"SELECT ProductName  FROM `product` WHERE ProductName LIKE '%Blue%' order BY CreateDate DESC limit 12;",
				1).replace("[", "").replace("]", "");
		String actualResult = Arrays.deepToString(productNameArr).replace("[", "").replace("]", "");
		assertEquals(actualResult, expectedResult);
	}
	
	@Test(description = "Search Product When Leaving Search Field Blank", groups = "validate case")
	public void leavingSearchFieldBlank() {
		driver.navigate().to(path);
		clickOnElement(plp.iconSearch);
		String actualResult=getHtml5ValidationMessage(plp.txtSearch);
assertEquals(actualResult, "Please fill out this field.");
	}
}
