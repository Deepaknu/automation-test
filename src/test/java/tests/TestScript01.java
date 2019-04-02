package tests;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.CoreBase;
import base.ScriptHelper;
import base.SuiteListener;
import pages.LandingPage;

@Listeners(SuiteListener.class)
public class TestScript01 extends CoreBase {
	static WebDriver driver;

//	@Test
//	public void verifyMenMenuLinks() {
//		verifyAnyMenuLinks("Men");
//	}
//
//	@Test
//	public void verifyWomenMenuLinks() {
//		verifyAnyMenuLinks("Women");
//	}
//
//	@Test
//	public void verifyOuterwearMenuLinks() {
//		verifyAnyMenuLinks("Outerwear");
//	}
//
//	@Test
//	public void verifyGearMenuLinks() {
//		verifyAnyMenuLinks("Gear");
//	}
//
//	@Test
//	public void verifyHomeMenuLinks() {
//		verifyAnyMenuLinks("Home");
//	}
//
//	@Test
//	public void verifyGuideToEBMenuLinks() {
//		verifyAnyMenuLinks("Guide");
//	}
//
//	@Test
//	public void verifyClearanceMenuLinks() {
//		verifyAnyMenuLinks("Clearance");
//	}

	@Test
	public void verifySearchFlow() {
		driver = ScriptHelper.getDriver();
		LandingPage landingPage = new LandingPage();
		landingPage.hoverToAnyMenu("Men");
		reportVP(INFO, "Hovering on Menu is completed");
		landingPage.clickOnAnySubCategory("Boots");
		reportVP(INFO, "Boots - Sub Category is clicked");
		landingPage.clickFirstProduct();
		String productName = landingPage.getProductTitle();
		reportVP(INFO, "Navigating to " + productName);
		landingPage.SearchAnyValue(productName);
		reportVP(INFO, "Search for <" + productName + "> is completed");
		landingPage.clickFirstProduct();
		String actualProductName = landingPage.getProductTitle();
		reportVP(INFO, "Actual product name: " + productName);
		boolean flag = productName.contentEquals(actualProductName);
		if (flag) {
			reportVP(PASS, "Product displayed is same as the expected one after Search");
		} else {
			reportVP(FAIL, "Product displayed is NOT same as the expected one after Search");
		}
		assertTrue(flag, "Product is displayed same as the expected one after Search");
	}

	private void verifyAnyMenuLinks(String menu) {
		driver = ScriptHelper.getDriver();

		LandingPage landingPage = new LandingPage();
		landingPage.hoverToAnyMenu(menu);
		reportVP(INFO, "Hovering on " + menu + " Menu is completed");
		verifySubCategoryMenu(landingPage, menu);
	}

	@SuppressWarnings("rawtypes")
	private void verifySubCategoryMenu(LandingPage landingPage, String menuName) {
		HashMap<String, String> links = new HashMap<>();
		links = landingPage.getSubCategoryLinks();

		int expectedLinks = 0;
		switch (menuName) {
		case "Men":
			expectedLinks = 42;
			break;
		case "Women":
			expectedLinks = 47;
			break;
		case "Outerwear":
			expectedLinks = 20;
			break;
		case "Gear":
			expectedLinks = 10;
			break;
		case "Home":
			expectedLinks = 8;
			break;
		case "Guide":
			expectedLinks = 30;
			break;
		case "Clearance":
			expectedLinks = 17;
			break;
		}

		if (expectedLinks == links.size()) {
			reportVP(PASS, "Total number of Sub category links for " + menuName
					+ " menu has been verified. Total links:" + links.size());
		} else {
			reportVP(FAIL, "Total number of Sub category links for " + menuName + " menu has NOT verified. Total links:"
					+ links.size() + "Expected links" + expectedLinks);

		}
		for (Map.Entry m : links.entrySet()) {
			driver.get(m.getValue().toString());
			this.checkAnyPageContainsError(m.getKey().toString().substring(m.getKey().toString().indexOf("_") + 1));
		}
	}

	public void checkAnyPageContainsError(String sectionName) {
		driver = ScriptHelper.getDriver();
		StringBuilder pageSrc = null;
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body"))));
		pageSrc = new StringBuilder(driver.findElement(By.tagName("body")).getText().replaceAll("[^a-zA-Z0-9]", ""));

		List<String> errMsgs = new ArrayList<>();
		errMsgs.add("nomatchesfound");
		errMsgs.add("unknownerror");
		errMsgs.add("networkerror");
		boolean flag = true;
		for (String errMsg : errMsgs) {
			if (pageSrc.toString().toLowerCase().contains(errMsg)) {
				flag = false;
			}
		}
		if (flag) {
			reportVP(PASS, sectionName + " - Page loaded correctly as expected.");// <br>[URL: " +
		} else {
			reportVP(FAIL, sectionName + " - Page loaded with ERROR MESSAGE.");// <br> [ URL: " + driver.getCurrentUrl()
																				// + "]");
		}
	}
}
