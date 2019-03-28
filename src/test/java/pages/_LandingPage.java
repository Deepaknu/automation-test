package pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LandingPage extends PageInitializer {

	@FindBy(css = ".logo")
	WebElement linkEBLogo;

	@FindBy(xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Men')]")
	WebElement menuMen;

	@FindBy(xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Women')]")
	WebElement menuWomen;

	@FindBy(xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Outerwear')]")
	WebElement menuOuterwear;

	@FindBy(xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Gear')]")
	WebElement menuGear;

	@FindBy(xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Home')]")
	WebElement menuHome;

	@FindBy(xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Guide')]")
	WebElement menuGuideToEB;

	@FindBy(xpath = "//div[contains(@class,'FocusedCategoryDropdown')]")
	WebElement focusedFlyOut;

	@FindBy(css = ".search_title")
	WebElement linkSearchIcon;

	@FindBy(css = ".sign_in")
	WebElement linkSignInIcon;

	@FindBy(css = ".bag")
	WebElement linkBagIcon;

	@FindBy(xpath = "//div[contains(@class,'FocusedCategoryDropdown')]/div/div/div/a")
	List<WebElement> linkProducts;

	public void hoverToMenMenu() {
		mousehover(menuMen);
		explicitWaitVisibilityOfElement(focusedFlyOut, 10);
		Assert.assertEquals(focusedFlyOut.isDisplayed(), true, "Flyout menu not displayed on hovering main Menu");
	}

	public void verifySubCategoryForMen() {
		boolean vFlag = true;
		HashMap<String, String> hMap = new HashMap<String, String>();
		hMap = getSubCategoryLinks();
		for (Map.Entry m : hMap.entrySet()) {
			System.out.println(m.getKey() + "--" + m.getValue());
			driver.get(m.getValue().toString());
			vFlag = vFlag && checkPageContainsError(m.getKey().toString());
		}
		System.out.println("Size of links:" + hMap.size());
		Assert.assertEquals(42, hMap.size());
//		Assert.assertTrue(vFlag);
	}

	private HashMap<String, String> getSubCategoryLinks() {
		HashMap<String, String> hMap = new HashMap<String, String>();
		int counter = 1;
		for (WebElement elem : linkProducts) {
			hMap.put(counter + "_" + elem.getText(), elem.getAttribute("href"));
			counter++;
		}
		System.out.println(hMap);
		return hMap;
	}

	public void navigateToMenMenu() {
		menuMen.click();
	}

	public void navigateToWomenMenu() {
		menuWomen.click();
	}

	public void navigateToOuterwearMenu() {
		menuOuterwear.click();
	}

	public void navigateToGearMenu() {
		menuGear.click();
	}

	public void navigateToHomeMenu() {
		menuHome.click();
	}

	public void navigateToGuideToEBMenu() {
		menuGuideToEB.click();
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public void verifyHeaderMenuSection() {
		Assert.assertTrue(linkEBLogo.isDisplayed() && menuMen.isDisplayed() && menuWomen.isDisplayed()
				&& menuOuterwear.isDisplayed() && menuGear.isDisplayed() && menuHome.isDisplayed()
				&& menuGuideToEB.isDisplayed());
	}

	public void verifySearchIconPresent() {
		Assert.assertTrue(linkSearchIcon.isDisplayed());
	}

	public void verifySignInIconPresent() {
		Assert.assertTrue(linkSignInIcon.isDisplayed());
	}

	public void verifyBagIconPresent() {
		Assert.assertTrue(linkBagIcon.isDisplayed());
	}

	public void hoverToWomenMenu() {
		mousehover(menuWomen);
		explicitWaitVisibilityOfElement(focusedFlyOut, 10);
		Assert.assertEquals(focusedFlyOut.isDisplayed(), true, "Flyout menu not displayed on hovering main Menu");

	}

	public void hoverToOuterwearMenu() {
		mousehover(menuOuterwear);
		explicitWaitVisibilityOfElement(focusedFlyOut, 10);
		Assert.assertEquals(focusedFlyOut.isDisplayed(), true, "Flyout menu not displayed on hovering main Menu");
	}

	public void hoverToGearMenu() {
		mousehover(menuGear);
		explicitWaitVisibilityOfElement(focusedFlyOut, 10);
		Assert.assertEquals(focusedFlyOut.isDisplayed(), true, "Flyout menu not displayed on hovering main Menu");
	}

	public void hoverToHomeMenu() {
		mousehover(menuHome);
		explicitWaitVisibilityOfElement(focusedFlyOut, 10);
		Assert.assertEquals(focusedFlyOut.isDisplayed(), true, "Flyout menu not displayed on hovering main Menu");
	}

	public void hoverToGuideToEBMenu() {
		mousehover(menuGuideToEB);
		explicitWaitVisibilityOfElement(focusedFlyOut, 10);
		Assert.assertEquals(focusedFlyOut.isDisplayed(), true, "Flyout menu not displayed on hovering main Menu");
	}

	public void verifySubCategoryForWomen() {
		boolean vFlag = true;
		HashMap<String, String> hMap = new HashMap<String, String>();
		hMap = getSubCategoryLinks();
		for (Map.Entry m : hMap.entrySet()) {
			System.out.println(m.getKey() + "--" + m.getValue());
			driver.get(m.getValue().toString());
			vFlag = vFlag && checkPageContainsError(m.getKey().toString());
		}
		System.out.println("Size of links:" + hMap.size());
		Assert.assertEquals(47, hMap.size());
//		Assert.assertTrue(vFlag);
	}

	public void verifySubCategoryForOuterwear() {
		boolean vFlag = true;
		HashMap<String, String> hMap = new HashMap<String, String>();
		hMap = getSubCategoryLinks();
		for (Map.Entry m : hMap.entrySet()) {
			System.out.println(m.getKey() + "--" + m.getValue());
			driver.get(m.getValue().toString());
			vFlag = vFlag && checkPageContainsError(m.getKey().toString());
		}
		System.out.println("Size of links:" + hMap.size());
		Assert.assertEquals(20, hMap.size());
//		Assert.assertTrue(vFlag);
	}

	public void verifySubCategoryForGear() {
		boolean vFlag = true;
		HashMap<String, String> hMap = new HashMap<String, String>();
		hMap = getSubCategoryLinks();
		for (Map.Entry m : hMap.entrySet()) {
			System.out.println(m.getKey() + "--" + m.getValue());
			driver.get(m.getValue().toString());
			vFlag = vFlag && checkPageContainsError(m.getKey().toString());
		}
		System.out.println("Size of links:" + hMap.size());
		Assert.assertEquals(10, hMap.size());
//		Assert.assertTrue(vFlag);
	}

	public void verifySubCategoryForHome() {
		boolean vFlag = true;
		HashMap<String, String> hMap = new HashMap<String, String>();
		hMap = getSubCategoryLinks();
		for (Map.Entry m : hMap.entrySet()) {
			System.out.println(m.getKey() + "--" + m.getValue());
			driver.get(m.getValue().toString());
			vFlag = vFlag && checkPageContainsError(m.getKey().toString());
		}
		System.out.println("Size of links:" + hMap.size());
		Assert.assertEquals(8, hMap.size());
//		Assert.assertTrue(vFlag);
	}

	public void verifySubCategoryForGuideToEB() {
		boolean vFlag = true;
		HashMap<String, String> hMap = new HashMap<String, String>();
		hMap = getSubCategoryLinks();
		for (Map.Entry m : hMap.entrySet()) {
			System.out.println(m.getKey() + "--" + m.getValue());
			driver.get(m.getValue().toString());
			vFlag = vFlag && checkPageContainsError(m.getKey().toString());
		}
		System.out.println("Size of links:" + hMap.size());
		Assert.assertEquals(17, hMap.size());
//		Assert.assertTrue(vFlag);
	}
}
