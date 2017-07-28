package crawler.life.samsung;

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

public class Life_SamSung_Inernet_Terminsurance {

	static List<WebElement> elements = null;
	static WebElement element = null;
	static JavascriptLibrary javascript = null;
	static WebDriver driver = new FirefoxDriver();

	public static void main(String[] args) throws Exception {

		final Logger logger = Logger.getLogger(Life_SamSung_Inernet_Terminsurance.class);
		PlanService planService = new PlanServiceLogic();
		logger.info("크롤링 시작");
		Plan plan = null;
		List<String> keys = null;
		Map<String, Object> totalList = new HashMap<String, Object>();
		String total = "";
		String premium = "";
		String cancelTable = "";
		String layerId = "";
		
		try {
			keys = planService.getKeys("1506030001");

		} catch (Exception e) {
			
			logger.error("통신상 에서 오류가 발생"+ e.getMessage());
		}
		String tmp = "";

		javascript = new JavascriptLibrary();
		
		driver.get("https://lounge.samsunglife.com/term/info.eds");
		
		for (String key : keys) {
		//	if (Long.valueOf(key) > 236) {
				try {
				javascript = new JavascriptLibrary();
					plan = planService.getPlan("1506030001", key);

					System.out.println("key result = " + key.toString());
					
					Birthday birth = InsuranceUtil.getBirthday(Integer.valueOf(plan.getValue("삼성생명 인터넷정기보험3.0(무배당)-나이")));
					System.out.println("암진단보험금나이" + plan.getValue("삼성생명 인터넷정기보험3.0(무배당)-나이"));
					System.out.println("생년월일" + birth.getYear() + birth.getMonth() + birth.getDay());
					
					driver.findElement(By.name("pbirthday")).sendKeys(	birth.getYear() + birth.getMonth() + birth.getDay());

					if (plan.getValue("삼성생명 인터넷정기보험3.0(무배당)-성별").equals("여자")) {
						element = driver.findElement(By.id("pfemale"));
						if (!element.isSelected()) {
							element.click();
						}
					}else {
						element = driver.findElement(By.id("pmale"));
						element.click();
					}

					element = driver.findElement(By.id("insTerm"));	
					
					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						int elementSize = 1;
						
						tmp = plan.getValue("삼성생명 인터넷정기보험3.0(무배당)-보험기간").replace("년", "");
						System.out.println("보험기간=== " + tmp);
						System.out.println("보험what = " + elementItem.getAttribute("value"));
						if (elementItem.getAttribute("value").equals(tmp)) {
							elementItem.click();
							break;
						}
						if (element.findElements(By.tagName("option")).size() == elementSize) {
							throw new RootException("보험기간****" + tmp + "값을 선택할수 없습니다");
						}
						elementSize++;
					}
					
					driver.findElement(By.id("planApply")).click();
					
					Thread.sleep(2000);
					
					String currentWindowId = driver.getWindowHandle();
					Set<String> allWindows = driver.getWindowHandles();
					Iterator<String> it = allWindows.iterator();
					String popId = "";
					  while( it.hasNext()){
						System.out.println("iteratorTest " + it.next());
						popId = it.next();
					}
					System.out.println("popump = " + popId);
						
						driver.switchTo().window(popId);
						driver.close();
						driver.switchTo().window(currentWindowId);					

						
						element = driver.findElement(By.className("resultSection"));
						List<WebElement> list =  element.findElements(By.tagName("p"));
						int t  = 1;
						for(WebElement col : list){
							if(col.getText().indexOf("월") != -1){
								 tmp = col.getText().replace("원", "");
								 tmp = tmp.replace("납입시", "");
								 tmp = tmp.replace("월", "");
								 tmp = tmp.replace(",", "");
								 tmp = tmp.trim();
								 totalList.put(t+"",tmp);
								 t++;
							}
						}
					
						
						JavascriptExecutor js = (JavascriptExecutor) driver;
					
						if(plan.getValue("삼성생명 인터넷정기보험3.0(무배당)-가입금액").equals("5000")){
						
							System.out.println("case1");
							System.out.println("가입금액 - 5000");
							total = (String) totalList.get("1");
							layerId = "mLayer0";
							premium = "5000";
						}
	
						if(plan.getValue("삼성생명 인터넷정기보험3.0(무배당)-가입금액").equals("10000")){
							
							System.out.println("case2");
							System.out.println("가입금액 - 10000");
							total = (String) totalList.get("2");
							layerId = "mLayer1";
							premium = "10000";
					}
					
					if(plan.getValue("삼성생명 인터넷정기보험3.0(무배당)-가입금액").equals("15000")){
						
							System.out.println("case3");
							System.out.println(" 가입금액 - 15000");
							total = (String) totalList.get("3");
							layerId = "mLayer2";
							premium = "15000";
					}
							
							if(plan.getValue("삼성생명 인터넷정기보험3.0(무배당)-가입금액").equals("20000")){
							System.out.println("case4");
							System.out.println("가입금액 - 20000");
							total = (String) totalList.get("4");
							layerId = "mLayer3";
							premium = "20000";
					}
					
					cancelTable =(String) js.executeScript("return  $('#"+layerId+"').find('table')[1].outerHTML;");
					System.out.println("cancelTable = " + cancelTable);
					
					String chkAlert = checkAlert();
					if (!chkAlert.equals("")) {
						throw new RootException(chkAlert);
					}
					
					
					plan.setState(PlanState.Crawled);
					plan.setQuote(Long.valueOf(total));
					plan.setRefund(cancelTable);
					plan.setValue("삼성생명 인터넷정기보험3.0(무배당) 보장내역-지급금액1", premium);
					System.out.println("total = " + total);
					 planService.putPlan("1506030001", plan);
					 driver.findElement(By.name("pbirthday")).clear();
				} catch (Exception e) {

					try {
						System.out.println("ErrorKeyResult' = " + key);
						logger.error("Exception 발생" + e.getMessage());
						e.printStackTrace();
						plan.setState(PlanState.Error);
						plan.setMessage(e.getMessage());
						plan.setQuote(0);
						 planService.putPlan("1506030001", plan);
						 driver.findElement(By.name("pbirthday")).clear();

					} catch (Exception e2) {
						logger.error("API통신오류");
						driver.findElement(By.name("pbirthday")).clear();
					}
				}
			}
		logger.info("크롤링완료");
		}
	//}

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
