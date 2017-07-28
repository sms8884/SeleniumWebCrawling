package crawler.life.samsung;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import bridge.crawler.entity.Item;
import bridge.crawler.entity.Plan;
import bridge.crawler.entity.PlanState;
import bridge.crawler.service.PlanService;
import bridge.crawler.service.logic.PlanServiceLogic;

import com.inpion.framework.exception.RootException;
import com.inpion.framework.util.Birthday;
import com.inpion.framework.util.InsuranceUtil;
import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

public class SamsungLifeCancelTest {
	private PlanService planService;

	@Before
	public void before() {
		planService = new PlanServiceLogic();
	}

	@After
	public void after() {
		planService = null;
	}

	@Test
	public void getKeys() throws Exception {
		List<String> keys = planService.getKeys("1506110002");
		/* List<String> keys = planService.getKeys("1504290001"); */
		int totalCount = keys.size();
		System.out.println("totalCount: " + totalCount);
		int i = 1;
		for (String key : keys) {

			// System.out.println("longTest = = = = = = ="+Long.valueOf(key));

			int x = i++;
			if (x <= 1) {
				// System.out.println("key <== " + key);
				Plan plan = planService.getPlan("1506110002", key);

				for (Item item : plan.getItems()) {
					System.out.println("item = = = =" + item.toString());
				}
			}
		}
	}

	@Test
	public void getPlan() throws Exception {
		List<String> keys = planService.getKeys("1505070002");
		Plan plan = null;

		// for (String key : keys) {
		try {
			plan = planService.getPlan("1505210001", keys.get(2));
			System.out.println("crawler 실행");
			plan.setState(PlanState.Crawled);
			plan.setValue("암진단보험금-보험료", "33350");
			System.out.println("**" + plan.getMessage());
			planService.putPlan("1505210001", plan);

		} catch (Exception e) {
			plan.setState(PlanState.Error);
			plan.setMessage("오류메시지...");
		}
		// }

		/*
		 * Plan plan = planService.getPlan("1504290002", "0000000001"); plan =
		 * planService.getPlan("1505070002", "0000000001");
		 * 
		 * System.out.println("#########"+plan.getValue("(무)신한Smart인터넷암보험-납입주기"))
		 * ;
		 * 
		 * System.out.println("*********"+plan.toString());
		 * 
		 * for (Item item : plan.getItems()) { System.out.println("\t" +
		 * item.toString()); }
		 */
	}

	@Test
	public void putPlan() throws Exception {
		Plan plan = planService.getPlan("1504290001", "0000000001");

		// 상품번호 , 가설번호

		// 성공시
		plan.setState(PlanState.Crawled);
		plan.setQuote(3450);
		plan.setValue("무배당 뉴원스톱 단계별로더받는암보험 (갱신형) 1형-보험료", "3450");
		plan.setValue("무배당 소액암특약(갱신형)-보험료", "940");

		// 오류시
		// plan.setState(PlanState.Error);
		// plan.setMessage("오류메시지...");

		planService.putPlan("1010100001", plan);

	}

	@Test
	public void stringTest() throws Exception {

		String tmp = " 월 4,626원 납입시";
		String tmp2 = tmp.replace("원 납입시", "");
		String realTotal = tmp2.replace("월", "");
		System.out.println(realTotal.replace(",", ""));

	}

