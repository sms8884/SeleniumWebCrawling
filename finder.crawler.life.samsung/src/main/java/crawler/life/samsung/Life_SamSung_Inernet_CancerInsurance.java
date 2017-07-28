package crawler.life.samsung;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import bridge.crawler.entity.Plan;
import bridge.crawler.service.PlanService;
import bridge.crawler.service.logic.PlanServiceLogic;

import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

public class Life_SamSung_Inernet_CancerInsurance {

	static List<WebElement> elements = null;
	static WebElement element = null;
	static JavascriptLibrary javascript = null;
	static WebDriver driver = new FirefoxDriver();
	

	public static void main(String[] args) throws Exception {

		final Logger logger = Logger.getLogger(Life_SamSung_Inernet_CancerInsurance.class);
		PlanService planService = new PlanServiceLogic();
		logger.info("크롤링 시작");
		Plan plan = null;
		List<String> keys = null;
		Map<String, Object> totalList = new HashMap<String, Object>();
		Map<String, Object> premiumList = new HashMap<String, Object>();
		String total = "";
		String premium = "";
		String premium2 = "";
		String cancelTable = "";
		String layerId = "";

		javascript = new JavascriptLibrary();
		
		for (String key : keys) {
			if (Long.valueOf(key) > 236) {
				try {
			WebDriver driver = new FirefoxDriver();
			driver.get("https://www.nate.com");
				javascript = new JavascriptLibrary();
				
				}catch (Exception e){
					
				}
				
				element = driver.findElement(By.id("id"));
				element.sendKeys("sms8884");
				
				
				}
			}
	}
	}
/*plan = planService.getPlan("1505210001", key);

					System.out.println("key result = " + key.toString());
					
					Birthday birth = InsuranceUtil.getBirthday(Integer.valueOf(plan.getValue("암진단보험금-나이")));
					System.out.println("암진단보험금나이" + plan.getValue("암진단보험금-나이"));
					System.out.println("생년월일" + birth.getYear() + birth.getMonth() + birth.getDay());
					
					driver.findElement(By.name("pbirthday")).sendKeys(	birth.getYear() + birth.getMonth() + birth.getDay());

					if (plan.getValue("암진단보험금-성별").equals("여자")) {
						element = driver.findElement(By.id("pfemale"));
						if (!element.isSelected()) {
							element.click();
						}
					}else {
						element = driver.findElement(By.id("pmale"));
						element.click();
					}
					driver.findElement(By.id("planApply")).click();
					
					Thread.sleep(2000);
					
					
						
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
					
					if(plan.getValue("암진단보험금-가입금액").equals("2000")){
						
						if(plan.getValue("암사망특약-가입금액").equals("2000")){
							System.out.println("case1");
							System.out.println("암진단보험금 가입금액 - 2000");
							System.out.println("암사망특약 가입금액 - 2000");
							total = (String) totalList.get("1");
							layerId = "mLayer0";
							
						}else{
							System.out.println("case2");
							System.out.println("암진단보험금 가입금액 - 2000");
							System.out.println("암사망특약 가입금액 - 4000");
							total = (String) totalList.get("2");
							layerId = "mLayer1";
						}
					}
					
					if(plan.getValue("암진단보험금-가입금액").equals("3000")){
						
						if(plan.getValue("암사망특약-가입금액").equals("3000")){
							System.out.println("case3");
							System.out.println("암진단보험금 가입금액 - 3000");
							System.out.println("암사망특약 가입금액 - 3000");
							total = (String) totalList.get("3");
							layerId = "mLayer2";
							
						}else{
							System.out.println("case4");
							System.out.println("암진단보험금 가입금액 - 3000");
							System.out.println("암사망특약 가입금액 - 6000");
							total = (String) totalList.get("4");
							layerId = "mLayer3";
						}
					}
					
					
					long tdLength =  (long) js.executeScript("return  $($('#"+layerId+">div>div>div>table')[0]).find('tbody>tr>td').size();");
					int y = 1;
					for (long j = 0; j < tdLength; j++) {
						
						premium = (String) js.executeScript("return  $($('#"+layerId+">div>div>div>table')[0]).find('tbody>tr>td')["+j+"].innerHTML;");
						
						
						if(premium.indexOf("만원") != -1){
							premium = premium.replace("만원", "");
							premiumList.put(y+"", premium.replace(",", ""));
							y++;
						}
						
					}
					premium2  = (String) js.executeScript("return  $($('#"+layerId+">div>div>div>table')[2]).find('tbody>tr>td')[1].innerHTML;");
					premium2 = premium2.replace("만원", "");
					premium2 = premium2.replace(",", "");
					cancelTable ="<table>"+ (String) js.executeScript("return  $($('#"+layerId+">div>div>div>table')[3]).html();")+"</table>";
					
					
					String chkAlert = checkAlert();
					if (!chkAlert.equals("")) {
						throw new RootException(chkAlert);
					}
					
					
					plan.setState(PlanState.Crawled);
					plan.setQuote(Long.valueOf(total));
					plan.setRefund(cancelTable);
					plan.setValue("암진단보험금 보장내역-지급금액1", (String) premiumList.get("1"));
					plan.setValue("암진단보험금 보장내역-지급금액2", (String) premiumList.get("2"));
					plan.setValue("암진단보험금 보장내역-지급금액3", (String) premiumList.get("3"));
					plan.setValue("암진단보험금 보장내역-지급금액4", (String) premiumList.get("4"));
					plan.setValue("암진단보험금 보장내역-지급금액5", (String) premiumList.get("5"));
					plan.setValue("암사망특약 보장내역-지급금액1", premium2);
					System.out.println("total = " + total);
					 planService.putPlan("1505210001", plan);
					 driver.close();
				} catch (Exception e) {

					try {
						System.out.println("ErrorKeyResult' = " + key);
						logger.error("Exception 발생" + e.getMessage());
						e.printStackTrace();
						plan.setState(PlanState.Error);
						plan.setMessage(e.getMessage());
						plan.setQuote(0);
						 planService.putPlan("1505210001", plan);
						 driver.close();

					} catch (Exception e2) {
						logger.error("API통신오류");
						driver.close();
					}
				}
			}
		}
	}

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
	}*/

