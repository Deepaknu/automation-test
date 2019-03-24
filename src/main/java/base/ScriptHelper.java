package base;

import java.awt.Robot;
import java.io.File;
import java.net.URI;

import org.openqa.selenium.Alert;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
 
public class ScriptHelper {
	

	public static String websiteURL;
	public static String browser;
	public static boolean spoofingEnabled;
	public static int maxPageLoadTime;
	public static int implicitlyWait;

	public static String FS = File.separator;

	public static String OSName = System.getProperty("os.name");
	public static String OSArchitecture = System.getProperty("os.arch");
	public static String OSVersion = System.getProperty("os.version");
	public static String OSBit = System.getProperty("sun.arch.data.model");

	public static String ProjectWorkingDirectory = System.getProperty("user.dir");
 
	public static String Reports = ".Results/";
	public static String Images = "./Results/Images/";

	public static Robot re;
	public static Alert al;
	public static String robotImageName;
	public static Select se;
	public static String FileToUpload;
	public static Actions actions;
	public static ITestResult testResult;
	public static SoftAssert softAssert;
	public static ITestResult result;
	public static URI uri;

	public static final String OUTPUT_FOLDER = "./Results/";

	public static ExtentReports extent;
	public static ISuite suite;
	public static ISuiteResult res;
	public static ExtentTest test;
}
