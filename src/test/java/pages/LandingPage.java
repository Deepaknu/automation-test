package pages;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.ScriptHelper;

public class LandingPage {

//	@FindBy(css = ".logo")
//	WebElement linkEBLogo;
//	@FindBy(xpath = "//div[contains(@class,'FocusedCategoryDropdown')]")
//	WebElement focusedFlyOut;
//
//	@FindBy(css = ".search_title")
//	WebElement linkSearchIcon;
//
//	@FindBy(css = ".sign_in")
//	WebElement linkSignInIcon;
//
//	@FindBy(css = ".bag")
//	WebElement linkBagIcon;
	/**
	 * Method to over on any menu in the header
	 * 
	 * @param menuName - options [Men,Women,Outerwear,Gear,Home,Guide,Clearance]
	 */
	public void hoverToAnyMenu(String menuName) {

		WebElement menu = ScriptHelper.getDriver().findElement(
				By.xpath("//div[contains(@class,'styles__CategoryRootItem') and contains(.,'" + menuName + "')]"));
		ScriptHelper.mousehover(menu);

		WebElement focusedFlyOut = ScriptHelper.getDriver()
				.findElement(By.xpath("//div[contains(@class,'FocusedCategoryDropdown')]"));
		ScriptHelper.explicitWaitVisibilityOfElement(focusedFlyOut, 30);

		Assert.assertEquals(focusedFlyOut.isDisplayed(), true, "Flyout menu not displayed on hovering main Menu");
	}

	public HashMap<String, String> getSubCategoryLinks() {
		HashMap<String, String> hMap = new HashMap<String, String>();
		int counter = 1;
		List<WebElement> linkProducts;

		linkProducts = ScriptHelper.getDriver()
				.findElements(By.xpath("//div[contains(@class,'FocusedCategoryDropdown')]/div/div/div/a"));

		for (WebElement elem : linkProducts) {
			hMap.put(counter + "_" + elem.getText().toUpperCase(), elem.getAttribute("href"));
			counter++;
		}
		return hMap;
	}

}
