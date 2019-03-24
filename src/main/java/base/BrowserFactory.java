package base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Listeners;

import io.github.bonigarcia.wdm.WebDriverManager;
import util.LoadConfigFile;

@Listeners({ util.ReportListener.class })
public class BrowserFactory extends ScriptHelper {
	protected static WebDriver driver;

	public BrowserFactory() {
		LoadConfigFile.getInstance();
		browser = LoadConfigFile.getValue("DefaultBrowser");
		websiteURL = LoadConfigFile.getValue("ApplicationUrl");
		spoofingEnabled = LoadConfigFile.getValue("SpoofingRequired").contains("true");
	}

	static DesiredCapabilities capabilities;

	static WebDriver createDriver() throws Exception {

		switch (browser.toLowerCase()) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions choptions = new ChromeOptions();
			if (spoofingEnabled) {
				choptions.addArguments("--user-agent=" + LoadConfigFile.getValue("UserAgent"));
			}
			choptions.addArguments("disable-infobars");
			//choptions.addArguments("--start-maximized");
			choptions.addArguments("--disable-extensions");
			driver = new ChromeDriver(choptions);
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxProfile profile = new FirefoxProfile();
			FirefoxOptions ffoptions = new FirefoxOptions();
			String spoofingEnabled = LoadConfigFile.getValue("spoofingEnabled");
			if (spoofingEnabled.equalsIgnoreCase("true")) {
				profile.setPreference("general.useragent.override", LoadConfigFile.getValue("UserAgent"));
			}
			ffoptions.setProfile(profile);
			driver = new FirefoxDriver(ffoptions);
			break;

		case "ie":
		case "internetexplorer":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		default:
			throw new NotFoundException("Browser Not Found. Please Provide a Valid Browser");
		}

		if (implicitlyWait > 0) {
			implicitlyWait(implicitlyWait);
		}

		if (maxPageLoadTime > 0) {
			setMaxPageLoadTime(maxPageLoadTime);
		}
		driver.manage().window().maximize();
		driver.get(websiteURL);
		return driver;
	}

	public static void implicitlyWait(int timeInSeconds) throws Exception {
		driver.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
	}

	public static void setMaxPageLoadTime(int timeInSeconds) throws Exception {
		driver.manage().timeouts().pageLoadTimeout(timeInSeconds, TimeUnit.SECONDS);
	}

}
