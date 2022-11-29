package test;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.AdminDeleteProductPage;
import pages.LogInPage;

public class AdminDeleteProductPageTest extends TestCase{
	public AdminDeleteProductPage adp= new AdminDeleteProductPage(driver);
	public String path= "http://localhost:8081/DrunkinDonut/admin/pages/product/manage-product.php";
	
	@BeforeMethod(onlyForGroups = "Admin Login")
	public void adminLogIn() {
		LogInPage lp = new LogInPage(driver);
		driver.navigate().to("http://localhost:8081/DrunkinDonut/pages/account/login.php");
		sendKeys(lp.txtEmail, "todayiswendy@gmail.com");
		sendKeys(lp.txtPassword, "123456");
		clickOnElement(lp.btnLogIn);
	}
	
	@Test(description = "Validate Delete Product Successfully When Clicking On The Trash Icon", groups= {"main case", "Admin Login"}, priority = 1)
	public void clickOnTheTrashIcon() {
		driver.navigate().to(path);
		String beforeDelete= driver.findElement(adp.lblIDList).getText();
		clickOnElement(adp.iconDelete);
		acceptAlert();
		String afterDelete= driver.findElement(adp.lblIDList).getText();
		boolean actualResult= beforeDelete.equals(afterDelete);
		assertEquals(actualResult, false);
	}
	
	@Test(description = "Validate Deleting Product At The Product Details Page", groups= {"main case", "Admin Login"}, priority = 2 )
public void deleteAtTheProductDetailPage() {
	driver.navigate().to(path);
	String beforeDelete= driver.findElement(adp.lblIDList).getText();
	clickOnElement(adp.iconView);
	clickOnElement(adp.btnDeleteAtProductDetailPage);
	acceptAlert();
	acceptAlert();
	String afterDelete= driver.findElement(adp.lblIDList).getText();
	boolean actualResult= beforeDelete.equals(afterDelete);
	assertEquals(actualResult, false);
}
	
	@Test(description ="Validate Deleting Multiple Products", groups= {"main case", "Admin Login"}, priority = 3 )
	public void deletingMultilpleProduct() {
		driver.navigate().to(path);
	List<WebElement> checkboxList= driver.findElements(adp.chbList);
	List<WebElement> IDList= driver.findElements(adp.lblIDList);
	String[] beforeDelete= {IDList.get(0).getText(),IDList.get(1).getText()};
		checkboxList.get(0).click();
		checkboxList.get(1).click();
		clickOnElement(adp.btnDelete);
		acceptAlert();
		acceptAlert();
		List<WebElement> IDList01= driver.findElements(adp.lblIDList);
	String[] afterDelete={IDList01.get(0).getText(),IDList01.get(1).getText()};
	String beforeDeleteStr= Arrays.deepToString(beforeDelete).replace("[", "").replace("]", "");
	String afterDeleteStr= Arrays.deepToString(afterDelete).replace("[", "").replace("]", "");
	boolean actualResult= beforeDeleteStr.equals(afterDeleteStr);
	assertEquals(actualResult, false);
	}
	
	@Test(description = "Validate Deleting Product Fail", groups= {"validate case", "Admin Login"}, priority = 4)
	public void deleteFail() {
		driver.navigate().to(path);
		String beforeDelete= driver.findElement(adp.lblIDList).getText();
		clickOnElement(adp.iconView);
		clickOnElement(adp.btnDeleteAtProductDetailPage);
		rejectAlert();
		driver.navigate().to(path);
		String afterDelete= driver.findElement(adp.lblIDList).getText();
		boolean actualResult= beforeDelete.equals(afterDelete);
		assertEquals(actualResult, true);
	}
}
