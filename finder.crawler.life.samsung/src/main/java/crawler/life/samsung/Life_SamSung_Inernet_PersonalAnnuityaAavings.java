package crawler.life.samsung;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import bridge.crawler.entity.Plan;
import bridge.crawler.entity.PlanState;
import bridge.crawler.service.PlanService;
import bridge.crawler.service.logic.PlanServiceLogic;

import com.inpion.framework.exception.RootException;
import com.inpion.framework.util.Birthday;
import com.inpion.framework.util.InsuranceUtil;
import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

public class Life_SamSung_Inernet_PersonalAnnuityaAavings {

	static List<WebElement> elements = null;
	static WebElement element = null;
	static JavascriptLibrary javascript = null;
	static WebDriver driver = new FirefoxDriver();

	public static void main(String[] args) throws Exception {

		final Logger logger = Logger.getLogger(Life_SamSung_Inernet_PersonalAnnuityaAavings.class);
		PlanService planService = new PlanServiceLogic();
		logger.info("크롤링 시작");
		Plan plan = null;
		List<String> keys = null;
		Map<String, Object> premiumList = new HashMap<String, Object>();
		String total = "";
		String cancelTable = "";
		String chkAlert = "";
		try {
			keys = planService.getKeys("1506110002");

		} catch (Exception e) {
			
			logger.error("통신상 에서 오류가 발생"+ e.getMessage());
		}
		String tmp = "";

		driver.get("https://lounge.samsunglife.com/annuity/info.eds");
		javascript = new JavascriptLibrary();
		
		for (String key : keys) {
			if (Long.valueOf(key) > 10852) {
				try {
				javascript = new JavascriptLibrary();
					plan = planService.getPlan("1506110002", key);

					System.out.println("key result = " + key.toString());
					Birthday birth = InsuranceUtil.getBirthday(Integer.valueOf(plan.getValue("삼성생명인터넷연금저축보험1.2(무배당)-나이")));
					driver.findElement(By.id("birthday")).sendKeys(	birth.getYear() + birth.getMonth() + birth.getDay());

					if (plan.getValue("삼성생명인터넷연금저축보험1.2(무배당)-성별").equals("여자")) {
						element = driver.findElement(By.id("pfemale"));
						if (!element.isSelected()) {
							element.click();
						}
					}else {
						element = driver.findElement(By.id("pmale"));
						element.click();
					}
					

					element = driver.findElement(By.id("napTerm"));	
					
					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						int elementSize = 1;
						
						if(plan.getValue("삼성생명인터넷연금저축보험1.2(무배당)-납입기간").equals("전기납")){
							tmp = "99";
						}else{
							tmp = plan.getValue("삼성생명인터넷연금저축보험1.2(무배당)-납입기간").replace("년납", "");
						}
						
						System.out.println("Plan납입기간=== " + tmp);
						System.out.println("사이트납입기간 = " + elementItem.getAttribute("value"));
						if (elementItem.getAttribute("value").equals(tmp)) {
							elementItem.click();
							break;
						}
						if (element.findElements(By.tagName("option")).size() == elementSize) {
							throw new RootException("납입기간****" + tmp + "값을 선택할수 없습니다");
						}
						elementSize++;
					}
					
					element = driver.findElement(By.id("annAge"));	
					
					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						int elementSize = 1;
							tmp = plan.getValue("삼성생명인터넷연금저축보험1.2(무배당)-연금개시나이").trim();
						
						System.out.println("Plan개시나이=== " + tmp);
						System.out.println("사이트개시나이 = " + elementItem.getAttribute("value"));
						if (elementItem.getAttribute("value").equals(tmp)) {
							elementItem.click();
							break;
						}
						if (element.findElements(By.tagName("option")).size() == elementSize) {
							throw new RootException("개시나이****" + tmp + "값을 선택할수 없습니다");
						}
						elementSize++;
					}
					
					driver.findElement(By.id("napMoney")).sendKeys(	plan.getValue("삼성생명인터넷연금저축보험1.2(무배당)-가입금액"));
					
					
					
					chkAlert = checkAlert();
					if (!chkAlert.equals("")) {
						throw new RootException(chkAlert);
					}
					
					driver.findElement(By.id("planApply")).click();
					
					
					
					Thread.sleep(2000);
					chkAlert = checkAlert();
					if (!chkAlert.equals("")) {
						throw new RootException(chkAlert);
					}
					
					String currentWindowId = driver.getWindowHandle();
					  Set<String> allWindows = driver.getWindowHandles();
					  Iterator<String> it = allWindows.iterator(); String popId =
					  ""; while( it.hasNext()){ System.out.println("iteratorTest "
					  + it.next()); popId = it.next(); }
					  System.out.println("popump = " + popId);
					  driver.switchTo().window(popId); driver.close();
					  driver.switchTo().window(currentWindowId);
					
						
						element = driver.findElement(By.id("firstInsu"));
						element = element.findElement(By.tagName("p"));
						tmp = element.getText().replace("원", "");
						 tmp = tmp.replace("납입시", "");
						 tmp = tmp.replace("월", "");
						 tmp = tmp.replace("만", "");
						 tmp = tmp.replace(",", "");
						 total = tmp.trim();								 
					
						 
							element = driver.findElement(By.id("firstInsu"));
							element = element.findElement(By.tagName("div"));
							element = element.findElement(By.tagName("a"));
							element.click();
							
							element = driver.findElement(By.id("mLayer0"));
							element = element.findElement(By.id("tab1"));
							List<WebElement> list =  element.findElements(By.tagName("em"));
							int t  = 1;
							for(WebElement col : list){
									 tmp = col.getText().replace(",", "");
									 premiumList.put(t+"",tmp.trim());
									 t++;
							}
							element = driver.findElement(By.id("mLayer0"));
							element = element.findElement(By.id("tab2"));
							element = element.findElement(By.tagName("table"));
							cancelTable =   element.getAttribute("outerHTML");
							
							element = driver.findElement(By.id("mLayer0"));
							element.findElement(By.tagName("button")).click();
				
					
					plan.setState(PlanState.Crawled);
					plan.setQuote(Long.valueOf(total));
					plan.setRefund(cancelTable);
					plan.setValue("삼성생명인터넷연금저축보험1.2(무배당) 보장내역-지급금액1", (String) premiumList.get("1"));
					plan.setValue("삼성생명인터넷연금저축보험1.2(무배당) 보장내역-지급금액2", (String) premiumList.get("2"));
					plan.setValue("삼성생명인터넷연금저축보험1.2(무배당) 보장내역-지급금액3", (String) premiumList.get("3"));
					plan.setValue("삼성생명인터넷연금저축보험1.2(무배당) 보장내역-지급금액4", (String) premiumList.get("4"));
					plan.setValue("삼성생명인터넷연금저축보험1.2(무배당) 보장내역-지급금액5", (String) premiumList.get("5"));
					plan.setValue("삼성생명인터넷연금저축보험1.2(무배당) 보장내역-지급금액6", (String) premiumList.get("6"));
					plan.setValue("삼성생명인터넷연금저축보험1.2(무배당) 보장내역-지급금액7", (String) premiumList.get("7"));
					plan.setValue("삼성생명인터넷연금저축보험1.2(무배당) 보장내역-지급금액8", (String) premiumList.get("8"));
					plan.setValue("삼성생명인터넷연금저축보험1.2(무배당) 보장내역-지급금액9", (String) premiumList.get("9"));
					System.out.println("total = " + total);
					 planService.putPlan("1506110002", plan);
					 driver.findElement(By.id("birthday")).clear();
					 driver.findElement(By.id("napMoney")).clear();
				} catch (Exception e) {

					try {
						System.out.println("ErrorKeyResult' = " + key);
						logger.error("Exception 발생" + e.getMessage());
						e.printStackTrace();
						plan.setState(PlanState.Error);
						plan.setMessage(e.getMessage());
						plan.setQuote(0);
						 planService.putPlan("1506110002", plan);
						 driver.findElement(By.id("birthday")).clear();
						 driver.findElement(By.id("napMoney")).clear();

					} catch (Exception e2) {
						logger.error("API통신오류");
						driver.findElement(By.id("birthday")).clear();
						driver.findElement(By.id("napMoney")).clear();
					}
				}
			}
			
		}
		logger.info("크롤링 완료");
	}

	protected static String checkAlert() {

		String alertMessage = "";
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(1, TimeUnit.SECONDS).pollingEvery(100, TimeUnit.MILLISECONDS).ignoring(StaleElementReferenceException.class);

		try {
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			if (alert != null) {
				alertMessage = alert.getText();
				alert.accept();
				return alertMessage;

			}
		} catch (Exception e) {

		}
		if (!alertMessage.equals("")) {
			throw new RootException("Popup창이 생성되었습니다.");
		}
		return "";
	}
}
