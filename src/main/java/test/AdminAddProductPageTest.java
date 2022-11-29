package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.ExcelUtils;
import pages.AdminAddProductPage;
import pages.LogInPage;

public class AdminAddProductPageTest extends TestCase{
public AdminAddProductPage aapp= new AdminAddProductPage(driver);
public String path="http://localhost:8081/DrunkinDonut/admin/pages/product/add-product.php";
public ExcelUtils excelUtils= new ExcelUtils();

@BeforeMethod(onlyForGroups = "Admin Login")
public void adminLogIn() {
	LogInPage lp = new LogInPage(driver);
	driver.navigate().to("http://localhost:8081/DrunkinDonut/pages/account/login.php");
	sendKeys(lp.txtEmail, "todayiswendy@gmail.com");
	sendKeys(lp.txtPassword, "123456");
	clickOnElement(lp.btnLogIn);
}

@Test(description = "Validate Navigating To Add Product Page", groups= {"main case", "Admin Login"}, priority = 1)
public void navigatingToAddProductPage() {
	driver.navigate().to("http://localhost:8081/DrunkinDonut/admin/pages/product/manage-product.php");
	clickOnElement(aapp.btnAddProduct);
	String currentURL= driver.getCurrentUrl();
	assertTrue(currentURL.contains("product/add-product"));
}

@DataProvider(name = "Add Product Valid Data")
public String[][] validData() throws IOException {
	String [][] data= excelUtils.getDataFromExcel("AddProductSuccessfully");
	return data;
}

@Test(description = "Validate Adding Product Successfully", dataProvider = "Add Product Valid Data", groups= {"main case", "Admin Login"}, priority = 2)
public void addProductSuccessfully(String picture, String category, String productName, String price, String quantity, String description) throws AWTException {
	driver.navigate().to(path);
	driver.findElement(aapp.btnChoosePicture).sendKeys(picture);
	selectDropdownBox(aapp.ddCategory, category);
	sendKeys(aapp.txtProductName, productName);
	sendKeys(aapp.txtPrice, price);
	sendKeys(aapp.txtQuantity, quantity);
	sendKeys(aapp.txtDescription, description);
clickOnElement(aapp.btnSave);
acceptAlert();
List<WebElement> paginationList= driver.findElements(aapp.btnPageList);
paginationList.get(paginationList.size()-1).click();
List<WebElement> productNameList= driver.findElements(aapp.lblProductNameList);
String actualProductName= productNameList.get(productNameList.size()-1).getText();
assertEquals(actualProductName, productName);
}

@Test(description = "Validate Adding Product Successfully With Duplicated Product Name", groups= {"main case", "Admin Login"}, priority = 3)
public void addProductSuccessfullyWithDuplicatedName() throws AWTException {
	driver.navigate().to(path);
	driver.findElement(aapp.btnChoosePicture).sendKeys("D:\\Projects\\Drunkin Donuts\\03TestCase\\Test_Images\\Test_Img01.jpg");
	selectDropdownBox(aapp.ddCategory, "2");
	sendKeys(aapp.txtProductName, "Chocolate Addicted");
	sendKeys(aapp.txtPrice, "50000");
	sendKeys(aapp.txtQuantity, "10");
	sendKeys(aapp.txtDescription, "abcd1234 %$");
clickOnElement(aapp.btnSave);
acceptAlert();
List<WebElement> paginationList= driver.findElements(aapp.btnPageList);
paginationList.get(paginationList.size()-1).click();
List<WebElement> productNameList= driver.findElements(aapp.lblProductNameList);
String actualProductName= productNameList.get(productNameList.size()-1).getText();
assertEquals(actualProductName, "Chocolate Addicted");
}

@Test(description = "Validate Adding Product Fail When Clicking Cancel", groups= {"validate case", "Admin Login"}, priority = 4)
public void addProductFailWhenClickingCancel() throws AWTException {
	driver.navigate().to(path);
	driver.findElement(aapp.btnChoosePicture).sendKeys("D:\\Projects\\Drunkin Donuts\\03TestCase\\Test_Images\\Test_Img01.jpg");
	selectDropdownBox(aapp.ddCategory, "2");
	sendKeys(aapp.txtProductName, "Caramel Sprinkles");
	sendKeys(aapp.txtPrice, "50000");
	sendKeys(aapp.txtQuantity, "10");
	sendKeys(aapp.txtDescription, "abcd1234 %$");
clickOnElement(aapp.btnCancel);
List<WebElement> paginationList= driver.findElements(aapp.btnPageList);
paginationList.get(paginationList.size()-1).click();
List<WebElement> productNameList= driver.findElements(aapp.lblProductNameList);
String actualProductName= productNameList.get(productNameList.size()-1).getText();
boolean result= actualProductName.equals("Caramel Sprinkles");
assertEquals(result, false);
}

@DataProvider(name = "Add Product When Leaving Compulsory Field Blank Data",indices = {9,10,11,12})
public String[][] fieldBlankData() throws IOException{
	String[][] data= excelUtils.getDataFromExcel("AddProductFail");
	return data;
}

@Test(description = "Validate Adding Product Fail When Leaving Compulsory Field Blank", dataProvider = "Add Product When Leaving Compulsory Field Blank Data", groups= {"main case", "Admin Login"}, priority = 5)
public void leavingFieldBlank(String picture, String category, String productName, String price, String quantity, String description, String expectedMessage) {
	driver.navigate().to(path);
	
	sendKeys(aapp.txtProductName, productName);
	sendKeys(aapp.txtPrice, price);
	sendKeys(aapp.txtQuantity, quantity);
	if(!picture.equals("")) {
		driver.findElement(aapp.btnChoosePicture).sendKeys(picture);
	}
	clickOnElement(aapp.btnSave);
	String actualResult=null;
if(productName.equals("")) {
	actualResult=getHtml5ValidationMessage(aapp.txtProductName);
}
else if (price.equals("")){
	actualResult=getHtml5ValidationMessage(aapp.txtPrice);
}
else if(quantity.equals("")) {
	actualResult=getHtml5ValidationMessage(aapp.txtQuantity);
}
else if(picture.equals("")) {
	actualResult=driver.findElement(aapp.lblErrorMessage).getText();
}
assertEquals(actualResult, expectedMessage);
}
}