	@Test
	public void executeOnePlan() throws Exception {

		List<WebElement> elements = null;
		WebElement element = null;
		JavascriptLibrary javascript = null;
		javascript = new JavascriptLibrary();

		Map<String, Object> totalList = new HashMap<String, Object>();
		Map<String, Object> elementVal = new HashMap<String, Object>();

		WebDriver driver = new FirefoxDriver();
		List<String> keys = planService.getKeys("1505270002");
		driver.get("http://direct.kdblife.co.kr/edirect/product/cancerDetail");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String tmp = "";

		int i = 1;

		for (String key : keys) {
			int x = i++;
			if (x <= 800) {

				Plan plan = planService.getPlan("1505270002", key);
				System.out.println("total = " + keys.size());
				// Birthday birth =
				// InsuranceUtil.getBirthday(Integer.valueOf(plan.getValue("(무) KDB다이렉트 암보험(갱신형)-나이")));
				Birthday birth = InsuranceUtil.getBirthday(20);
				System.out.println("생년월일" + birth.getYear() + birth.getMonth()
						+ birth.getDay());

				/*
				 * 
				 * 
				 * 
				 * Thread.sleep(2000);
				 * 
				 * driver.findElement(By.id("pYmd")).clear();
				 * 
				 * 
				 * String total =
				 * (String)driver.findElement(By.id("monthAmt1")).getText();
				 * System.out.println("total= =" + total); element =
				 * driver.findElement(By.id("result1")); WebElement testTable;
				 * 
				 * testTable = element.findElement(By.tagName("table"));
				 * 
				 * List<WebElement> rows =
				 * testTable.findElements(By.tagName("tr")); int t = 1; for
				 * (WebElement row : rows) { List<WebElement> cols =
				 * row.findElements(By.tagName("td"));
				 * 
				 * for (WebElement col : cols) {
				 * 
				 * if (col.getText().indexOf("만") != -1) { String colPay =
				 * col.getText().replace("만", ""); colPay = colPay.replace(",",
				 * ""); elementVal.put(t + "", colPay.trim());
				 * System.out.println("***********"+elementVal.get(t+""));
				 * //System.out.println("***********"+elementVal.size()); t++; }
				 * } }
				 */

				/*
				 * String tmp = ""; String testTotal = ""; element =
				 * driver.findElement(By.className("resultSection")); WebElement
				 * testEle= element.findElement(By.tagName("p")); testTotal =
				 * testEle.getText().replace("원", ""); testTotal =
				 * testTotal.replace("납입시", ""); testTotal =
				 * testTotal.replace("월", ""); testTotal =
				 * testTotal.replace(",", ""); testTotal = testTotal.trim();
				 * System.out.println("*******"+testTotal);
				 * 
				 * long total = Long.valueOf(testTotal).longValue();
				 */

				/*
				 * 팝업 스위치 전환 String currentWindowId = driver.getWindowHandle();
				 * Set<String> allWindows = driver.getWindowHandles();
				 * Iterator<String> it = allWindows.iterator(); String popId =
				 * ""; while( it.hasNext()){ System.out.println("iteratorTest "
				 * + it.next()); popId = it.next(); }
				 * System.out.println("popump = " + popId);
				 * 
				 * driver.switchTo().window(popId); driver.close();
				 * driver.switchTo().window(currentWindowId);
				 */

				/*
				 * 
				 * 
				 * List<WebElement> list =
				 * element.findElements(By.tagName("p")); int t = 1;
				 * for(WebElement col : list){ if(col.getText().indexOf("월") !=
				 * -1){ tmp = col.getText().replace("원", ""); tmp =
				 * tmp.replace("납입시", ""); tmp = tmp.replace("월", ""); tmp =
				 * tmp.replace(",", ""); System.out.println("tmp = = = = " +
				 * tmp); totalList.put(t+"",tmp); t++; } }
				 */
				// long total = (long)totalList.get("1");
				/*
				 * System.out.println("LongTest = " + testTotal);
				 * plan.setQuote(total);
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * Map<String, Object> premiumList = new HashMap<String,
				 * Object>(); String premium = ""; String premium2 = ""; String
				 * cancelTable = ""; long tdLength = (long) js.executeScript(
				 * "return  $($('#mLayer1>div>div>div>table')[0]).find('tbody>tr>td').size();"
				 * ); int y = 1; for (long j = 0; j < tdLength; j++) {
				 * 
				 * premium = (String) js.executeScript(
				 * "return  $($('#mLayer1>div>div>div>table')[0]).find('tbody>tr>td')["
				 * +j+"].innerHTML;");
				 * 
				 * 
				 * if(premium.indexOf("만원") != -1){ premium =
				 * premium.replace("만원", ""); premiumList.put(y+"",
				 * premium.replace(",", "")); y++; }
				 * 
				 * } premium2 = (String) js.executeScript(
				 * "return  $($('#mLayer1>div>div>div>table')[2]).find('tbody>tr>td')[1].innerHTML;"
				 * ); cancelTable = (String) js.executeScript(
				 * "return  $($('#mLayer1>div>div>div>table')[3]).html();");
				 * 
				 * System.out.println(cancelTable);
				 * System.out.println(premiumList.get("1"));
				 * System.out.println(premiumList.get("2"));
				 * System.out.println(premiumList.get("3"));
				 * System.out.println(premiumList.get("4"));
				 * System.out.println(premiumList.get("5"));
				 * System.out.println(premium2);
				 */

				/*
				 * element = driver.findElement(By.id("firstInsu")); element =
				 * element.findElement(By.className("btn"));
				 * element.findElement(By.tagName("a")).click();
				 * 
				 * 
				 * WebElement testTable ;
				 * 
				 * element = driver.findElement(By.id("mLayer0")); element =
				 * element.findElement(By.className("layerPopBd")); element =
				 * element.findElement(By.id("tab1")); testTable =
				 * element.findElement(By.tagName("table"));
				 * 
				 * List<WebElement> rows =
				 * testTable.findElements(By.tagName("tr")); int t = 1; for
				 * (WebElement row : rows) { List<WebElement> cols =
				 * row.findElements(By.tagName("td"));
				 * 
				 * for (WebElement col : cols) {
				 * 
				 * if (col.getText().indexOf("만원") != -1) { String colPay =
				 * col.getText().replace("만원", ""); elementVal.put(t + "",
				 * colPay.replace(",", ""));
				 * System.out.println("***********"+elementVal.get(t+""));
				 * //System.out.println("***********"+elementVal.size()); t++; }
				 * }
				 * 
				 * }
				 */

				/*
				 * element = driver.findElement(By.className("resultSection"));
				 * List<WebElement> list =
				 * element.findElements(By.tagName("p")); int t = 1;
				 * for(WebElement col : list){ if(col.getText().indexOf("월") !=
				 * -1){ String tmp = col.getText().replace("원", ""); tmp =
				 * tmp.replace("납입시", ""); tmp = tmp.replace("월", "");
				 * totalList.put(t+"",tmp.replace(",", "")); t++; } }
				 * System.out.println("total = =" + totalList.get("1"));
				 * 
				 * 
				 * 
				 * element = driver.findElement(By.id("firstInsu")); element =
				 * element.findElement(By.tagName("p")); String tmp =
				 * element.getText().replace("원", ""); tmp = tmp.replace("납입시",
				 * ""); tmp = tmp.replace("월", "");
				 * System.out.println("totalTest = "+tmp.replace(",", ""));
				 */

				// driver.quit();
			}

			/*
			 * element = driver.findElement(By.className("edit"));
			 * element.findElement(By.tagName("button")).click();
			 * 
			 * elements = driver.findElements(By.className("insert_data"));
			 * 
			 * element = driver.findElement(By.id("gst_in"));
			 * 
			 * element = driver.findElement(By.id("gst_itime")); for (WebElement
			 * elementItem : element.findElements(By.tagName("option"))) {
			 * String tmp =
			 * plan.getValue("(무)신한Smart인터넷암보험-보험기간").replace("년","");
			 * System.out.println("#####################"+tmp.replace("세", ""));
			 */

			/*
			 * if (elementItem.getAttribute("text").equals(plan.getValue(
			 * "(무)신한Smart인터넷암보험-보험기간"))) {
			 * 
			 * elementItem.click(); break; }
			 */
		}
	}

