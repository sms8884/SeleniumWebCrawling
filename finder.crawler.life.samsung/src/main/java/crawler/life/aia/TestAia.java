package crawler.life.aia;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

public class TestAia {
	
	static List<WebElement> elements = null;
	static WebElement element = null;
	static JavascriptLibrary javascript = null;

	public static void main(String[] args) throws InterruptedException {
		javascript = new JavascriptLibrary();
		
		WebDriver driver = new FirefoxDriver();
		
		driver.get("http://www.aia.co.kr/renewal/customer/pricenotice_1_list.jsp?planNo=PL20101_01|4|7");
		
		driver.findElement(By.id("custNm")).sendKeys("홍길동");
		driver.findElement(By.id("brthDt")).sendKeys("19770801");
		/*driver.findElement(By.id("policy_period")).sendKeys("10");
		driver.findElement(By.id("pay_period")).sendKeys("10");
		driver.findElement(By.id("product_amount")).sendKeys("1000");*/
		
		/*elements = driver.findElements(By.name("rider_amount"));
		
		element = elements.get(0);
		for (WebElement elementItem: element.findElements(By.tagName("option"))) {
			if (elementItem.getAttribute("value").equals("1000")) {
				elementItem.click();
				break;
			}
		}
		
		element = elements.get(1);
		for (WebElement elementItem: element.findElements(By.tagName("option"))) {
			if (elementItem.getAttribute("value").equals("1000")) {
				elementItem.click();
				break;
			}
		}
		
		element = driver.findElement(By.className("btnprice")).findElement(By.tagName("a"));
		element.click();
		
		Thread.sleep(1000);
		
		element = driver.findElement(By.id("premium"));
		String premium = element.getText().replace("원", "").replace(",", "");
		System.out.println(premium);
		*/
		
		driver.quit();
	}
	
}
