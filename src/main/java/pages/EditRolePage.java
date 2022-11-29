package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditRolePage extends Page{

	public EditRolePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public By iconEdit= By.id("edit0");
	public By popupEditRole=By.className("modal-content");
	public By iconClose=By.id("close-edit0");
	public By dropdownRole= By.id("role-list-1");
	public By btnSaveChange= By.id("edit-account-1");
	public By linktextManagement= By.xpath("//a[contains(@href,'admin/index')]");
	public By lblMenuBar= By.xpath("//li//span[@class='title']");
	public By lblAccountDropdown= By.xpath("//div[@class='dropdown-content']//a");
}
