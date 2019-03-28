package base;

import java.io.File;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import util.LoadConfigFile;

public class BaseFactory extends CoreBase {
	public static String reportFolder = "./Results/";
	private static String BROWSER;
	private static String URL;
	private static boolean SPOOFINGENABLED;
	public static ExtentReports extent;

	static WebDriver createInstance() {
		WebDriver driver = null;
		LoadConfigFile.getInstance();
		BROWSER = LoadConfigFile.getValue("DefaultBrowser");
		URL = LoadConfigFile.getValue("ApplicationUrl");
		SPOOFINGENABLED = LoadConfigFile.getValue("SpoofingRequired").contains("true");
		long implicitlyWait = Integer.parseInt(LoadConfigFile.getValue("ImplicitlyWait"));
		long maxPageLoadTime = Integer.parseInt(LoadConfigFile.getValue("MaxPageLoadTime"));

		try {
			if (BROWSER.toLowerCase().contains("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				FirefoxProfile profile = new FirefoxProfile();
				FirefoxOptions ffoptions = new FirefoxOptions();
				if (SPOOFINGENABLED) {
					profile.setPreference("general.useragent.override", LoadConfigFile.getValue("UserAgent"));
				}
				ffoptions.setProfile(profile);
				driver = new FirefoxDriver(ffoptions);
			} else {
				WebDriverManager.chromedriver().setup();
				ChromeOptions choptions = new ChromeOptions();
				if (SPOOFINGENABLED) {
					choptions.addArguments("--user-agent=" + LoadConfigFile.getValue("UserAgent"));
				}
				choptions.addArguments("disable-infobars");
				choptions.addArguments("--disable-extensions");
				driver = new ChromeDriver(choptions);
			}

			driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(maxPageLoadTime, TimeUnit.SECONDS);

			driver.get(URL);
			driver.manage().window().maximize();
		}

		catch (Exception e) {
			System.out.println(e.getMessage() + "");
		}
		return driver;
	}

	static ExtentTest createTestInstance(String testName) {
		ExtentTest test = extent.createTest(testName);
		System.out.println("####MESSAGE::ExtentReport-Test-Instance created successfully for " + testName
				+ ". Hashcode:" + test.hashCode());
		return test;
	}

	static ExtentReports createExtentReportInstance() {
		Date date = new Date();
		StringBuilder FILE_NAME = new StringBuilder();
		FILE_NAME.append(date.toString().replace(":", "_").replace(" ", "_"));
		reportFolder = reportFolder + FILE_NAME + "/";
		FILE_NAME.append(".html").toString();
		File dirFile = new File(reportFolder + "screenshots");
		dirFile.mkdirs();
		setReportPath(reportFolder);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFolder + FILE_NAME);
		htmlReporter.config().setDocumentTitle("EddieBauer - Automation Execution");
		htmlReporter.config().setReportName("Smoke Test Suite - Results");
		// htmlReporter.config().enableTimeline(true);
		htmlReporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		System.out.println("####MESSAGE::ExtentReport-Instance created successfully. Hashcode:" + extent.hashCode());

		extent.setSystemInfo("Author", "EddieBauer-QE Automation Team");
		extent.setSystemInfo("Browser", "browser");
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("OS Version", System.getProperty("os.version"));
		extent.setSystemInfo("OS Architecture", System.getProperty("os.arch"));
		extent.setSystemInfo("OS Bit", System.getProperty("sun.arch.data.model"));
		extent.setSystemInfo("JAVA Version", System.getProperty("java.version"));
		try {
			extent.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
			extent.setSystemInfo("Host IP Address", InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
		}

		return extent;
	}

	static void setReportPath(String path) {
		reportFolder = path;
	}

}