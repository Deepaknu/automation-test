package base;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

public class CoreBase extends WebDriverFactory {
	public String getUrlTitle() throws Exception {
		URL aURL = new URL(websiteURL);
		String WebName = aURL.getHost();
		String WebSiteName = WebName.toUpperCase();
		return WebSiteName;
	}

	public void scrollUp() throws Exception {
		((JavascriptExecutor) getWebDriver()).executeScript("scroll(0, -100);");
	}

	public void scrollDown() throws Exception {
		((JavascriptExecutor) getWebDriver()).executeScript("scroll(0, 100);");
	}

	public void mousehover(WebElement element) {
		actions = new Actions(getWebDriver());
		actions.moveToElement(element).build().perform();
	}

	public void JSclick(WebElement element) {
		((JavascriptExecutor) getWebDriver()).executeScript("return arguments[0].click();", element);
	}

	public static void explicitWaitElementToBeClickable(WebElement element, int time) {
		WebDriverWait clickableWait = new WebDriverWait(driver, time);
		clickableWait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/** To Wait Until Element to be Selectable */
	public static void explicitWaitElementToBeSelected(WebElement element, int time) {
		WebDriverWait selectableWait = new WebDriverWait(driver, time);
		selectableWait.until(ExpectedConditions.elementToBeSelected(element));
	}

	/** To Wait Until Element has the text */
	public static void explicitWaitTextToBePresentInElement(WebElement element, int time, String text) {
		WebDriverWait textToBePresent = new WebDriverWait(driver, time);
		textToBePresent.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	/** To Wait Until Title contains the text */
	public static void explicitWaitTitleContains(WebElement element, int time, String title) {
		WebDriverWait titleContains = new WebDriverWait(driver, time);
		titleContains.until(ExpectedConditions.titleContains(title));
	}

	/** To Wait Until Title is */
	public static void explicitWaitTitleIs(WebElement element, int time, String title) {
		WebDriverWait titleIs = new WebDriverWait(driver, time);
		titleIs.until(ExpectedConditions.titleIs(title));
	}

	/** To Wait Until Element to be Visible */
	public static void explicitWaitVisibilityOfElement(WebElement element, int time) {
		WebDriverWait elementToBeVisible = new WebDriverWait(driver, time);
		elementToBeVisible.until(ExpectedConditions.visibilityOf(element));
	}

	/** To Wait Until Element is Selected */
	public static void explicitWaitSelectionStateToBe(WebElement element, int time, boolean selected) {
		WebDriverWait elementIsSelected = new WebDriverWait(driver, time);
		elementIsSelected.until(ExpectedConditions.elementSelectionStateToBe(element, selected));
	}

	/** To Wait Until Elements to be Visible */
	public static void explicitWaitVisibilityOfElements(List<WebElement> element, int time) {
		WebDriverWait elementsToBeVisible = new WebDriverWait(driver, time);
		elementsToBeVisible.until(ExpectedConditions.visibilityOfAllElements(element));
	}

	/* Select using By Method */
	/** To Wait Until Element to be Clickable */
	public static void explicitWaitElementToBeClickable(By element, int time) {
		WebDriverWait clickableWait = new WebDriverWait(driver, time);
		clickableWait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/** To Wait Until Element to be Selectable */
	public static void explicitWaitElementToBeSelected(By element, int time) {
		WebDriverWait selectableWait = new WebDriverWait(driver, time);
		selectableWait.until(ExpectedConditions.elementToBeSelected(element));
	}

	/** To Wait Until Title contains the text */
	public static void explicitWaitTitleContains(By element, int time, String title) {
		WebDriverWait titleContains = new WebDriverWait(driver, time);
		titleContains.until(ExpectedConditions.titleContains(title));
	}

	/** To Wait Until Title is */
	public static void explicitWaitTitleIs(By element, int time, String title) {
		WebDriverWait titleIs = new WebDriverWait(driver, time);
		titleIs.until(ExpectedConditions.titleIs(title));
	}

	/** To Wait Until Element to be Visible */
	public static void explicitWaitVisibilityOfElement(By element, int time) {
		WebDriverWait elementToBeVisible = new WebDriverWait(driver, time);
		elementToBeVisible.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

	/** To Wait Until Element is Selected */
	public static void explicitWaitSelectionStateToBe(By element, int time, boolean selected) {
		WebDriverWait elementToBeVisible = new WebDriverWait(driver, time);
		elementToBeVisible.until(ExpectedConditions.elementSelectionStateToBe(element, selected));
	}

	/** To Wait for the Alert */
	public static void explicitWaitForAlert(int time) {
		WebDriverWait waitForAlert = new WebDriverWait(driver, time);
		waitForAlert.until(ExpectedConditions.alertIsPresent());
	}

	public static boolean checkCacheError(String currentURL) throws Exception {
		int bflag_counter = 0;
		boolean bflag = true;
		String url;
		// Check for the token Number remaining seconds.
		while (bflag_counter < 5 && bflag == true) {
			url = driver.getCurrentUrl();
			if (url.toLowerCase().contains("offline.html") == true || url.toLowerCase().contains("seconds")) {
				Thread.sleep(100000);
				bflag_counter += 1;
			} else {
				bflag_counter = 10;
				bflag = false;
				return bflag;
			}
			driver.get(currentURL);
		}
		return true;
	}

	public boolean checkPageError(String key, String currenturl) throws Exception {
		// Check for Java Script Errors .
		// waitForPageLoad(driver);
		boolean bflag_cacheerror = driver.getPageSource().toLowerCase()
				.contains("this equator website is temporarily unavailable");
		if (bflag_cacheerror) {
			// Cache issue. lets wait and loop through the errors.
			bflag_cacheerror = checkCacheError(currenturl);
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		String PageObject = driver.getPageSource().toLowerCase();

		/*
		 * boolean bflag_goToHomePageerror =
		 * PageObject.contains("click here to go to your home page");
		 *
		 * if ( bflag_goToHomePageerror ){ try { driver.findElement(By.xpath("./
		 *//*
			 * [contains(text(),'go to your home page')]")).click(); Select select = new
			 * Select(driver.findElement(By.cssSelector(
			 * "select[name='change_lender_id']"))); select.selectByValue(lenderValue);
			 * //selectByVisibleText(lenderName); // Verify the Lender Selected.
			 * Thread.sleep(1000); reporter("INFO",
			 * "Selected the required Lender after clicking 'click here to go to your home page' Link"
			 * ,"Click on link completed"); }catch ( Exception e){ e.printStackTrace();
			 * reporter("FATAL",
			 * "Failed to select the required Lender after clicking 'click here to go to your home page' Link"
			 * ,"Click on link failed"); } }
			 */

		String error = (String) ((JavascriptExecutor) driver).executeScript("return window.jsErrors");

		if (!(error == null || error.trim().length() == 0)) {
			// String fileName = new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new
			// Date());
			// fileName = rootReportFolder + "\\Fail_JavaScript_" + lenderName + "_" +
			// fileName;
			String url = driver.getCurrentUrl();
			// assertBoolean(test,"Pass",pair.getKey() +
			// "Link Passed with No Errors","",fileName);
//			assertBoolean(test, "Fail", "", key + " : Link with Java Errors Found.\n Page_URL: [" + currenturl + "]");
			return false;
		}

		// Check for Any Page Error.
		boolean bflag_logoutLink = false;

		boolean bflag = PageObject.contains("unexpected error");
		boolean bflag_Jquery = PageObject.contains("uncaught typeerror");
		boolean bflag_serverError = PageObject.contains("server error");
		boolean bflag_sError = PageObject.contains("500 - internal server error.");
		boolean bflag_serError = PageObject.contains("service unavailable");
		boolean bflag_PageError = PageObject.contains("404 - file or directory not found");
		boolean bflag_error = PageObject.contains("error has occurred");
		boolean bflag_jsonerror = PageObject.contains("caught exception in jsondataset");

		bflag_logoutLink = PageObject.contains("logout");
		/*
		 * boolean loginButton = false; try { loginButton =
		 * driver.findElement(By.id("btnLogin")).isDisplayed(); }catch
		 * (NoSuchElementException e){ loginButton = false; }
		 *
		 * if (loginButton){ Login.User_Login(); }
		 */

		if (bflag_error == true) {
			int counter = 0;
			int errorCounter = 0;
			String str = PageObject;
			// Get the occurrence of error has occurred.

			errorCounter = StringUtils.countMatches(str, "error has occurred");
			counter = StringUtils.countMatches(str, "error has occurred.</option>");

			int i = errorCounter - counter;
			bflag_error = i > 0;
		}
		String[] ignoreErrorLink = { "report.taskreports", "report.propertyreports", "report.adHocStep",
				"report.allreports", "report.offerreports", "report.kpiexecutivedashboard", "reports",
				"report.adhocstep1&reportid=0", "report.timelinetrackingreports", "report.eqsecurityreports" };

		if (bflag_jsonerror || bflag_error || bflag || bflag_serverError || bflag_sError || bflag_Jquery
				|| bflag_PageError || bflag_cacheerror || bflag_serError || !bflag_logoutLink) {
//			String fileName = new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new Date());
//			assertBoolean(test, "Fail", "", key + " : Page with Error Found.\n Page_URL: [" + currenturl + "]");
			return false;
		}

		boolean bflag_aperror = PageObject.contains("we apologize");

		if (bflag_aperror == true && Arrays.asList(ignoreErrorLink).contains(key.trim().toLowerCase())) {
			bflag_aperror = false;
		}

		if (bflag_aperror == true) {
			return true;
			/*
			 * String fileName = new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new
			 * Date()); fileName = rootReportFolder+"\\Warning_"+
			 * NIT.lenderName+"_"+fileName; // assertBoolean(test,"Pass",pair.getKey() +
			 * "Link Passed with No Errors","",fileName); assertBoolean(test,"Warning"
			 * ,key+" : Page with content 'we apologize' Found.\n Page_URL: ["
			 * +currenturl+"]","",fileName); return false;
			 */
		}

		// Check for Log out page has occured.
		// ./*//*[@class='error'] --- XPATH for Logout screen.
		int logoutMessageCount = -1;
		int errorCount = -1;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		logoutMessageCount = driver.findElements(By.xpath(".//*[@class='error']")).size();
		errorCount = driver.findElements(By.xpath(".//*[@class='error']")).size();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		boolean logoutBtn = driver.findElement(By.xpath(".//*[contains(@href,'security.doLogout')]")).isDisplayed();

		// System.out.println(logoutMessageCount);
		// System.out.println(errorCount);
		if ((!logoutBtn)) {

			boolean loginBtn = false;

//			String fileName = new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new Date());
//			fileName = rootReportFolder + "\\Fail_" + lenderName + "_" + fileName;
			try {
				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				loginBtn = driver.findElement(By.name("btnLogin")).isDisplayed();
				logoutBtn = driver.findElement(By.xpath(".//*[contains(@href,'security.doLogout')]")).isDisplayed();
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				if (loginBtn == true && !logoutBtn) {
//					Login.User_Login();
//					assertBoolean(test, "Fail", "", key
//							+ " : Forceful logout when clicked on Link Found ..! \n Page_URL: [" + currenturl + "]");
					return false;
				} else {
//					reporter("PASS",
//							"No login Button Found. Hence setting the flag as PASS, Please verify the Screen shot. ",
//							"No Login Button button");
//					assertBoolean(test, "PASS", "", key
//							+ " : Forceful logout when clicked on Link Found, but didn't found the Login button ..! \n Page_URL: ["
//							+ currenturl + "]");
					return true;
				}

			} catch (NoSuchElementException e) {
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				e.printStackTrace();
//				reporter("FAIL", "No Login Button Found on the Page.Error Message \n" + e.getMessage(), "");
			}
		}
		// Catching the Json Error.
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int count = 0;
		try {
			count = driver.findElements(By.xpath("//*[@id='SpryDebugWindow']")).size();
			if (count > 0) {
//				String fileName = new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new Date());
//				fileName = rootReportFolder + "\\Fail_" + lenderName + "_" + fileName;
//
//				assertBoolean(test, "Fail", "", key + " : Page with Error Found.\n Page_URL: [" + currenturl + "]");
				return false;
			}
		} catch (Exception e) {

		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return true;
	}

	/*
	 * returns the page response code by 1. creating an httpConnection 2. connecting
	 * to the connection 3. getting the response code for the connection
	 */
	private int getPageResponseCode(String url) throws IOException {
		int responseCode = 0;
		try {
			HttpURLConnection httpConnection = createHttpConnection(url);
			httpConnection.connect();
			responseCode = httpConnection.getResponseCode();
		} catch (ProtocolException | SSLException exception) {
			System.out.println("could not open " + url);
		}
		return responseCode;
	}

	// create the httpConnection object
	private HttpURLConnection createHttpConnection(String href) throws IOException {
		URL url = new URL(href);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("GET");
		return huc;
	}

	public boolean checkPageContainsError(String sectionName) {
		StringBuilder pageSrc = new StringBuilder(
				driver.findElement(By.tagName("body")).getText().replaceAll("[^a-zA-Z0-9]", ""));

		List<String> errMsgs = new ArrayList<>();
		errMsgs.add("no matches found");
		errMsgs.add("unknown error");
		errMsgs.add("network error");

		boolean flag = true;

		for (String err : errMsgs) {
			if (pageSrc.toString().toLowerCase().contains(err.replaceAll("[^a-zA-Z0-9]", ""))) {
				System.out.println(
						"Error Msg: " + err + "  is present for  " + sectionName + ", URL: " + driver.getCurrentUrl());
				flag = false;
			} else {
				System.out.println("Error Msg: " + err + " is not present for " + sectionName + ", URL:"
						+ driver.getCurrentUrl() + " as expected");
			}
		}
		return flag;
	}
}