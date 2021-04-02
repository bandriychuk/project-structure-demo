package com.socks.ui.tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.socks.api.services.CartApiService;
import com.socks.ui.CatalogPage;
import com.socks.ui.ShoppingCartPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


import static com.codeborne.selenide.Condition.exactText;

public class ShoppingCartTest extends BaseUITest {

	private CartApiService cartApiService = new CartApiService();

	@Test
	public void userCanAddItemToCartFromCatalog() {
		CatalogPage
				.open()
				.addItemByIndex(0)
				.goToCart();
		at(ShoppingCartPage.class).totalAmount().shouldHave(exactText("$104.98"));
	}

	@Test
	public void testCanDeletedItemFromCart() {
		ShoppingCartPage.open();
		String cookies = WebDriverRunner.getWebDriver().manage().getCookieNamed("md.sid").getValue();

		cartApiService.addItemToCart("3395a43e-2d88-40de-b95f-e00e1502085b", cookies);
		cartApiService.getCartItems(cookies);

		ShoppingCartPage
				.open()
				.deleteItem()
				.totalAmount().shouldHave(exactText("$0.00"));
	}

	@AfterMethod
	public void tearDown(){
		Selenide.clearBrowserCookies();
	}
}
