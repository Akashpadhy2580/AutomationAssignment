package stepdef;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Stepdef 
{
	public RemoteWebDriver driver;
	public FluentWait<RemoteWebDriver> wait;
	public Scenario s;
	
	@Given("open chrome browser")
	public void open_chrome_browser() 
	{
	    WebDriverManager.chromedriver().setup();
	     driver=new ChromeDriver();
	     driver.manage().window().maximize();
	     wait =new  FluentWait<RemoteWebDriver> (driver);
	     wait.withTimeout(Duration.ofSeconds(20));
	     wait.pollingEvery(Duration.ofMillis(1000));
	    
	}

	@Given("open swaglabs site {string}")
	public void open_swaglabs_site(String string) 
	{
	    driver.get("https://www.saucedemo.com/");
	}

	@When("Enter {string} and {string} credentials")
	public void enter_and_credentials(String username, String pwd) 
	{
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("user-name"))).sendKeys(username);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(pwd);

	}

	@When("click on signin")
	public void click_on_signin() {
		wait.until(ExpectedConditions.elementToBeClickable(By.name("login-button"))).click();
	}

	@Then("it will redirect to homepage")
	public void it_will_redirect_to_homepage() 
	{
	    WebElement title=driver.findElement(By.xpath("//div[text()='Swag Labs']"));
	    if(title.isDisplayed())
	    {
	    	s.log("We are in Homepage of Swag Labs");
	    	byte[] a=driver.getScreenshotAs(OutputType.BYTES);
			s.attach(a, "image1/png", "Test Passed");
	    }
	    else {
	    	s.log("We are not in Homepage of Swag Labs");
	    	byte[] a=driver.getScreenshotAs(OutputType.BYTES);
			s.attach(a, "image1/png", "Test Failed");
	    	System.exit(0);
	    }
	}

	@Then("verify the homepage has {int} products")
	public void verify_the_homepage_has_products(Integer noOfElement) 
	{
	    List<WebElement> products= driver.findElements(By.xpath("//div[@class='inventory_list']/child::div"));
	    int count=products.size();
	    if(noOfElement==count)
	    {
	    	s.log(" Having 6 products");
	    	byte[] a=driver.getScreenshotAs(OutputType.BYTES);
		s.attach(a, "image2/png", "Test Passed");
	    }
	    else
	    {
	    	s.log("Not Having 6 products");
	    	byte[] a=driver.getScreenshotAs(OutputType.BYTES);
			s.attach(a, "image2/png", "Test Failed");
	    	System.exit(0);
	    }
	}

	@When("add the Product with the highest price to the Cart")
	public void add_the_product_with_the_highest_price_to_the_cart() {
		
		List<WebElement> products= driver.findElements(By.xpath("//div[@class='inventory_list']/descendant::div[@class='inventory_item_price']"));
		
		int max=0;
		for(WebElement product:products)
		{
			driver.executeScript("arguments[0].scrollIntoView();", product);
			String price=product.getText().trim();
			String num=price.replace("$", "");
			num=price.replaceAll("[^0-9]","");
			if(max==0)
			{
				max=Integer.parseInt(num);
			}
			else if(max<Integer.parseInt(num))
			{
				max=Integer.parseInt(num);
				product.findElement(By.xpath("following-sibling::button")).click();
			}
			
		}
	}

	@Then("verify that Product is successfully added to the Cart")
	public void verify_that_product_is_successfully_added_to_the_cart() 
	{
		WebElement e=driver.findElement(By.className("shopping_cart_badge"));
		String number=e.getText();
	    driver.executeScript("arguments[0].scrollIntoView();",e);
	    if(Integer.parseInt(number)==1)
	    {
	    	s.log("Successfully Added to cart");
	    byte[] a=driver.getScreenshotAs(OutputType.BYTES);
		s.attach(a, "image3/png", "Test Passed");
	    }
	    else
	    {
	    	s.log("Unsuccessful to add product to cart");
	    	byte[] a=driver.getScreenshotAs(OutputType.BYTES);
			s.attach(a, "image3/png", "Test Failed");
	    	System.exit(0);
	    }
	    
	}
	@Then("close site")
	public void closesite() 
	{
	    driver.quit();
	}
	
}
