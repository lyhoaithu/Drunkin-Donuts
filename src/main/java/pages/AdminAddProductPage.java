package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminAddProductPage extends Page {

	public AdminAddProductPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public By btnAddProduct = By.xpath("//button[@type='button']");
	public By btnChoosePicture = By.xpath("//*[@id=\"image\"]");
	public By ddCategory = By.id("cate-list");
	public By txtProductName = By.name("pname");
	public By txtPrice = By.name("price");
	public By txtQuantity = By.name("quantity");
	public By txtDescription = By.name("desc");
	public By btnSave = By.id("save");
	public By btnCancel = By.xpath("//button[@class='button button4']");
	public By btnPageList = By.xpath("//div[@id='pagination']//a");
	public By lblProductNameList = By.xpath("//table//td[4]");
	public By lblErrorMessage=By.className("error");

}
