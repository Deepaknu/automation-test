package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.CoreBase;
import base.ScriptHelper;
import base.SuiteListener;
import pages.LandingPage;

@Listeners(SuiteListener.class)
public class TestScript01 extends CoreBase {
	static WebDriver driver;

	@Test
	public void verifyMenMenuLinks() {
		verifyAnyMenuLinks("Men");
	}

	@Test
	public void verifyWomenMenuLinks() {
		verifyAnyMenuLinks("Women");
	}

	@Test
	public void verifyOuterwearMenuLinks() {
		verifyAnyMenuLinks("Outerwear");
	}

	@Test
	public void verifyGearMenuLinks() {
		verifyAnyMenuLinks("Gear");
	}

	@Test
	public void verifyHomeMenuLinks() {
		verifyAnyMenuLinks("Home");
	}

	@Test
	public void verifyGuideToEBMenuLinks() {
		verifyAnyMenuLinks("GuideToEB");
	}

	@Test
	public void verifyClearanceMenuLinks() {
		verifyAnyMenuLinks("Clearance");
	}

	private void verifyAnyMenuLinks(String menu) {
		driver = ScriptHelper.getDriver();

		LandingPage landingPage = new LandingPage();
		landingPage.hoverToAnyMenu(menu);
		reportVP(INFO, "Hovering on " + menu + " Menu is completed");

		verifySubCategoryMenu(landingPage, menu);
		reportVP(PASS, menu + " Menu - Sub category links have been verified successfully");
	}

	@SuppressWarnings("rawtypes")
	private void verifySubCategoryMenu(LandingPage landingPage, String menuName) {
		HashMap<String, String> links = new HashMap<>();
		boolean flag = false;
		links = landingPage.getSubCategoryLinks();
		// reportVP(INFO,
		// "Retriving all the hyperlinks for " + menuName + " menu is completed. Total
		// links:" + links.size());

		for (Map.Entry m : links.entrySet()) {
			//System.out.println(m.getKey() + "--" + m.getValue());
			driver.get(m.getValue().toString());

			flag = this
					.checkAnyPageContainsError(m.getKey().toString().substring(m.getKey().toString().indexOf("_") + 1));
		}
		Assert.assertTrue(flag, menuName + " Menu - links verification");
	}

	public boolean checkAnyPageContainsError(String sectionName) {
		driver = ScriptHelper.getDriver();
		StringBuilder pageSrc = new StringBuilder(
				driver.findElement(By.tagName("body")).getText().replaceAll("[^a-zA-Z0-9]", ""));
		List<String> errMsgs = new ArrayList<>();
		errMsgs.add("nomatchesfound");
		errMsgs.add("unknownerror");
		errMsgs.add("networkerror");

		boolean flag = true;
		for (String errMsg : errMsgs) {
			if (pageSrc.toString().toLowerCase().contains(errMsg)) {
				reportVP(WARNING, "Error Msg: " + errMsg + "  is present for  " + sectionName + "[, URL: "
						+ driver.getCurrentUrl() + "]");
				flag = flag && false;
			} else {
				reportVP(PASS, "Error Msg: " + errMsg + "  is not present as expected for  " + sectionName + "[, URL: "
						+ driver.getCurrentUrl() + "]");
				flag = flag && true;
			}
		}
		return flag;
	}

}
