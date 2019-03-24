package pages;

import org.openqa.selenium.support.PageFactory;

import base.CoreBase;

public abstract class PageInitializer extends CoreBase {
	public LandingPage landingPage() {
		return PageFactory.initElements(getWebDriver(), LandingPage.class);
		
	}

	public MenCategoryPage menCategoryPage() {
		return PageFactory.initElements(getWebDriver(), MenCategoryPage.class);
	}
	
	 
}
