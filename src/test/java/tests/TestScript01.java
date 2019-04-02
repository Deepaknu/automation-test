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

	@Test
	public void verifySubCategoryLinkTexts() {
		this.verifySubCategoryMenuTexts("Men");
		this.verifySubCategoryMenuTexts("Women");
		this.verifySubCategoryMenuTexts("Outerwear");
		this.verifySubCategoryMenuTexts("Gear");
		this.verifySubCategoryMenuTexts("Home");
		this.verifySubCategoryMenuTexts("Guide");
		this.verifySubCategoryMenuTexts("Clearance");

	}

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
		verifyAnyMenuLinks("Guide");
	}

	@Test
	public void verifyClearanceMenuLinks() {
		verifyAnyMenuLinks("Clearance");
	}

	@Test
	public void verifySearchFlow() {
		try {
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
		} catch (Exception e) {
			reportVP(FAIL, "Exception occured. " + e.getStackTrace());
		}
	}

	@Test
	public void verifyCheckOutFlow() {
		try {
			driver = ScriptHelper.getDriver();
			LandingPage landingPage = new LandingPage();
			landingPage.hoverToAnyMenu("Men");
			reportVP(INFO, "Hovering on Menu is completed");
			landingPage.clickOnAnySubCategory("Boots");
			reportVP(INFO, "Boots - Sub Category is clicked");
			landingPage.clickFirstProduct();
			String productName = landingPage.getProductTitle();
			reportVP(INFO, "Selected product is: " + productName);
			landingPage.chooseAnySizeForTheShoe("10.5");
			reportVP(INFO, "Boots - Size <10.5> is choosed");
			landingPage.clickOnAddToBag();
			reportVP(INFO, "Product is added to BAG: " + productName);

			boolean flag = landingPage.verifyAddToBagOverlay(productName);
			if (flag) {
				reportVP(PASS, "Product is added to the overlay");
			} else {
				reportVP(FAIL, "Product is NOT added to the overlay");
			}

			landingPage.continueToCheckOut();
			reportVP(INFO, "Proceeding to Checkout with product: " + productName);
			flag = landingPage.verifyCheckOutPageContainsTheProduct(productName);

			if (flag) {
				reportVP(PASS, "The product is available in Cart Page: " + productName);
			} else {
				reportVP(FAIL, "The product is NOT available in Cart Page: " + productName);
			}
			flag = landingPage.proceedToCheckOut();
			if (flag) {
				reportVP(PASS, "PROCEED TO CHECKOUT redirects to LoginPage as expected");
			} else {
				reportVP(FAIL, "PROCEED TO CHECKOUT NOT redirected to LoginPage");
			}
			assertTrue(flag, "Product is successfully checked out. ");
		} catch (Exception e) {
			reportVP(FAIL, "Exception occured. " + e.getStackTrace());
		}
	}

	private void verifyAnyMenuLinks(String menu) {
		try {
			driver = ScriptHelper.getDriver();
			LandingPage landingPage = new LandingPage();
			landingPage.hoverToAnyMenu(menu);
			reportVP(INFO, "Hovering on " + menu + " Menu is completed");
			verifySubCategoryMenu(landingPage, menu);
		} catch (Exception e) {
			reportVP(FAIL, "Exception occured. " + e.getStackTrace());
		}
	}

	@SuppressWarnings("rawtypes")
	private void verifySubCategoryMenu(LandingPage landingPage, String menuName) {
		HashMap<String, String> links = new HashMap<>();
		links = landingPage.getSubCategoryLinks();

		int expectedLinkCount = 0;
		String expectedLinks = null;
		switch (menuName) {
		case "Men":
			expectedLinkCount = 42;
			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelRainwearSpringSaleClearanceACTIVITYHikingTravelFishingFieldTrainingJACKETSVESTSParkasCasualInsulatedHardShellsSoftShellsRainwear3in1VestsBOTTOMSPantsJeansShortsBaselayersTOPSShirtsShirtJacketsTShirtsSweatersSweatshirtsHoodiesBaselayersFLEECETechnicalCasualFOOTWEARBootsHikingCasualSandalsSlippersSocksACCESSORIESBeltsGlovesHatsScarvesSunglassesSocks";
			break;
		case "Women":
			expectedLinkCount = 47;
			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelRainwearSpringSaleClearanceACTIVITYHikingTravelFishingFieldTrainingJACKETSVESTSParkasCasualInsulatedHardShellsSoftShellsRainwear3in1VestsBOTTOMSPantsJeansTightsCaprisShortsSkirtsSkortsBaselayersTOPSShirtsTShirtsTankTopsSweatersSweatshirtsHoodiesSleepDressesBaselayersFLEECETechnicalCasualFOOTWEARBootsHikingCasualSandalsSlippersSocksACCESSORIESBeltsGlovesHatsScarvesSunglassesSocks";
			break;
		case "Outerwear":
			expectedLinkCount = 20;
			expectedLinks = "MENParkasCasualInsulatedHardShellsSoftShellsRainwear3in1FleeceVestsWOMENParkasCasualInsulatedHardShellsSoftShellsRainwear3in1FleeceVestsBOYSJacketsVestsGIRLSJacketsVests";
			break;
		case "Gear":
			expectedLinkCount = 10;
			expectedLinks = "GEARBackpacksDuffelsLuggageMessengerLaptopBagsTravelAccessoriesTentsSleepingBagsStovesCookwareHydrationLightingToteBags";
			break;
		case "Home":
			expectedLinkCount = 8;
			expectedLinks = "HOMEBlanketsThrowsSheetsPillowcasesDuvetCoversShamsComfortersPillowsMattressPadsOutdoorFurnitureHardwoodFlooring";
			break;
		case "Guide":
			expectedLinkCount = 30;
			expectedLinks = "FEATUREDAdventureTravelRainwearACTIVITYHikingTravelFishingFieldTrainingSnowAlpineABOUTUSOurFounderGuidesAthletesExpeditionsResponsibleSourcingAwardWinnersCareersPARTNERSHIPSBigCityMountaineersAmericanHikingSocietyTheHeroesProjectAmericanForests";
			break;
		case "Clearance":
			expectedLinkCount = 17;
			expectedLinks = "MENJacketsVestsBottomsTopsFleeceFootwearAccessoriesWOMENJacketsVestsBottomsTopsFleeceFootwearAccessoriesGEARBackpacksDuffelsLuggageMessengerLaptopBagsTravelAccessoriesTentsSleepingBagsStovesCookwareHydrationLightingToteBagsHOMEBlanketsThrowsSheetsPillowcasesDuvetCoversShamsComfortersPillowsMattressPadsOutdoorFurnitureHardwoodFlooring";

			break;
		}

		for (Map.Entry m : links.entrySet()) {
			driver.get(m.getValue().toString());
			this.checkAnyPageContainsError(m.getKey().toString().substring(m.getKey().toString().indexOf("_") + 1));
		}
		if (expectedLinkCount == links.size()) {
			reportVP(PASS, "Total number of Sub category links for " + menuName
					+ " menu has been verified. Total links:" + links.size());

		} else {
			reportVP(FAIL, "Total number of Sub category links for " + menuName + " menu has NOT verified. Total links:"
					+ links.size() + "Expected links" + expectedLinkCount);
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

	private void verifySubCategoryMenuTexts(String menuName) {
		LandingPage landingPage = new LandingPage();
		String expectedLinks = null;
		switch (menuName) {
		case "Men":

			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelRainwearSpringSaleClearanceACTIVITYHikingTravelFishingFieldTrainingJACKETSVESTSParkasCasualInsulatedHardShellsSoftShellsRainwear3in1VestsBOTTOMSPantsJeansShortsBaselayersTOPSShirtsShirtJacketsTShirtsSweatersSweatshirtsHoodiesBaselayersFLEECETechnicalCasualFOOTWEARBootsHikingCasualSandalsSlippersSocksACCESSORIESBeltsGlovesHatsScarvesSunglassesSocks";
			break;
		case "Women":
			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelRainwearSpringSaleClearanceACTIVITYHikingTravelFishingFieldTrainingJACKETSVESTSParkasCasualInsulatedHardShellsSoftShellsRainwear3in1VestsBOTTOMSPantsJeansTightsCaprisShortsSkirtsSkortsBaselayersTOPSShirtsTShirtsTankTopsSweatersSweatshirtsHoodiesSleepDressesBaselayersFLEECETechnicalCasualFOOTWEARBootsHikingCasualSandalsSlippersSocksACCESSORIESBeltsGlovesHatsScarvesSunglassesSocks";
			break;
		case "Outerwear":
			expectedLinks = "MENParkasCasualInsulatedHardShellsSoftShellsRainwear3in1FleeceVestsWOMENParkasCasualInsulatedHardShellsSoftShellsRainwear3in1FleeceVestsBOYSJacketsVestsGIRLSJacketsVests";
			break;
		case "Gear":
			expectedLinks = "GEARBackpacksDuffelsLuggageMessengerLaptopBagsTravelAccessoriesTentsSleepingBagsStovesCookwareHydrationLightingToteBags";
			break;
		case "Home":
			expectedLinks = "HOMEBlanketsThrowsSheetsPillowcasesDuvetCoversShamsComfortersPillowsMattressPadsOutdoorFurnitureHardwoodFlooring";
			break;
		case "Guide":
			expectedLinks = "FEATUREDAdventureTravelRainwearACTIVITYHikingTravelFishingFieldTrainingSnowAlpineABOUTUSOurFounderGuidesAthletesExpeditionsResponsibleSourcingAwardWinnersCareersPARTNERSHIPSBigCityMountaineersAmericanHikingSocietyTheHeroesProjectAmericanForests";
			break;
		case "Clearance":
			expectedLinks = "MENJacketsVestsBottomsTopsFleeceFootwearAccessoriesWOMENJacketsVestsBottomsTopsFleeceFootwearAccessoriesGEARBackpacksDuffelsLuggageMessengerLaptopBagsTravelAccessoriesTentsSleepingBagsStovesCookwareHydrationLightingToteBagsHOMEBlanketsThrowsSheetsPillowcasesDuvetCoversShamsComfortersPillowsMattressPadsOutdoorFurnitureHardwoodFlooring";
			break;
		}

		String actualLinkTexts = landingPage.getSubCategoryLinkTexts(menuName);
		if (expectedLinks.contentEquals(actualLinkTexts.replaceAll("[^a-zA-Z0-9]", ""))) {
			reportVP(PASS, "The Sub category links for " + menuName.toUpperCase()
					+ " menu has been verified. <br>Sub categories:" + actualLinkTexts.replaceAll("\n", ","));

		} else {
			reportVP(FAIL, "The Sub category links for " + menuName + " menu has NOT verified. <br> Expected:"
					+ expectedLinks + "Actual:" + actualLinkTexts.replaceAll("[^a-zA-Z0-9]", ""));
		}

	}
}
