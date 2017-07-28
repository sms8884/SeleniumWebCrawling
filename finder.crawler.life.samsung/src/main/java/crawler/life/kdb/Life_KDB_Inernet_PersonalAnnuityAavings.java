package crawler.life.kdb;

import java.util.List;
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

public class Life_KDB_Inernet_PersonalAnnuityAavings {

	static List<WebElement> elements = null;
	static WebElement element = null;
	static JavascriptLibrary javascript = null;
	static WebDriver driver = new FirefoxDriver();

	public static void main(String[] args) throws Exception {

		final Logger logger = Logger.getLogger(Life_KDB_Inernet_PersonalAnnuityAavings.class);
		PlanService planService = new PlanServiceLogic();
		logger.info("크롤링 시작");
		Plan plan = null;
		List<String> keys = null;
		String total = "";
		String monthAmt = "";
		String chkAlert = "";
		String tmp = "";
		String premium1 = "";
		String premium2 = "";
		try {
			keys = planService.getKeys("1506100002");

		} catch (Exception e) {
			
			logger.error("통신상 에서 오류가 발생"+ e.getMessage());
		}
		driver.get("http://direct.kdblife.co.kr/edirect/product/pensionDetail");
		javascript = new JavascriptLibrary();
		
		for (String key : keys) {
			//if (Long.valueOf(key) > 10577) {
				javascript = new JavascriptLibrary();
				try {
					plan = planService.getPlan("1506100002", key);

					System.out.println("key result = " + key.toString());
					
					Birthday birth = InsuranceUtil.getBirthday(Integer.valueOf(plan.getValue("(무) KDB다이렉트 연금저축보험-나이")));
					System.out.println("생년월일" + birth.getYear() + birth.getMonth() + birth.getDay());
					driver.findElement(By.id("pYmd")).sendKeys(	birth.getYear() + birth.getMonth() + birth.getDay());

					if (plan.getValue("(무) KDB다이렉트 연금저축보험-성별").equals("여자")) {
						element = driver.findElement(By.id("pGenderF"));
						if (!element.isSelected()) {
							element.click();
						}
					}else {
						element = driver.findElement(By.id("pGenderM"));
						element.click();
					}
					
					
						element = driver.findElement(By.id("pNapTerm"));	
						for (WebElement elementItem : element.findElements(By.tagName("option"))) {
							int elementSize = 1;
							if(plan.getValue("(무) KDB다이렉트 연금저축보험-납입기간").equals("전기납")){
								tmp = "60";
							}else{
								tmp = plan.getValue("(무) KDB다이렉트 연금저축보험-납입기간").replace("년납", "");
							}
							System.out.println("납입기간=== " + tmp);
							System.out.println("납입what = " + elementItem.getAttribute("value"));
							if (elementItem.getAttribute("value").equals(tmp)) {
								elementItem.click();
								break;
							}
							
							if (element.findElements(By.tagName("option")).size() == elementSize) {
								throw new RootException("납입기간****" + tmp + "값을 선택할수 없습니다");
							}
							elementSize++;
						}
					
						driver.findElement(By.id("pPayAmt")).sendKeys(plan.getValue("(무) KDB다이렉트 연금저축보험-가입금액"));
						driver.findElement(By.id("pBeginAge")).sendKeys(plan.getValue("(무) KDB다이렉트 연금저축보험-연금개시나이"));
						driver.findElement(By.id("calBtn")).click();
					
					 chkAlert = checkAlert();
					if (!chkAlert.equals("")) {
						throw new RootException(chkAlert);
					}
					
					Thread.sleep(2000);
					
					
					total = plan.getValue("(무) KDB다이렉트 연금저축보험-가입금액");
					total = total.trim();
					
					chkAlert = checkAlert();
					if (!chkAlert.equals("")) {
						throw new RootException(chkAlert);
					}
					
					
					plan.setState(PlanState.Crawled);
					plan.setQuote(Long.valueOf(total));
					plan.setValue("(무) KDB다이렉트 연금저축보험_종신연금형(정액형)_보장내역-지급금액1",driver.findElement(By.id("L10")).getText().replace(",", ""));
					plan.setValue("(무) KDB다이렉트 연금저축보험_종신연금형(정액형)_보장내역-지급금액2",driver.findElement(By.id("L20")).getText().replace(",", ""));
					plan.setValue("(무) KDB다이렉트 연금저축보험_종신연금형(정액형)_보장내역-지급금액3",driver.findElement(By.id("L30")).getText().replace(",", ""));
					plan.setValue("(무) KDB다이렉트 연금저축보험_종신연금형(정액형)_보장내역-지급금액4",driver.findElement(By.id("L100")).getText().replace(",", ""));
					plan.setValue("(무) KDB다이렉트 연금저축보험_확정연금형 (연금지급기간별)_보장내역-지급금액1",driver.findElement(By.id("F10")).getText().replace(",", ""));
					plan.setValue("(무) KDB다이렉트 연금저축보험_확정연금형 (연금지급기간별)_보장내역-지급금액2",driver.findElement(By.id("F15")).getText().replace(",", ""));
					plan.setValue("(무) KDB다이렉트 연금저축보험_확정연금형 (연금지급기간별)_보장내역-지급금액3",driver.findElement(By.id("F20")).getText().replace(",", ""));
					plan.setValue("(무) KDB다이렉트 연금저축보험_확정연금형 (연금지급기간별)_보장내역-지급금액4",driver.findElement(By.id("F25")).getText().replace(",", ""));
					plan.setValue("(무) KDB다이렉트 연금저축보험_확정연금형 (연금지급기간별)_보장내역-지급금액5",driver.findElement(By.id("F30")).getText().replace(",", ""));
					System.out.println("total = " + total);
					 driver.findElement(By.id("pYmd")).clear();
					 driver.findElement(By.id("pPayAmt")).clear();
					driver.findElement(By.id("pBeginAge")).clear();
					 planService.putPlan("1506100002", plan);

				} catch (Exception e) {

					try {
						System.out.println("ErrorKeyResult' = " + key);
						logger.error("Exception 발생" + e.getMessage());
						e.printStackTrace();
						plan.setState(PlanState.Error);
						plan.setMessage(e.getMessage());
						plan.setQuote(0);
						driver.findElement(By.id("pYmd")).clear();
						driver.findElement(By.id("pPayAmt")).clear();
						driver.findElement(By.id("pBeginAge")).clear();
						 planService.putPlan("1506100002", plan);

					} catch (Exception e2) {
						driver.findElement(By.id("pYmd")).clear();
						driver.findElement(By.id("pPayAmt")).clear();
						driver.findElement(By.id("pBeginAge")).clear();
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
				driver.findElement(By.id("pPayAmt")).clear();
				driver.findElement(By.id("pBeginAge")).clear();
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
