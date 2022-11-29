package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminEditProductPage extends Page {

	public AdminEditProductPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public By iconEdit = By.xpath("//button[@class='fa fa-pencil-square-o']");
	public By ddCategory = By.name("cateList");
	public By txtProductName = By.name("pname");
	public By txtPrice = By.name("price");
	public By txtQuantity = By.name("quantity");
	public By txtDiscount = By.name("discount");
	public By txtDescription = By.name("desc");
	public By btnChangePicture = By.xpath("//*[@id=\"image\"]");
	public By btnSave = By.id("save");
	public By btnCancel = By.xpath("//button[@class='button button4']");
	public By btnEditProduct = By.xpath("//button[@class='button']");
	public By iconView = By.xpath("//button[@class='fa fa-eye']");
	public By lblProductName = By.className("product-title");
	public By lblErrorMessage = By.className("error");
}
