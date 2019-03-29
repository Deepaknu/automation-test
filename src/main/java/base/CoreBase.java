package base;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import util.LoadConfigFile;

public class CoreBase {

	public static ExtentReports extent;

	public static final String PASS = "PASS";
	public static final String FAIL = "FAIL";
	public static final String WARNING = "WARNING";
	public static final String INFO = "INFO";

	@BeforeSuite
	public void beforeSuite() {
		killBrowserDriverInstances();
		extent = BaseFactory.createExtentReportInstance();
	}

	@AfterSuite
	public void afterSuite() {
		extent.flush();
		System.out.println("####MESSAGE::ExtentReport- Final report flushed successfully.");

	}

	private void killBrowserDriverInstances() {
		Process process = null;
		if (System.getProperty("os.name").contains("Windows")) {
			try {
				process = Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
				process = Runtime.getRuntime().exec("taskkill /F /IM firefoxdriver.exe /T");
			} catch (IOException e) {

				e.printStackTrace();
			}
			process.destroy();
		}
	}

	public static String capureScreen() {
		String name = UUID.randomUUID().toString();
		try {
			File scrFile = ((TakesScreenshot) ScriptHelper.getDriver()).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(BaseFactory.reportFolder + "screenshots/" + name + ".jpg"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return name;
	}

	public static void reportVP(String stepStatus, String reportDescription) {

		LoadConfigFile.getInstance();
		boolean screenshotForPassStep = LoadConfigFile.getValue("TakeScreenshotForPassedStep").contains("true");
		boolean screenshotForFailStep = LoadConfigFile.getValue("TakeScreenshotForFailedStep").contains("true");
		boolean screenshotForAllSteps = LoadConfigFile.getValue("TakeScreenshotForAllSteps").contains("true");

		if (screenshotForAllSteps) {
			screenshotForPassStep = true;
			screenshotForFailStep = true;
		}

		ExtentTest tes = ScriptHelper.getTest();
		MediaEntityModelProvider img = null;

		try {

			if (stepStatus.equalsIgnoreCase(Status.PASS.toString())) {
				tes.pass("PASSED: " + reportDescription);
				if (screenshotForPassStep) {
					String scrnshtName = capureScreen();
					scrnshtName = "./screenshots/" + scrnshtName + ".jpg";
					img = MediaEntityBuilder.createScreenCaptureFromPath(scrnshtName).build();
					tes.pass("Refer Screenshot: [URL:" + ScriptHelper.getDriver().getCurrentUrl() + "]", img);
				}
			} else if (stepStatus.equalsIgnoreCase(Status.FAIL.toString())) {
				tes.fail(MarkupHelper.createLabel("FAILED: " + reportDescription, ExtentColor.RED));
				if (screenshotForFailStep) {
					String scrnshtName = capureScreen();
					scrnshtName = "./screenshots/" + scrnshtName + ".jpg";
					img = MediaEntityBuilder.createScreenCaptureFromPath(scrnshtName).build();
					tes.fail("Refer Screenshot: [URL:" + ScriptHelper.getDriver().getCurrentUrl() + "]", img);
				}
			} else if (stepStatus.equalsIgnoreCase(Status.WARNING.toString())) {
				tes.warning(MarkupHelper.createLabel("WARNING: " + reportDescription, ExtentColor.ORANGE));
				String scrnshtName = capureScreen();
				scrnshtName = "./screenshots/" + scrnshtName + ".jpg";
				img = MediaEntityBuilder.createScreenCaptureFromPath(scrnshtName).build();
				tes.warning("Refer Screenshot: [URL:" + ScriptHelper.getDriver().getCurrentUrl() + "]", img);
			} else if (stepStatus.equalsIgnoreCase(Status.INFO.toString())) {
				tes.info("INFO: " + reportDescription);
				if (screenshotForAllSteps) {
					String scrnshtName = capureScreen();
					scrnshtName = "./screenshots/" + scrnshtName + ".jpg";
					img = MediaEntityBuilder.createScreenCaptureFromPath(scrnshtName).build();
					tes.info("Refer Screenshot: [URL:" + ScriptHelper.getDriver().getCurrentUrl() + "]", img);
				}
			}
		} catch (IOException e) {
			System.out.println("Exception occured building Media Entity Builder :: " + e.getMessage());
		}
	}
}
