package crawler.life.shinhan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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

public class Life_Shinhan_Inernet_Terminsurance_Type_DisasterGuarantee {

	static List<WebElement> elements = null;
	static WebElement element = null;
	static JavascriptLibrary javascript = null;
	static WebDriver driver = new FirefoxDriver();

	public static void main(String[] args) throws Exception {

		final Logger logger = Logger.getLogger(Life_Shinhan_Inernet_Terminsurance_Type_DisasterGuarantee.class);
		PlanService planService = new PlanServiceLogic();
		logger.info("크롤링 시작");
		Plan plan = null;
		List<String> keys = null;
		try {
			keys = planService.getKeys("1505080004");

		} catch (Exception e) {
			
			logger.error("통신상 에서 오류가 발생"+ e.getMessage());
		}
		String tmp = "";

		driver.get("http://e.shinhanlife.co.kr/planTermSe.ids");
		javascript = new JavascriptLibrary();
		element = driver.findElement(By.id("pro_plan"));

		if (element.getCssValue("display").equals("block")) {
			element.findElement(By.tagName("a")).click();
		}
		element = driver.findElement(By.className("pcheck"));
		element.findElement(By.tagName("a")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String tmpTable = (String) js.executeScript("var obj = $('#phex_popup'); return obj.find('table').html();");
		String cancelReturnTable = "<table>" + tmpTable + "</table>";

		System.out.println(cancelReturnTable);

		js.executeScript("javascript:history.back();");

		Thread.sleep(5000);

		
		for (String key : keys) {
			//if (Long.valueOf(key) > 10577) {

				javascript = new JavascriptLibrary();
				try {
					plan = planService.getPlan("1505080004", key);

					element = driver.findElement(By.id("pro_plan"));

					if (element.getCssValue("display").equals("block")) {
						element.findElement(By.tagName("a")).click();
					}

					element = driver.findElement(By.className("edit"));
					element.findElement(By.tagName("button")).click();

					elements = driver.findElements(By.className("insert_data"));

					element = driver.findElement(By.id("gst_in"));
					System.out.println("key result = " + key.toString());

					Birthday birth = InsuranceUtil.getBirthday(Integer.valueOf(plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-나이")));

					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						if (elementItem.getAttribute("text").equals(plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-성별"))) {
							elementItem.click();
							break;
						}
					}

					element = driver.findElement(By.id("sel_year_in"));
					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						if (elementItem.getAttribute("value").equals(birth.getYear())) {
							elementItem.click();
							break;
						}
					}

					element = driver.findElement(By.id("sel_month_in"));
					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						if (elementItem.getAttribute("value").equals(birth.getMonth())) {
							elementItem.click();
							break;
						}
					}
					element = driver.findElement(By.id("sel_day_in"));
					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						if (elementItem.getAttribute("value").equals(birth.getDay())) {
							elementItem.click();
							break;
						}
					}
					element = driver.findElement(By.id("sel_gst_istatic"));
					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						if (elementItem.getAttribute("text").equals("재해보장형")) {
							elementItem.click();
							break;
						}
					}

					element = driver.findElement(By.id("gst_itime"));
					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						int elementSize = 1;
						tmp = plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-보험기간").replace("년", "");
						System.out.println("보험기간=== " + tmp);
						System.out.println("보험what = " + elementItem.getAttribute("text"));
						// System.out.println("SIZESIZE = = = ="+element.findElements(By.tagName("option")).size());
						if (elementItem.getAttribute("text").equals(tmp)) {
							elementItem.click();
							break;
						}
						if (element.findElements(By.tagName("option")).size() == elementSize) {
							throw new RootException("보험기간****" + tmp + "값을 선택할수 없습니다");
						}
						elementSize++;
					}

					element = driver.findElement(By.id("gst_paid"));
					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						int elementSize = 1;
						tmp = plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-납입기간").replace("년납", "");
						System.out.println("납입기간 === " + tmp);
						if (elementItem.getAttribute("text").equals(tmp)) {
							elementItem.click();
							break;
						}
						if (element.findElements(By.tagName("option")).size() == elementSize) {
							throw new RootException("납입기간****" + tmp + "값을 선택할수 없습니다");
						}
						elementSize++;
					}

					element = driver.findElement(By.id("gst_pam"));

					for (WebElement elementItem : element.findElements(By.tagName("option"))) {
						if (plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-납입주기").equals("년납")) {
							tmp = "매년";
						} else {
							tmp = "매월";
						}
						System.out.println("납입주기 === " + tmp);
						if (elementItem.getAttribute("text").equals(tmp)) {
							System.out.println("5");
							elementItem.click();
							break;
						}
					}

					element = driver.findElement(By.className("plan_btn"));
					element.findElement(By.tagName("button")).click();

					Thread.sleep(1000);
					String chkAlert = checkAlert();
					if (!chkAlert.equals("")) {
						throw new RootException(chkAlert);
					}

					String total = "";
					if (plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액").equals("5000")) {
						System.out.println("가입금액 = = =" + plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액"));
						element = driver.findElement(By.id("it61"));
						element.click();
						total = driver.findElement(By.id("it61")).getText().replace(",", "");
						plan.setValue("보장내역-지급금액1", plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액"));
						plan.setValue("보장내역-지급금액2", "10000");

					} else if (plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액").equals("10000")) {
						System.out.println("가입금액 = = =" + plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액"));
						element = driver.findElement(By.id("it71"));
						element.click();

						total = driver.findElement(By.id("it71")).getText().replace(",", "");
						plan.setValue("보장내역-지급금액1", plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액"));
						plan.setValue("보장내역-지급금액2", "20000");

					} else if (plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액").equals("15000")) {
						System.out.println("가입금액 = = =" + plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액"));
						element = driver.findElement(By.id("it81"));
						element.click();
						total = driver.findElement(By.id("it81")).getText().replace(",", "");
						plan.setValue("보장내역-지급금액1", plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액"));
						plan.setValue("보장내역-지급금액2", "30000");

					} else if (plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액").equals("20000")) {
						System.out.println("가입금액 = = =" + plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액"));
						element = driver.findElement(By.id("it91"));
						element.click();
						total = driver.findElement(By.id("it91")).getText().replace(",", "");
						plan.setValue("보장내역-지급금액1", plan.getValue("(무)신한Smart인터넷 정기보험(재해보장형)-가입금액"));
						plan.setValue("보장내역-지급금액2", "40000");
					}

					plan.setState(PlanState.Crawled);
					plan.setQuote(Long.valueOf(total));
					System.out.println("total = " + total);
					plan.setRefund(cancelReturnTable);
					 planService.putPlan("1505080004", plan);

				} catch (Exception e) {

					try {
						System.out.println("ErrorKeyResult' = " + key);
						logger.error("Exception 발생" + e.getMessage());
						e.printStackTrace();
						plan.setState(PlanState.Error);
						plan.setMessage(e.getMessage());
						plan.setQuote(0);
						 planService.putPlan("1505080004", plan);

					} catch (Exception e2) {
						logger.error("API통신오류");
					}
				}
			}
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
				element = driver.findElement(By.className("plan_btn"));
				element = element.findElement(By.className("ccl"));
				element.findElement(By.tagName("button")).click();
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
