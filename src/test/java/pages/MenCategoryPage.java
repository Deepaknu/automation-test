package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MenCategoryPage extends PageInitializer {

	private WebDriverWait wait;

	 
	 
//		wait.until(ExpectedConditions.visibilityOf(titleMen));
//		System.out.println(driver.getCurrentUrl() + driver.getTitle());
		// Assert.assertEquals(getTitle(), "Men | Eddie Bauer");
		// checkStatusCodeForAnyPage(driver.getCurrentUrl());
	 

	@FindBy(xpath = "//div[contains(@class,'styles__CategoryRootItem') and contains(.,'Men')]")
	WebElement menuMen;

	@FindBy(xpath = "//span[@class='title' and contains(.,'Men')]")
	WebElement titleMen;

	@FindBy(tagName = "a")
	List<WebElement> lists;

	@FindBy(xpath = "//div[contains(@class,'StyledProductInfo')]//../a")
	List<WebElement> linkProducts;

	public void printLinks() {
		for (WebElement e : lists) {
			e.getText();

		}
	}

	 
	public void verifySubCategories() {
		verifyProductLists();
	}

	public void verifyProductLists() {
		List<String> strProductLists = new ArrayList<>();
		for (WebElement elem : linkProducts) {
			strProductLists.add(elem.getAttribute("href"));
			
		}
		System.out.println(strProductLists);
//		verifyAnyPageForSuccessfulLoading(strProductLists);

	}

}
