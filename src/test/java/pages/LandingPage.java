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

	public void hoverToAnyMenu(String menuName) {
		String xpath = null;
		switch (menuName) {
		case "Men":
			xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Men')]";
			break;
		case "Women":
			xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Women')]";
			break;
		case "Outerwear":
			xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Outerwear')]";
			break;
		case "Gear":
			xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Gear')]";
			break;
		case "Home":
			xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Home')]";
			break;
		case "GuideToEB":
			xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Guide')]";
			break;
		case "Clearance":
			xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Clearance')]";
			break;
		 
		}
		WebElement menu = ScriptHelper.getDriver().findElement(By.xpath(xpath));
		ScriptHelper.mousehover(menu);
		WebElement focusedFlyOut = ScriptHelper.getDriver()
				.findElement(By.xpath("//div[contains(@class,'FocusedCategoryDropdown')]"));
		ScriptHelper.explicitWaitVisibilityOfElement(focusedFlyOut, 10);
		Assert.assertEquals(focusedFlyOut.isDisplayed(), true, "Flyout menu not displayed on hovering main Menu");
	}

	public HashMap<String, String> getSubCategoryLinks() {
		HashMap<String, String> hMap = new HashMap<String, String>();
		int counter = 1;
		List<WebElement> linkProducts = ScriptHelper.getDriver()
				.findElements(By.xpath("//div[contains(@class,'FocusedCategoryDropdown')]/div/div/div/a"));
		for (WebElement elem : linkProducts) {
			hMap.put(counter + "_" + elem.getText(), elem.getAttribute("href"));
			counter++;
		}
		System.out.println(hMap);
		return hMap;
	}

}
