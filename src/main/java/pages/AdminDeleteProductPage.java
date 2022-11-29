package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminDeleteProductPage extends Page{

	public AdminDeleteProductPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
public By iconDelete= By.id("delete");
public By btnDelete= By.name("delete_all");
public By chbList= By.xpath("//input[@name='choose_all[]']");
public By lblIDList= By.xpath("//table//tr//td[2]");
public By iconView= By.xpath("//button[@class='fa fa-eye']");
public By btnDeleteAtProductDetailPage= By.xpath("//a[contains(@href,'delete')]//button");
}
