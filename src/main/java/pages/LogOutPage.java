package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogOutPage extends Page{

	public LogOutPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
public By iconUser=  By.xpath("//a[@class='fa-solid fa-user ']");
public By linktextLogOut=By.xpath("//a[contains(@href,'logout')]");
}
