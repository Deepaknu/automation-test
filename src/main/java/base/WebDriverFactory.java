package base;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import util.LoadConfigFile;

public class WebDriverFactory extends BrowserFactory {
	public static ThreadLocal<WebDriver> wd = new ThreadLocal<WebDriver>();

	@BeforeMethod
	public void beforeMethod() throws Exception {
		System.out.println("Browser: " + browser);
		System.out.println("WebsiteURL: " + websiteURL);
		new WebDriverFactory();
		WebDriver driver = WebDriverFactory.createDriver();
		setWebDriver(driver);
	}

	public void setWebDriver(WebDriver driver) {
		wd.set(driver);
	}

	public static WebDriver getWebDriver() {
		return wd.get();
	}

	public static void saveFullPageScreenshot(String name) throws IOException {
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
				.takeScreenshot(getWebDriver());
		ImageIO.write(screenshot.getImage(), "PNG", new File(name));
	}

	@AfterMethod(alwaysRun = true, enabled = true)
	public void afterMethod(ITestResult result) throws Exception {
		Thread.sleep(1);
		if (result.getStatus() == ITestResult.FAILURE) {
			saveFullPageScreenshot(
					"./Results/Images/" + result.getTestClass().getName() + "." + result.getMethod().getMethodName()+".png");
		}

		getWebDriver().quit();
	}

//	public static String takeScreenShot(String methodName) {
//		String path = "./Results/Images/" + methodName + ".png";
//		try {
//			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//			FileUtils.copyFile(screenshotFile, new File(path));
//		} catch (Exception e) {
//			System.out.println("Could not write screenshot" + e);
//		}
//		return path;
//	}

	 
}
