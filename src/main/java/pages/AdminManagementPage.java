package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdminManagementPage extends Page{
	
	
	public AdminManagementPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public By iconUser= By.xpath("//a[@class='fa-solid fa-user ']");
	public By linktextManagement= By.xpath("//a[contains(@href,'admin/index')]");
	public By iconAccountManage= By.xpath("//a[contains(@href,'manage-account')]");
	public By txtSearchBox= By.id("search-box");
	public By iconSearch= By.xpath("//button[@class='fa fa-search']");
	public By lblID= By.xpath("//tbody//tr//td[1]");
	public By lblName= By.xpath("//tbody//tr//td[2]");
	public By lblEmail= By.xpath("//tbody//tr//td[4]");
	public By lblPhone= By.xpath("//tbody//tr//td[3]");
	public By lblRole= By.xpath("//tbody//tr//td[5]");
	public By lblSearchMessage= By.className("message");
	
}
