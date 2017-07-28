package crawler.life.kdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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

public class Life_KDB_Inernet_CancerInsurance_Type_renewal {

	static List<WebElement> elements = null;
	static WebElement element = null;
	static JavascriptLibrary javascript = null;
	static WebDriver driver = new FirefoxDriver();

	public static void main(String[] args) throws Exception {

		final Logger logger = Logger.getLogger(Life_KDB_Inernet_CancerInsurance_Type_renewal.class);
		PlanService planService = new PlanServiceLogic();
		logger.info("크롤링 시작");
		Plan plan = null;
		List<String> keys = null;
		Map<String, Object> premiumList = new HashMap<String, Object>();
		WebElement premiumTable ;
		String total = "";
		String result = "";
		String monthAmt = "";
		String chkAlert = "";
		
		try {
			keys = planService.getKeys("1505270001");

		} catch (Exception e) {
			
			logger.error("통신상 에서 오류가 발생"+ e.getMessage());
		}
		driver.get("http://direct.kdblife.co.kr/edirect/product/cancerDetail");
		javascript = new JavascriptLibrary();
		
		for (String key : keys) {
			//if (Long.valueOf(key) > 10577) {
				javascript = new JavascriptLibrary();
				try {
					plan = planService.getPlan("1505270001", key);

					System.out.println("key result = " + key.toString());
					
					Birthday birth = InsuranceUtil.getBirthday(Integer.valueOf(plan.getValue("(무) KDB다이렉트 암보험(갱신형)-나이")));
					System.out.println("생년월일" + birth.getYear() + birth.getMonth() + birth.getDay());
					driver.findElement(By.id("pYmd")).sendKeys(	birth.getYear() + birth.getMonth() + birth.getDay());

					if (plan.getValue("(무) KDB다이렉트 암보험(갱신형)-성별").equals("여자")) {
						element = driver.findElement(By.id("pGenderF"));
						if (!element.isSelected()) {
							element.click();
						}
					}else {
						element = driver.findElement(By.id("pGenderM"));
						element.click();
					}
					driver.findElement(By.id("calBtn")).click();
					
					
					 chkAlert = checkAlert();
					if (!chkAlert.equals("")) {
						throw new RootException(chkAlert);
					}
					
					
					
					Thread.sleep(2000);
					
					if(plan.getValue("(무) KDB다이렉트 암보험(갱신형)-가입금액").equals("1250")){
						System.out.println("가입금액 - 1250");
						element = driver.findElement(By.id("result0"));
						if(element.getCssValue("display").equals("none")){
							throw new RootException("선택된 가입금액으로 가입할수없습니다");
						}
						result = "result0";
						monthAmt = "monthAmt0";
						
					}
					if(plan.getValue("(무) KDB다이렉트 암보험(갱신형)-가입금액").equals("2500")){
						System.out.println("가입금액 - 2500");
						element = driver.findElement(By.id("result1"));
						if(element.getCssValue("display").equals("none")){
							throw new RootException("선택된 가입금액으로 가입할수없습니다");
						}
						result = "result1";
						monthAmt = "monthAmt1";
						
					}
					
					
					total = driver.findElement(By.id(monthAmt)).getText();
					total = total.replace(",", "");
					total = total.trim();
					element = driver.findElement(By.id(result));
					premiumTable = element.findElement(By.tagName("table"));		
				
					List<WebElement> rows = premiumTable.findElements(By.tagName("tr"));
					int t = 1;
					for (WebElement row : rows) {
						List<WebElement> cols = row.findElements(By.tagName("td"));

						for (WebElement col : cols) {

							if (col.getText().indexOf("만") != -1) {
							String colPay = col.getText().replace("만", "");
									  colPay = colPay.replace(",", ""); 	
									  premiumList.put(t + "", colPay.trim());
								System.out.println("***********"+premiumList.get(t+""));
								//System.out.println("***********"+elementVal.size());
								t++;
							}
						}

					}
					
					chkAlert = checkAlert();
					if (!chkAlert.equals("")) {
						throw new RootException(chkAlert);
					}
					
					
					plan.setState(PlanState.Crawled);
					plan.setQuote(Long.valueOf(total));
					plan.setValue("(무) KDB다이렉트 암보험(갱신형) 보장내역-지급금액1", (String) premiumList.get("1"));
					plan.setValue("(무) KDB다이렉트 암보험(갱신형) 보장내역-지급금액2", (String) premiumList.get("2"));
					plan.setValue("(무) KDB다이렉트 암보험(갱신형) 보장내역-지급금액3", (String) premiumList.get("3"));
					plan.setValue("(무) KDB다이렉트 암보험(갱신형) 보장내역-지급금액4", (String) premiumList.get("4"));
					System.out.println("total = " + total);
					 driver.findElement(By.id("pYmd")).clear();
					 planService.putPlan("1505270001", plan);

				} catch (Exception e) {

					try {
						System.out.println("ErrorKeyResult' = " + key);
						logger.error("Exception 발생" + e.getMessage());
						e.printStackTrace();
						plan.setState(PlanState.Error);
						plan.setMessage(e.getMessage());
						plan.setQuote(0);
						driver.findElement(By.id("pYmd")).clear();
						 planService.putPlan("1505270001", plan);

					} catch (Exception e2) {
						driver.findElement(By.id("pYmd")).clear();
						logger.error("API통신오류");
					}
				}
			}
		logger.info("크롤링 완료");
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
				driver.findElement(By.id("pYmd")).clear();
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
