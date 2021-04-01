package com.socks.ui.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import com.socks.ui.CatalogPage;
import com.socks.ui.ShoppingCartPage;
import org.testng.annotations.Test;

public class ShoppingCartTest extends BaseUITest {

	@Test
	public void userCanAddItemToCartFromCatalog() {
		CatalogPage
				.open()
				.addItemByIndex(0)
				.goToCart();

		at(ShoppingCartPage.class).totalAmount().shouldHave(Condition.exactText("$104.98"));
	}

	@Test
	public void testCanDeletedItemFromCart() {
		ShoppingCartPage.open();
		String cookie = WebDriverRunner.getWebDriver().manage().getCookieNamed("md.sid").getValue();
	}
}
