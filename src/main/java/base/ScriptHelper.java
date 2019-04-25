package base;

import com.beust.jcommander.internal.Nullable;
import com.google.common.base.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import util.commonfunctions;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static base.BaseFactory.catHash;
import static base.BaseFactory.reportFolder;
import static base.CoreBase.*;

public class ScriptHelper {
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<ExtentTest> eTest = new ThreadLocal<ExtentTest>();
	
	public static WebDriver getDriver() {
		return webDriver.get();
	}

	static void setWebDriver(WebDriver driver) {
		webDriver.set(driver);
	}

	public static ExtentTest getTest() {
		return eTest.get();
	}

	static void setTest(ExtentTest test) {
		eTest.set(test);
	}

	public static void mousehover(WebElement element) {

		WebDriver wb = getDriver();
		waitForPageLoad(wb);
		Actions actions = new Actions(wb);
        actions.moveToElement(element).build().perform();;
	}

	public static void explicitWaitVisibilityOfElement(WebElement element, int time) {
		WebDriverWait elementToBeVisible = new WebDriverWait(getDriver(), time);
		elementToBeVisible.until(ExpectedConditions.visibilityOf(element));
	}

	public static Boolean checkStateSeeking() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		Boolean seekingState = (Boolean) js.executeScript("return document.readyState").equals("complete");
		while (seekingState) {
			seekingState = (Boolean) js.executeScript("return document.readyState").equals("complete");
		}
		return seekingState;
	}

	public static String getConvertedDate(){
		Date today = Calendar.getInstance().getTime();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String datenow = "";
		datenow = df.format(today);
		return datenow;
	}

	public static void createReportFile(StringBuilder filename){

		// Check whether report folder exists.
		File fnFolder = new File(reportFolder);

		if (! fnFolder.exists()){
			fnFolder.mkdirs();
		}

		File fn = new File(reportFolder+ filename);
		if(! fn.exists()){
			File fname = new File(fn.toString());
			try {
				fname.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void navigateToURL() {

		catHash.forEach((key, value) -> {
			String pageName = key;
			String pageURL = value;
			ScriptHelper.getDriver().get(value);
			checkAnyPageContainsError(key);
		});

	}

	public static void checkAnyPageContainsError(String sectionName) {
		WebDriver driver = ScriptHelper.getDriver();
		StringBuilder pageSrc = null;
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body"))));
		pageSrc = new StringBuilder(driver.findElement(By.tagName("body")).getText().replaceAll("[^a-zA-Z0-9]", ""));
		String ErrorMessage = "";

		List<String> errMsgs = new ArrayList<>();
		errMsgs.add("nomatchesfound");
		errMsgs.add("unknownerror");
		errMsgs.add("networkerror");

		boolean flag = true;
		for (String errMsg : errMsgs) {
			if (pageSrc.toString().toLowerCase().contains(errMsg)) {
				ErrorMessage = errMsg;
				flag = false;
			}
		}
		if (flag) {
			reportVP(PASS, sectionName + " - Page loaded correctly as expected.");// <br>[URL: " +
		} else {
			reportVP(FAIL, sectionName + " - Page loaded with ERROR MESSAGE. Error Message type is [ "+ErrorMessage+" ]);// <br> " +
							"[ URL: " + driver.getCurrentUrl()  + "]");
		}
	}
	public static void waitForPageLoad(WebDriver wdriver) {

		// WebDriverWait wait = new WebDriverWait(wdriver, 5, 1000);
		Wait<WebDriver> wait = new FluentWait<>(wdriver).withTimeout(15, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		try {
			//wait.until(AdditionalConditions.angularHasFinishedProcessing());
		} catch (Exception e) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}


		try {
			wait.until(commonfunctions.pageLoadFinished());
		} catch (Exception ee) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		try {
			wait.until(commonfunctions.isJqueryCallDone());
		} catch (Exception ee) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
