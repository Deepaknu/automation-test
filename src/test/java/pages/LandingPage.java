package pages;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.ScriptHelper;

public class LandingPage {

	/**
	 * Method to over on any menu in the header
	 * 
	 * @param menuName - options [Men,Women,Outerwear,Gear,Home,Guide,Clearance]
	 */
	public void hoverToAnyMenu(String menuName) {

		WebElement menu = ScriptHelper.getDriver().findElement(
				By.xpath("//a[contains(@class,'styles__CategoryRootItem') and contains(.,'" + menuName + "')]"));

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

	public void clickOnAnySubCategory(String subCat) {
		WebElement subCatElem = ScriptHelper.getDriver().findElement(By.xpath(
				"//div[contains(@class,'FocusedCategoryDropdown')]/div/div/div/a/div[contains(.,'" + subCat + "')]"));
		subCatElem.click();
		WebElement breadCrumb = ScriptHelper.getDriver().findElement(By.xpath("//ul[contains(@class,'Breadcrumb')]"));
		ScriptHelper.explicitWaitVisibilityOfElement(breadCrumb, 30);
	}

	public void clickFirstProduct() {
		WebElement product = ScriptHelper.getDriver().findElement(By.xpath("//div[contains(@class,'product_tile')]"));
		product.click();
	}

	public String getProductTitle() {
		WebElement prodTitle = ScriptHelper.getDriver()
				.findElement(By.xpath("//div[contains(@class,'StyledHeader')]/div[@class='title']"));
		ScriptHelper.explicitWaitVisibilityOfElement(prodTitle, 30);
		prodTitle.click();
		return prodTitle.getText();
	}

	public void SearchAnyValue(String searchText) {
		WebElement searchIcon = ScriptHelper.getDriver().findElement(By.className("icon_container"));
		searchIcon.click();
		WebElement searchInputBox = ScriptHelper.getDriver().findElement(By.cssSelector("#search"));
		searchInputBox.sendKeys(searchText);
		searchInputBox.sendKeys(Keys.ENTER);
	}

	public void chooseAnySizeForTheShoe(String size) {
		WebElement sizeIcon = ScriptHelper.getDriver()
				.findElement(By.xpath("//span[@class='size_option' and contains(.,'" + size + "')]"));
		sizeIcon.click();
	}

	public void clickOnAddToBag() {
		WebElement addToCartBtn = ScriptHelper.getDriver()
				.findElement(By.xpath("//button[contains(@class,'add_to_cart')]"));
		addToCartBtn.click();
	}

	public boolean verifyAddToBagOverlay(String productName) {
		WebElement overlay = ScriptHelper.getDriver().findElement(By.cssSelector("#add_to_cart_success"));
		ScriptHelper.explicitWaitVisibilityOfElement(overlay, 30);
		return overlay.getText().contains(productName);
	}

	public void continueToCheckOut() {
		WebElement checkOutBtn = ScriptHelper.getDriver().findElement(By.xpath("//div[@id='sbCartLinksHolder']/div/a"));
		checkOutBtn.click();
	}

	public boolean verifyCheckOutPageContainsTheProduct(String productName) {
		WebElement cartPage = ScriptHelper.getDriver().findElement(By.cssSelector(".sb-item-container"));
		ScriptHelper.explicitWaitVisibilityOfElement(cartPage, 30);
		return cartPage.getText().contains(productName);
	}

	public boolean proceedToCheckOut() {
		WebElement checkOutBtn = ScriptHelper.getDriver().findElement(By.id("osCheckoutBtn"));
		checkOutBtn.click();
		WebElement loginPageElement = ScriptHelper.getDriver().findElement(By.id("checkoutLoginFormsHolder"));
		ScriptHelper.explicitWaitVisibilityOfElement(loginPageElement, 30);
		return loginPageElement.isDisplayed();
	}

	public String getSubCategoryLinkTexts(String menuName) {
		hoverToAnyMenu(menuName);

		WebElement linkTexts = ScriptHelper.getDriver()
				.findElement(By.xpath("//div[contains(@class,'FocusedCategoryDropdown')]/div"));

		return linkTexts.getText();
	}
}
