package crawler.life.samsung;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

public class H90045 {
	
	static JavascriptLibrary javascript = null;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		javascript = new JavascriptLibrary();
		
		WebDriver driver = new FirefoxDriver();
		
		driver.get("http://product.samsunglife.com/product/insu/product/rule/ruleCalculation.do?hCode=H90045&layoutType=1");
		
		driver.findElement(By.id("name_1")).sendKeys("홍길동");
		driver.findElement(By.id("ageYear_1")).sendKeys("1977");
		
		WebElement element = driver.findElement(By.id("ageMonth_1"));
		List<WebElement> elements = element.findElements(By.tagName("option"));
		System.out.println(elements.size());
		for (WebElement elementItem: elements) {
			if (elementItem.getAttribute("value").equals("08")) {
				elementItem.click();
				break;
			}
		}
		
		element = driver.findElement(By.id("ageDay_1"));
		elements = element.findElements(By.tagName("option"));
		System.out.println(elements.size());
		for (WebElement elementItem: elements) {
			if (elementItem.getAttribute("value").equals("01")) {
				elementItem.click();
				break;
			}
		}
		
		elements = driver.findElements(By.id("sex_11"));
		for (WebElement elementItem: elements) {
			if (elementItem.getAttribute("value").equals("1")) {
				elementItem.click();
				break;
			}
		}
		
		element = driver.findElement(By.id("fxprs_I12835"));
		element.sendKeys(Keys.BACK_SPACE + "1000");
		javascript.callEmbeddedSelenium(driver, "doFireEvent", element, "blur");
		
		element = driver.findElement(By.id("fxprs_I19930"));
		element.sendKeys(Keys.BACK_SPACE + "2000");
		javascript.callEmbeddedSelenium(driver, "doFireEvent", element, "blur");
		
		driver.findElement(By.id("btnCalc")).click();
	}
	
}
