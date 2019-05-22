package com.eddiebauer.tests;


import static com.eddiebauer.base.BaseFactory.catHash;
import static com.eddiebauer.base.ScriptHelper.mousehover;
import static com.eddiebauer.base.ScriptHelper.navigateToURL;
import static org.testng.Assert.assertTrue;

import java.util.*;

import com.eddiebauer.base.CoreBase;
import com.eddiebauer.base.ScriptHelper;
import com.eddiebauer.base.SuiteListener;
import com.eddiebauer.pages.LandingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;




@Listeners(SuiteListener.class)
public class TestScript01 extends CoreBase {
	static WebDriver driver;

	@Test
	public void verifySubCategoryLinkTexts() {
		verifyMenuLinks("");
	}

	/*@Test
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
	}*/

	@Test(enabled = false)
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

	@Test(enabled = false)
	public void verifyCheckOutFlow() {
		try {
			driver = ScriptHelper.getDriver();
			LandingPage landingPage = new LandingPage();
			landingPage.hoverToAnyMenu("Clearance");
			reportVP(INFO, "Hovering on Menu is completed");
			landingPage.clickOnAnySubCategory("Fleece");
			reportVP(INFO, "Fleece - Sub Category is clicked");
			landingPage.clickFirstProduct();
			String productName = landingPage.getProductTitle();
			reportVP(INFO, "Selected product is: " + productName);
			landingPage.chooseLSizeForFleece();
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


	private void verifyMenuLinks(String menu) {

		try {
			driver = ScriptHelper.getDriver();
            List<WebElement> catLink = driver.findElements(By.xpath("//*[contains(@class,'styles__CategoriesContainer')]/*/li"));
            List<String> ids = new ArrayList<>(catLink.size());
					for(int i =0;i<catLink.size();i++){
						ids.add(catLink.get(i).getAttribute("id"));
					}

           for ( int i = 0;i<catLink.size();i++ ){
			//for ( int i = 0;i<1;i++ ){
				//catLink = driver.findElements(By.xpath("//*[contains(@class,'styles__CategoriesContainer')]/*/li"));
                clickonHoverCat(driver.findElement(By.id(ids.get(i))));
				// open all the urls one by one in the browser.
				navigateToURL();
            }

//			LandingPage landingPage = new LandingPage();
//
//			landingPage.hoverToAnyMenu(menu);
			reportVP(INFO, "Verifying the URLs is completed.");
		//	verifySubCategoryMenu(landingPage, menu);
		} catch (Exception e) {
			e.printStackTrace();
			reportVP(FAIL, "Exception occured. " + e.getStackTrace());
		}
	}



	private void clickonHoverCat(WebElement catLink) {

		mousehover(catLink);
		List<WebElement> itemlabels = driver.findElements(By.xpath("//div[contains(@class,'item-label')]"));
		List<WebElement> itemlabelHref = driver.findElements(By.xpath("//div[contains(@class,'item-label')]/ancestor::a[contains(@href,'/')]"));

		List<String> labels = new ArrayList<>(itemlabelHref.size());
		catHash.clear();

		for(int i=0;i<itemlabelHref.size();i++){
		    labels.add(itemlabels.get(i).getText());
		    catHash.put(itemlabels.get(i).getText(),itemlabelHref.get(i).getAttribute("href"));
		}

		itemlabels.removeAll(labels);
		// display items labels which doesnt have URL.
        for(int i=0;i<itemlabels.size();i++){
            reportVP(INFO, "The Link which doesn't have values are " + itemlabels.get(i).getText());
        }
		System.out.println(catHash);
    }

   // @SuppressWarnings("rawtypes")
	/*private void verifySubCategoryMenu(LandingPage landingPage, String menuName) {
		HashMap<String, String> links = new HashMap<>();
		links = landingPage.getSubCategoryLinks();

		int expectedLinkCount = 0;
		String expectedLinks = null;
		switch (menuName) {
		case "Men":
			expectedLinkCount = 43;
			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelRainwearSpringSaleClearanceACTIVITYHikingTravelFishingFieldTrainingJACKETSVESTSParkasCasualInsulatedHardShellsSoftShellsRainwear3in1VestsBOTTOMSPantsJeansShortsBaselayersTOPSShirtsShirtJacketsTShirtsSweatersSweatshirtsHoodiesBaselayersFLEECETechnicalCasualFOOTWEARBootsHikingCasualSandalsSlippersSocksACCESSORIESBeltsGlovesHatsScarvesSunglassesSocks";
			break;
		case "Women":
			expectedLinkCount = 48;
			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelRainwearSpringSaleClearanceACTIVITYHikingTravelFishingFieldTrainingJACKETSVESTSParkasCasualInsulatedHardShellsSoftShellsRainwear3in1VestsBOTTOMSPantsJeansTightsCaprisShortsSkirtsSkortsBaselayersTOPSShirtsTShirtsTankTopsSweatersSweatshirtsHoodiesSleepDressesBaselayersFLEECETechnicalCasualFOOTWEARBootsHikingCasualSandalsSlippersSocksACCESSORIESBeltsGlovesHatsScarvesSunglassesSocks";
			break;
		case "Outerwear":
			expectedLinkCount = 30;
			expectedLinks = "MENParkasCasualInsulatedHardShellsSoftShellsRainwear3in1FleeceVestsWOMENParkasCasualInsulatedHardShellsSoftShellsRainwear3in1FleeceVestsBOYSJacketsVestsGIRLSJacketsVests";
			break;
		case "Gear":
			expectedLinkCount = 20;
			expectedLinks = "GEARBackpacksDuffelsLuggageMessengerLaptopBagsTravelAccessoriesTentsSleepingBagsStovesCookwareHydrationLightingToteBags";
			break;
		case "Home":
			expectedLinkCount = 13;
			expectedLinks = "HOMEBlanketsThrowsSheetsPillowcasesDuvetCoversShamsComfortersPillowsMattressPadsOutdoorFurnitureHardwoodFlooring";
			break;
		case "Guide":
			expectedLinkCount = 10;
			expectedLinks = "FEATUREDAdventureTravelRainwearACTIVITYHikingTravelFishingFieldTrainingSnowAlpineABOUTUSOurFounderGuidesAthletesExpeditionsResponsibleSourcingAwardWinnersCareersPARTNERSHIPSBigCityMountaineersAmericanHikingSocietyTheHeroesProjectAmericanForests";
			break;
		case "Clearance":
			expectedLinkCount = 14;
			expectedLinks = "MENJacketsVestsBottomsTopsFleeceFootwearAccessoriesWOMENJacketsVestsBottomsTopsFleeceFootwearAccessoriesGEARBackpacksDuffelsLuggageMessengerLaptopBagsTravelAccessoriesTentsSleepingBagsStovesCookwareHydrationLightingToteBagsHOMEBlanketsThrowsSheetsPillowcasesDuvetCoversShamsComfortersPillowsMattressPadsOutdoorFurnitureHardwoodFlooring";

			break;
		}

		for (Map.Entry m : links.entrySet()) {
			driver.get(m.getValue().toString());
			checkAnyPageContainsError(m.getKey().toString().substring(m.getKey().toString().indexOf("_") + 1));
		}
		if (expectedLinkCount == links.size()) {
			reportVP(PASS, "Total number of Sub category links for " + menuName
					+ " menu has been verified. Total links:" + links.size());

		} else {
			reportVP(FAIL, "Total number of Sub category links for " + menuName + " menu has NOT verified. Total links:"
					+ links.size() + "Expected links" + expectedLinkCount);
		}
		ScriptHelper.getDriver();
	}
*/


	/*private void verifySubCategoryMenuTexts(String menuName) {
		LandingPage landingPage = new LandingPage();
		String expectedLinks = null;
		switch (menuName) {
		case "Men":

			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelRainwearSpringSaleClearanceACTIVITYHikingTravelFishingFieldTrainingMountainSportsJACKETSVESTSParkasCasualInsulatedHardShellsSoftShellsRainwear3in1VestsBOTTOMSPantsJeansShortsBaselayersTOPSShirtsShirtJacketsTShirtsSweatersSweatshirtsHoodiesBaselayersFLEECETechnicalCasualFOOTWEARBootsHikingCasualSandalsSlippersSocksACCESSORIESBeltsGlovesHatsScarvesSunglassesSocks";
			break;
		case "Women":
			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelRainwearSpringSaleClearanceACTIVITYHikingTravelFishingFieldTrainingMountainSportsJACKETSVESTSParkasCasualInsulatedHardShellsSoftShellsRainwear3in1VestsBOTTOMSPantsJeansTightsCaprisShortsSkirtsSkortsBaselayersTOPSShirtsTShirtsTankTopsSweatersSweatshirtsHoodiesSleepDressesBaselayersFLEECETechnicalCasualFOOTWEARBootsHikingCasualSandalsSlippersSocksACCESSORIESBeltsGlovesHatsScarvesSunglassesSocks";
			break;
		case "Outerwear":
			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelRainwearSpringSaleClearanceACTIVITYHikingTravelMountainSportsTrainingMENParkasCasualInsulatedHardShellsSoftShellsRainwear3in1FleeceVestsWOMENParkasCasualInsulatedHardShellsSoftShellsRainwear3in1FleeceVestsBOYSJacketsVestsGIRLSJacketsVests";
			break;
		case "Gear":
			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelSpringSaleClearanceACTIVITYHikingTravelMountainSportsTrainingFishingFieldGEARBackpacksDuffelsLuggageMessengerLaptopBagsTravelAccessoriesTentsSleepingBagsStovesCookwareHydrationLightingToteBags";
			break;
		case "Home":
			expectedLinks = "FEATUREDViewAllNewArrivalsAdventureTravelSpringSaleClearanceHOMEBlanketsThrowsSheetsPillowcasesDuvetCoversShamsComfortersPillowsMattressPadsOutdoorFurnitureHardwoodFlooring";
			break;
		case "Guide":
			expectedLinks = "ABOUTUSOurFounderGuidesAthletesExpeditionsResponsibleSourcingAwardWinnersCareersPARTNERSHIPSBigCityMountaineersAmericanHikingSocietyTheHeroesProjectAmericanForests";
			break;
		case "Clearance":
			expectedLinks = "MENJacketsVestsBottomsTopsFleeceFootwearAccessoriesWOMENAmericanHikingSocietyJacketsVestsBottomsTopsFleeceFootwearAccessoriesKIDSBoysGirls";
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

	}*/
}
