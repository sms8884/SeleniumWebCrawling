package crawler.life.lina;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

public class Life_Llina {
	
	static List<WebElement> elements = null;
	static WebElement element = null;
	static JavascriptLibrary javascript = null;

	public static void main(String[] args) throws InterruptedException {
		javascript = new JavascriptLibrary();
		
		WebDriver driver = new FirefoxDriver();
		
		driver.get("http://www.lina.co.kr/product/simulation01.htm?paramCenterCode=WEB-DSCL&paramProductCode=CCP&paramProductType=N");
		
		driver.findElement(By.id("name")).sendKeys("홍길동");
		driver.findElement(By.id("iresid_no1")).sendKeys("19770801");
		driver.findElement(By.id("policy_period")).sendKeys("10");
		driver.findElement(By.id("pay_period")).sendKeys("10");
	System.out.println(	driver.findElement(By.id("pay_period")).getText());
																																																																																							
		driver.findElement(By.id("product_amount")).sendKeys("1000");
		
		elements = driver.findElements(By.name("rider_amount"));
		
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
		
		element = elements.get(2);
		for (WebElement elementItem: element.findElements(By.tagName("option"))) {
			if (elementItem.getAttribute("value").equals("1000")) {
				elementItem.click();
				break;
			}
		}
		element = elements.get(3);
		for (WebElement elementItem: element.findElements(By.tagName("option"))) {
			if (elementItem.getAttribute("value").equals("1000")) {
				elementItem.click();
				break;
			}
		}
		
		element = elements.get(4);
		for (WebElement elementItem: element.findElements(By.tagName("option"))) {
			if (elementItem.getAttribute("value").equals("1000")) {
				elementItem.click();
				break;
			}
		}
		element = elements.get(5);
		for (WebElement elementItem: element.findElements(By.tagName("option"))) {
			if (elementItem.getAttribute("value").equals("1000")) {
				elementItem.click();
				break;
			}
		}
		element = elements.get(6);
		for (WebElement elementItem: element.findElements(By.tagName("option"))) {
			if (elementItem.getAttribute("value").equals("1000")) {
				elementItem.click();
				break;
			}
		}
		
		
		element = driver.findElement(By.className("btnprice")).findElement(By.tagName("a"));
		element.click();
		
		Thread.sleep(1000);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("goStep2()");
		
		
		
		

		/*System.out.println("****"+driver.findElement(By.className("prmTable")).getText());
	String tmp = 	driver.findElement(By.className("prmTable")).getText();
	System.out.println(tmp);*/
		/*WebElement simpleTable  = driver.findElement(By.className("prmTable"));
		List<WebElement> rows = (List<WebElement>) simpleTable.findElement(By.tagName("tr"));
		for(WebElement row: rows) {
			List<WebElement> cols  = row.findElements(By.tagName("td"));
			for(WebElement col : cols ) {
				System.out.println(col.getText() + "/t");
			}
			System.out.println();
		}*/
		/*driver.quit();*/
	}
}
