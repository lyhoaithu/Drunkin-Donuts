package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.ExcelUtils;
import pages.AdminEditProductPage;
import pages.LogInPage;

public class AdminEditProductPageTest extends TestCase {
	public ExcelUtils excelUtils = new ExcelUtils();
	public AdminEditProductPage aepp = new AdminEditProductPage(driver);
	public String path = "http://localhost:8081/DrunkinDonut/admin/pages/product/manage-product.php";

	@BeforeMethod(onlyForGroups = "Admin Login")
	public void adminLogIn() {
		LogInPage lp = new LogInPage(driver);
		driver.navigate().to("http://localhost:8081/DrunkinDonut/pages/account/login.php");
		sendKeys(lp.txtEmail, "todayiswendy@gmail.com");
		sendKeys(lp.txtPassword, "123456");
		clickOnElement(lp.btnLogIn);
	}

//	@Test(description = "Validate Navigating To Edit Product Page By Clicking On The Edit Icon", groups= {"main case", "Admin Login"}, priority = 1)
//	public void clickingOnIconEdit() {
//		driver.navigate().to(path);
//		clickOnElement(aepp.iconEdit);
//		String currentURL= driver.getCurrentUrl();
//		assertTrue(currentURL.contains("edit-product"));
//	}
//	
//	@Test(description = "Validate Navigating To Edit Product Page By Clicking On Edit Product Button", groups= {"main case", "Admin Login"}, priority = 2)
//	public void clickingOnEditButton() {
//		driver.navigate().to(path);
//		clickOnElement(aepp.iconView);
//		clickOnElement(aepp.btnEditProduct);
//		String currentURL= driver.getCurrentUrl();
//		assertTrue(currentURL.contains("edit-product"));
//	}
//	
//	@Test(description = "Validate Editing Product Fail When Clicking On Cancel Button", groups = {"validate case", "Admin Login"})
//	public void editProductFail() {
//	driver.navigate().to(path);
//	clickOnElement(aepp.iconView);
//	String beforeEdit=driver.findElement(aepp.lblProductName).getText();
//	clickOnElement(aepp.btnEditProduct);
//	sendKeys(aepp.txtProductName, "Ocean Blue");
//	driver.findElement(aepp.btnChangePicture).sendKeys("D:\\XAMPP\\htdocs\\DrunkinDonut\\public\\images\\products\\Blue Sky.png");
//	clickOnElement(aepp.btnCancel);
//	String afterEdit=driver.findElement(aepp.lblProductName).getText();
//	assertEquals(afterEdit, beforeEdit);
//	}
//
//	@DataProvider(name = "Edit Product Successfully")
//	public String[][] editProductSuccessfullyData() throws IOException {
//		String[][] data = excelUtils.getDataFromExcel("EditProductSuccessfully");
//		return data;
//	}
//
//	@Test(description = "Edit Product Successfully", dataProvider = "Edit Product Successfully", groups = { "main case",
//			"Admin Login" })
//	public void editProductSuccessfully(String picture, String category, String productName, String price,
//			String quantity, String discount) {
//		driver.navigate().to(path);
//		clickOnElement(aepp.iconView);
//		String beforeEdit = driver.findElement(aepp.lblProductName).getText();
//		clickOnElement(aepp.btnEditProduct);
//		driver.findElement(aepp.btnChangePicture).sendKeys(picture);
//		selectDropdownBox(aepp.ddCategory, category);
//		sendKeys(aepp.txtProductName, productName);
//		sendKeys(aepp.txtPrice, price);
//		sendKeys(aepp.txtQuantity, quantity);
//		sendKeys(aepp.txtDiscount, discount);
//		clickOnElement(aepp.btnSave);
//		String alertMessage = getAlertMessage();
//		assertTrue(alertMessage.equals("Product has been updated!"));
//		acceptAlert();
//		String afterEdit = driver.findElement(aepp.lblProductName).getText();
//		boolean result = afterEdit.equals(beforeEdit);
//		assertEquals(result, false);
//	}
//	
//	@DataProvider(name = "Edit Product Blank Field", indices = {13,14,15,16} )
//	public String[][] blankFieldData() throws IOException {
//		String[][] data = excelUtils.getDataFromExcel("EditProductFail");
//		return data;
//	}
//
//	@Test(description = "Edit Product Fail When Leaving Compulsory Field Blank", dataProvider = "Edit Product Blank Field", groups = { "validate case",
//			"Admin Login" })
//	public void editProductBlankField(String picture, String category, String productName, String price,
//			String quantity, String discount, String description, String expectedMessage) {
//		driver.navigate().to(path);
//		clickOnElement(aepp.iconView);
//		clickOnElement(aepp.btnEditProduct);
//		if(!picture.equals("")) {
//			driver.findElement(aepp.btnChangePicture).sendKeys(picture);
//		}
//		sendKeys(aepp.txtProductName, productName);
//		sendKeys(aepp.txtPrice, price);
//		sendKeys(aepp.txtQuantity, quantity);
//		sendKeys(aepp.txtDiscount, discount);
//		sendKeys(aepp.txtDescription, description);
//		clickOnElement(aepp.btnSave);
//String actualResult= null;
//if(productName.equals("")) {
//	actualResult=getHtml5ValidationMessage(aepp.txtProductName);
//}
//else if (price.equals("")){
//	actualResult=getHtml5ValidationMessage(aepp.txtPrice);
//}
//else if(quantity.equals("")) {
//	actualResult=getHtml5ValidationMessage(aepp.txtQuantity);
//}
//else if(picture.equals("")) {
//	actualResult=driver.findElement(aepp.lblErrorMessage).getText();
//}
//assertEquals(actualResult, expectedMessage);
//}
}
