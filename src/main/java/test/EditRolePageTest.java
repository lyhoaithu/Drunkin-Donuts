package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.EditRolePage;
import pages.HomePage;
import pages.LogInPage;

public class EditRolePageTest extends TestCase{
	public EditRolePage erp= new EditRolePage(driver);
	public String pagePath= "http://localhost:8081/DrunkinDonut/admin/pages/account/manage-account.php";
	public String email= "lyhoaithu69@gmail.com";
	public String password="123456";
	
	@BeforeMethod(onlyForGroups = "Admin Login")
	public void adminLogIn() {
		LogInPage lp = new LogInPage(driver);
		driver.navigate().to("http://localhost:8081/DrunkinDonut/pages/account/login.php");
		sendKeys(lp.txtEmail, "todayiswendy@gmail.com");
		sendKeys(lp.txtPassword, "123456");
		clickOnElement(lp.btnLogIn);
	}
	
@Test(description = "Validate Popup Edit Role Appears When Clicking On Edit Icon", groups = {"main case", "Admin Login"}, priority = 1)
public void validatePopUp() {
	driver.navigate().to(pagePath);
	clickOnElement(erp.iconEdit);
	boolean isDisplayed=checkElementDisplayed(erp.popupEditRole);
	assertEquals(isDisplayed, true);
}

@Test(description = "Validate Closing Popup When Clicking Anywhere Outside The PopUp", groups = {"validate case", "Admin Login"}, priority = 2)
public void closingPopUpByClickingOutside() {
	driver.navigate().to(pagePath);
	clickOnElement(erp.iconEdit);
	clickOutside();
	boolean isDisplayed=checkElementDisplayed(erp.popupEditRole);
	assertEquals(isDisplayed, false);
}

@Test(description = "Validate Switching To Admin Role Successfully",groups = {"main case", "Admin Login"}, priority = 3)
public void switchToAdmin() {
	driver.navigate().to(pagePath);
	clickOnElement(erp.iconEdit);
	selectDropdownBox(erp.dropdownRole, "3");
	clickOnElement(erp.btnSaveChange);
	
	//Check Log In Again To See If Account Has the Function Of Admin
	postLogIn(email, password);
	HomePage hp= new HomePage(driver);
	hoverMouse(hp.iconUser);
	clickOnElement(erp.linktextManagement);
	String currentURL= driver.getCurrentUrl();
	assertTrue(currentURL.contains("admin/index"));
}

@Test(description = "Validate Switching To Employee Role Successfully",groups = {"main case", "Admin Login"}, priority = 4)
public void switchToEmployee() {
	driver.navigate().to(pagePath);
	clickOnElement(erp.iconEdit);
	selectDropdownBox(erp.dropdownRole, "2");
	clickOnElement(erp.btnSaveChange);
	
	//Check Log In Again To See If Account Has the Function Of Employee
	postLogIn(email, password);
	HomePage hp= new HomePage(driver);
	hoverMouse(hp.iconUser);
	clickOnElement(erp.linktextManagement);
	List<WebElement> menubarList= driver.findElements(erp.lblMenuBar);
	String[] menuBarLabel= new String[menubarList.size()];
	for(int i=0; i<menubarList.size();i++) {
		menuBarLabel[i]= menubarList.get(i).getText();
	}
	String menuBarLabelStr= Arrays.deepToString(menuBarLabel).replace("[", "").replace("]", "");
	assertTrue(!menuBarLabelStr.contains("Account(s)"));
}

@Test(description = "Validate Switching To User Role Successfully",groups = {"main case", "Admin Login"}, priority = 5)
public void switchToUser() {
	driver.navigate().to(pagePath);
	clickOnElement(erp.iconEdit);
	selectDropdownBox(erp.dropdownRole, "1");
	clickOnElement(erp.btnSaveChange);
	
	//Check Log In Again To See If Account Has the Function Of User
	postLogIn(email, password);
	HomePage hp= new HomePage(driver);
	clickOnElement(hp.iconUser);
	List<WebElement> accountDropdownList= driver.findElements(erp.lblAccountDropdown);
	assertTrue(accountDropdownList.size()==4);
}

@Test(description = "Validate Closing Pop Up When Clicking On The Close Icon", groups = {"validate case", "Admin Login"}, priority = 6)
public void clickOnCloseIcon() {
	driver.navigate().to(pagePath);
	clickOnElement(erp.iconEdit);
	clickOnElement(erp.iconClose);
	boolean isDisplayed=checkElementDisplayed(erp.popupEditRole);
	assertEquals(isDisplayed, false);
}

}


