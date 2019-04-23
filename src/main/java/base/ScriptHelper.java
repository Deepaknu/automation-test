package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static base.BaseFactory.reportFolder;

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
		Actions actions = new Actions(getDriver());
		actions.moveToElement(element).build().perform();
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
}
