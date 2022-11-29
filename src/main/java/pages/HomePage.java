package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends Page{

	public HomePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
public By iconUser= By.xpath("//a[@class='fa-solid fa-user ']");
public By linktextLogIn= By.xpath("//a[contains(@href,'login')]");
public By linktextMyOrder=By.xpath("//a[contains(@href,'my-order')]");
public By txtSearchBox=By.id("search-box");
public By iconSearch=By.id("search");
public By linktextAboutUs= By.xpath("//a[contains(@href,'about_us')]");
public By linktextProduct= By.xpath("//a[contains(@href,'product_page')]");
public By linktextHome=By.xpath("//a[contains(@href,'index')]");
public By linktextContact= By.xpath("//a[contains(@href,'#contact')]");
public By iconShoppingCart= By.xpath("//a[contains(@href,'view_cart')]");
public By btnFOM= By.xpath("//div[@class='content']//a[contains(@href,'about_us')]");
public By btnOrderNow=By.xpath("//div[@class='container two']//a");
public By btnContactUs= By.xpath("//div[@class='container three']//a");

}
