package commomFunctions;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FunctionLibrary {
	
	public static WebDriver driver;
	public static Properties conpro;
	public static String Expecteddata="";
	public static String Actualdata="";
	//method for launching the browser
	public static WebDriver startBrowser() throws Throwable
	{
		
		conpro = new Properties();
		conpro.load(new FileInputStream(".\\PropertyFiles\\Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		return driver;
		
	}
	//method for launching url
	public static void openUrl(WebDriver driver)
	{
		
		driver.get(conpro.getProperty("Url"));
		
	}
	//method for wait for element
	public static void waitForElement(WebDriver driver,String Locator_Type,String Locator_Value, String wait)
	{
		
		WebDriverWait myWait = new WebDriverWait(driver, Integer.parseInt(wait));
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
		
	}
	//method for type actions
	public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		}
		
	}
	//method for buttons,checkboxes,links,images,radiobuttons
	public static void clickAction(WebDriver driver,String Locator_Type,String Locator_Value)
	{
		
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).click();
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).click();
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}
		
	}
	//method to validate title
	public static void validateTitle(WebDriver driver,String Expectedtitle)
	{
		
		String Actualtitle = driver.getTitle();
		try
		{
			Assert.assertEquals(Actualtitle, Expectedtitle,"Title is not matching");
		}
		catch(Throwable t)
		{
			System.out.println(t);
		}
		
	}
	//method to close the browser
	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	//method for listboxes
	public static void selectDropDown(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.xpath(Locator_Value));
			Select select = new Select(element);
			select.selectByIndex(value);
		}
		else if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.id(Locator_Value));
			Select select = new Select(element);
			select.selectByIndex(value);
		}
		else if(Locator_Type.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.name(Locator_Value));
			Select select = new Select(element);
			select.selectByIndex(value);
		}
		
	}
	//method for capturestockitem number
	public static void capturestockitem(WebDriver driver,String Locator_Type,String Locator_Value)
	{
		
		Expecteddata = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		
	}
	//mehtod for stock table
	public static void stockTable(WebDriver driver) throws Throwable
	{
		
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox is displayed don't click search panel button or else click
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expecteddata);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Actualdata = driver.findElement(By.xpath("//table[@id='tbl_a_stock_itemslist']/tbody/tr[1]/td[8]/div/span/span")).getText();
		System.out.println(Expecteddata+"		"+Actualdata);
		Assert.assertEquals(Expecteddata, Actualdata ,"Stock number is not matching");
		
	}
	//method for mouse click
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Items')])[2]"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Categories')])[2]"))).click().perform();
		
	}
	//method for category table
	public static void categoryTable(WebDriver driver,String Expecteddata) throws Throwable
	{
		
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox is displayed don't click search panel button or else click
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expecteddata);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		Actualdata = driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
		System.out.println(Expecteddata+"		"+Actualdata);
		Assert.assertEquals(Expecteddata, Actualdata ,"Category number is not matching");
		
	}
	//method to capture supplier number
	public static void capureData(WebDriver driver,String Locator_Type,String Locator_Value)
	{
		
		Expecteddata = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		
	}
	//method for supplier table
	public static void supplierTable(WebDriver driver) throws Throwable
	{
		
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox is displayed don't click search panel button or else click
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expecteddata);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		Actualdata = driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		System.out.println(Expecteddata+"		"+Actualdata);
		Assert.assertEquals(Expecteddata, Actualdata ,"Supplier number is not matching");
		
	}
	//method for date generation
	public static String generateDate()
	{
		
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY__MM__dd");
		return df.format(date);
		
	}
	//method for customer table
	public static void customerTable(WebDriver driver) throws Throwable
	{
		
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//if search textbox is displayed don't click search panel button or else click
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expecteddata);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		Actualdata = driver.findElement(By.xpath("//span[@id='el1_a_customers_Customer_Number']")).getText();
		System.out.println(Expecteddata+"		"+Actualdata);
		Assert.assertEquals(Expecteddata, Actualdata ,"Customer number is not matching");
		
	}

}
