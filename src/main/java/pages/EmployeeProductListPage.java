package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;

public class EmployeeProductListPage extends Page {

	public EmployeeProductListPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
public By linktextProduct=By.xpath("//ul//li[4]");
public By cardProduct= By.xpath("//div[@class='cardBox']//a[contains(@href,'manage-product')]");
public By lblIDList= By.xpath("//table[@id='account']//tr//td[2]");
public By lblCatgoryNameList= By.xpath("//table[@id='account']//tr//td[3]");
public By lblProductNameList= By.xpath("//table[@id='account']//tr//td[4]");
public By lblPriceList= By.xpath("//table[@id='account']//tr//td[6]");
public By lblQuantityList= By.xpath("//table[@id='account']//tr//td[7]");
public By iconView= By.xpath("//table[@id='account']//tr//td[8]//a");
public By iconEdit= By.id("edit0");
public By txtSearch= By.name("keyword");
public By iconSearch= By.xpath("//button[@class='fa fa-search']");
public By btnPageNumber= By.xpath("//div[@class='pagination']//a[2]");
public By lblSearchMessage=By.className("message");

//Edit Quantity
public By popupEdit= By.className("modal-content");
public By txtQuantity= By.name("quantity");
public By btnSave= By.xpath("//div[@class='option']//button");
public By iconClose= By.className("close");

}
