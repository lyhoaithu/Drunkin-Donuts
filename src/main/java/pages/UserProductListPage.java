package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserProductListPage extends Page {

	public UserProductListPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
public By linktextProducts=By.xpath("//li[2]//a");
public By imgProduct=By.xpath("//div[@class='card-p']//div[@class='img']//img");
public By lblName=By.xpath("//div[@class='card-p']//div[@class='content']//div//h3");
public By lblOutOfStock=By.xpath("//div[@class='middle']");
public By lblOOSPN= By.xpath("//div[@class='card-p1']//div[@class='productName']");
public By pagination= By.xpath("//*[@id=\"pagination\"]/a[2]");
public By listProductName=By.xpath("//div[@class='productName']//h3");
public By lblProductDetailName= By.xpath("//div[@class='product-content']//h2");

//Product Sorting
public By ddSort=By.id("sort");

//Product search
public By txtSearch= By.name("keyword");
public By iconSearch= By.xpath("//button[@class='fa fa-search']");
}
