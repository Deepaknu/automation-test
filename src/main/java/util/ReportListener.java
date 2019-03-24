package util;

import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import base.ScriptHelper;
import base.WebDriverFactory;

public class ReportListener extends ScriptHelper implements IReporter {

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

		Date date = new Date();
		StringBuilder FILE_NAME = new StringBuilder();
		FILE_NAME.append(date.toString().replace(":", "_").replace(" ", "_"));
		FILE_NAME.append(".html");

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
		htmlReporter.config().setDocumentTitle("EddieBauer - Automation Execution");
		htmlReporter.config().setReportName("Smoke Test Suite - Results");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setTheme(Theme.STANDARD);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setReportUsesManualConfiguration(true);
		for (Object suite : suites) {
			Map result = ((ISuite) suite).getResults();

			for (Object res : result.values()) {
				ITestContext context = ((ISuiteResult) res).getTestContext();

				try {
					buildTestNodes(context.getFailedTests(), Status.FAIL);
					buildTestNodes(context.getSkippedTests(), Status.SKIP);
					buildTestNodes(context.getPassedTests(), Status.PASS);
				} catch (Exception e) {
				}
			}
		}
		for (String s : Reporter.getOutput()) {
			extent.setTestRunnerOutput(s);
		}
		extent.setSystemInfo("Author", "EddieBauer-QE Automation Team");
		extent.setSystemInfo("Browser", browser);
		extent.setSystemInfo("OS", OSName);
		extent.setSystemInfo("OS Version", OSVersion);
		extent.setSystemInfo("OS Architecture", OSArchitecture);
		extent.setSystemInfo("OS Bit", OSBit);
		extent.setSystemInfo("JAVA Version", System.getProperty("java.version"));
		try {
			extent.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
			extent.setSystemInfo("Host IP Address", InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
		}
		extent.flush();
	}

	private void buildTestNodes(IResultMap tests, Status status) throws Exception {
		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.createTest(result.getMethod().getMethodName());
				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);
				if (result.getThrowable() != null) {
					test.log(status, result.getThrowable().getMessage());
				} else {
					test.log(status, "Test " + status.toString().toLowerCase() + "ed");
				}

//				if (result.getStatus() == ITestResult.SUCCESS) {
//					String sTimestamp=getTimestamp() ;
//					test.pass("Step Passed: " + result.getTestClass().getName() + "." + result.getMethod().getMethodName(),
//							MediaEntityBuilder.createScreenCaptureFromPath("./Images/" + sTimestamp+ ".png")
//									.build());
//					test.addScreenCaptureFromPath("./Images/" + sTimestamp+ ".png");
//
//				}
				if (result.getStatus() == ITestResult.FAILURE) {
					 
					test.fail(
							"Failure occured. Refer the screenshot below: " + result.getTestClass().getName() + "."
									+ result.getMethod().getMethodName(),
							MediaEntityBuilder.createScreenCaptureFromPath("./Images/" + result.getTestClass().getName()
									+ "." + result.getMethod().getMethodName() + ".png").build());
					test.addScreenCaptureFromPath("./Images/" + result.getTestClass().getName() + "."
							+ result.getMethod().getMethodName() + ".png");
					 
				}
				test.getModel().setStartTime(getTime(result.getStartMillis()));
				test.getModel().setEndTime(getTime(result.getEndMillis()));
			}
		}
	}

	private String getTimestamp() {

		return new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
}
