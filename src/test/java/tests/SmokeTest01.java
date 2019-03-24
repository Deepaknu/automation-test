package tests;

import org.testng.annotations.Test;

import pages.PageInitializer;

public class SmokeTest01 extends PageInitializer {

	@Test(enabled = true)
	public void verifyHeaderAndFooterSections() throws Exception {

		landingPage().verifyHeaderMenuSection();
		landingPage().verifySearchIconPresent();
		landingPage().verifySignInIconPresent();
		landingPage().verifyBagIconPresent();
//		landingPage().verifyFooter();
	}

	@Test
	public void verifyMenSubCategoryLinks() {
		landingPage().hoverToMenMenu();
		landingPage().verifySubCategoryForMen();
	}

	@Test
	public void verifyWomenSubCategoryLinks() {
		landingPage().hoverToWomenMenu();
		landingPage().verifySubCategoryForWomen();
	}

	@Test
	public void verifyOuterwearSubCategoryLinks() {
		landingPage().hoverToOuterwearMenu();
		landingPage().verifySubCategoryForOuterwear();
	}

	@Test
	public void verifyGearSubCategoryLinks() {
		landingPage().hoverToGearMenu();
		landingPage().verifySubCategoryForGear();
	}

	@Test
	public void verifyHomeSubCategoryLinks() {
		landingPage().hoverToHomeMenu();
		landingPage().verifySubCategoryForHome();
	}

	@Test
	public void verifyGuideToEBSubCategoryLinks() {
		landingPage().hoverToGuideToEBMenu();
		landingPage().verifySubCategoryForGuideToEB();
	}

	@Test(enabled = false, dependsOnMethods = "verifyHeaderElements")
	public void verifyMenListingPage() {
		landingPage().navigateToMenMenu();
		menCategoryPage().verifySubCategories();
//		List<String> lists = landingPage.getSubCategoryLinks();
	}
}
