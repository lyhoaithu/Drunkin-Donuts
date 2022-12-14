package common;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
	public WebDriver driver;

	public void openBrowser(String browserName) {
		File file01= new File("driver/msedgedriver.exe");
		File file02= new File("driver/geckodriver.exe");
		if (browserName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver",file01.getAbsolutePath());
			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", file02.getAbsolutePath());
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
	}

	public void clickOnElement(By locator) {
		// Wait for about 3s for some element to show
		WebDriverWait wait = new WebDriverWait(driver, 3);

		// click on element
		driver.findElement(locator).click();
	}

	public void sendKeys(By locator, String value) {
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(value);
	}

	public void waitForThePresenceOfElement(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public String getHtml5ValidationMessage(By locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String message = jsExecutor.executeScript("return arguments[0].validationMessage;", driver.findElement(locator))
				.toString();
		return message;
	}

	public String getValidateMessage(By locator) {
		String message = driver.findElement(locator).getText();
		return message;
	}

	public String getAlertMessage() {
		String alertMessage = driver.switchTo().alert().getText();
		return alertMessage;
	}

	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	public void rejectAlert() {
		driver.switchTo().alert().dismiss();
	}

	public void selectDropdownBox(By locator, String value) {
		Select dropdown = new Select(driver.findElement(locator));
		dropdown.selectByValue(value);
	}
	
	public boolean checkElementStatus(By locator) {
		boolean elementStatus= driver.findElement(locator).isEnabled();
		return elementStatus;
	}
	
	public boolean checkElementDisplayed(By locator) {
		boolean elementVisibility= driver.findElement(locator).isDisplayed();
		return elementVisibility;
	}
	
	public String getDataFromDataBase(String query, int columnIndex) throws SQLException, ClassNotFoundException {
		DataBaseConnection con= new DataBaseConnection();
		ResultSet rs= con.verifyData(query);
		
		//T???o Arrays List ????? ch???a kq l???y t??? DB
		ArrayList<String> result= new ArrayList<String>();
		
		//?????c kq t??? db r ghi v??o array
		while(rs.next()){
			 result.add(rs.getString(columnIndex));
			}
		 Object[] resultArr= new String[result.size()];
		 resultArr=result.toArray();
		 String resultString=Arrays.deepToString(resultArr);
	return resultString;
	}
	
	public void hoverMouse(By locator) {
		Actions action= new Actions(driver);
		action.moveToElement(driver.findElement(locator)).build().perform();
	}
	
	public void getHtml5ValidationFromASeriesOfFields(By[]elements, String[]data, By locator,String message) {
		for (int j = 0; j < elements.length; j++) {
			sendKeys(elements[j], data[j]);
		}
		clickOnElement(locator);
		String actualMessage = null;
		for (int i=0; i<elements.length; i++) {
			if(data[i].equals("")) {
				actualMessage= getHtml5ValidationMessage(elements[i]);
			}
		}
	Assert.assertEquals(actualMessage, message);
	}
	
	public  String getValueFromDatabase(String query, int columnIndex) throws ClassNotFoundException, SQLException {
		DataBaseConnection con= new DataBaseConnection();
		ResultSet rs= con.verifyData(query);
		
		//T???o Arrays List ????? ch???a kq l???y t??? DB
		ArrayList<String> result= new ArrayList<String>();
		
		//?????c kq t??? db r ghi v??o array
		while(rs.next()){
			 result.add(rs.getString(columnIndex));
			}
		
		 Object[] resultArr= new String[result.size()];
		 resultArr=result.toArray();
		 String resultString=Arrays.deepToString(resultArr).replace("[", "").replace("]", "");
	return resultString;
	}
	
	public void validateWebDisplayedResult(By[] locator,String[] command, int[] columnIndex) throws ClassNotFoundException, SQLException {
		for(int i=0; i<command.length;i++) {
			List<WebElement> idList=driver.findElements(locator[i]);
			String[] actualArr=new String[idList.size()];
			for (int j=0; j<idList.size();j++) {
				actualArr[j]=idList.get(j).getText();
			}
			String expectedResult= getValueFromDatabase(command[i], columnIndex[i]);
			String actualResult= Arrays.deepToString(actualArr).replace("[", "").replace("]", "");
			assertEquals(actualResult, expectedResult);
		}
	}
	
	public String getDisplayedInformationFromWebTable(By[] locator) {
		String[] actualArr02= new String[locator.length]; //array l???n ????? ch???a c??c array nh???
		
		//Ch???y v??ng for ????? duy???t c??c loctor trong arr locator. M???i locator l???i c?? 1 list c??c element ri??ng
		for(int i=0; i<locator.length;i++) {
			List<WebElement> idList=driver.findElements(locator[i]);
			String[] actualArr=new String[idList.size()]; //array nh??? ????? add t???ng text t??m ???????c c???a t???ng element trong list
			
			//Ch???y v??ng for ????? l???y l???n l?????t c??c ph???n t??? trong list
			for (int j=0; j<idList.size();j++) {
				actualArr[j]=idList.get(j).getText();
			}
			actualArr02[i]=Arrays.deepToString(actualArr).replace("[", "").replace("]", ""); //?????i arr nh??? th??nh string ????? t???ng v??o arr l???n
	}
		String actualResult= Arrays.deepToString(actualArr02).replace("[", "").replace("]", "");
		return actualResult;
}
	
	public void clickOutside() {
	    Actions action = new Actions(driver);
	    action.moveByOffset(0, 0).click().build().perform();
	}
}