	// }

	@Test
	public void birthTest() throws Exception {

		List<WebElement> elements = null;
		WebElement element = null;
		List<String> keys = planService.getKeys("1505210001");
		WebDriver driver = new FirefoxDriver();
		driver.get("http://direct.kdblife.co.kr/edirect/product/cancerDetail");

		int age = 20;
		for (String key : keys) {

			Plan plan = planService.getPlan("1505270001", key);
			System.out.println("total = " + keys.size());

			// Birthday birth =
			// InsuranceUtil.getBirthday(Integer.valueOf(plan.getValue("암진단보험금-나이")));

			/*
			 * System.out.println("text = = = = = = "+driver.findElement(By.name(
			 * "pbirthday")).getText());
			 * if(driver.findElement(By.name("pbirthday"
			 * )).getText().equals("")){ System.out.println("널이니라"); }
			 */
			Birthday birth = InsuranceUtil.getBirthday(age);
			System.out.println("생년월일" + birth.getYear() + birth.getMonth()
					+ birth.getDay());
			driver.findElement(By.id("pYmd")).sendKeys(
					birth.getYear() + birth.getMonth() + birth.getDay());
			System.out.println("생년월일2" + birth.getYear() + birth.getMonth()
					+ birth.getDay());

			if (plan.getValue("(무) KDB다이렉트 암보험(갱신형)-성별").equals("여자")) {
				element = driver.findElement(By.id("pGenderF"));
				if (!element.isSelected()) {
					element.click();
				}
			} else {
				element = driver.findElement(By.id("pGenderM"));
				element.click();
			}
			driver.findElement(By.id("calBtn")).click();

			Thread.sleep(2000);

			age++;
			driver.findElement(By.id("pYmd")).clear();
			// driver.close();
		}
	}

	@Test
	public void executeTest() throws Exception {

		List<WebElement> elements = null;
		WebElement element = null;
		JavascriptLibrary javascript = null;
		javascript = new JavascriptLibrary();
		String premium = "";
		String premium2 = "";
		String cancelTable = "";
		String layerId = "";
		Map<String, Object> totalList = new HashMap<String, Object>();
		Map<String, Object> elementVal = new HashMap<String, Object>();
		Map<String, Object> premiumList = new HashMap<String, Object>();

		WebDriver driver = new FirefoxDriver();
		List<String> keys = planService.getKeys("1506110002");
		driver.get("https://lounge.samsunglife.com/annuity/info.eds");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String tmp = "";
		int i = 1;
		for (String key : keys) {
			int x = i++;
			if (x <= 1) {
				Plan plan = planService.getPlan("1506110002", "0000002500");
				System.out.println("total = " + keys.size());
				// Birthday birth =
				// InsuranceUtil.getBirthday(Integer.valueOf(plan.getValue("삼성생명 인터넷정기보험3.0(무배당)-나이")));
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
				
				
				driver.findElement(By.id("planApply")).click();
				
				Thread.sleep(2000);
				
					
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
					 String total = tmp.trim();
					System.out.println("total = " + total);
					
					
						//Thread.sleep(2000);

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
							 System.out.println("premiu" + tmp);
							 totalList.put(t+"",tmp.trim());
							 t++;
					}
					element = driver.findElement(By.id("mLayer0"));
					element = element.findElement(By.id("tab2"));
					element = element.findElement(By.tagName("table"));
					System.out.println( element.getAttribute("outerHTML"));
					
					
					
					element = driver.findElement(By.id("mLayer0"));
					element.findElement(By.tagName("button")).click();
					driver.findElement(By.id("birthday")).clear();
					driver.findElement(By.id("napMoney")).clear();
			}
		}
	}
	// }

}
