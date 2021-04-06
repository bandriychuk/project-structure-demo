package com.socks.ui.tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.socks.api.payloads.UserPayload;
import com.socks.api.services.CartApiService;
import com.socks.api.services.UserApiService;
import com.socks.ui.CatalogPage;
import com.socks.ui.LoggedUserPage;
import com.socks.ui.MainPage;
import com.socks.ui.ShoppingCartPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.socks.api.conditions.Conditions.statusCode;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class ShoppingCartTest extends BaseUITest {

	private CartApiService cartApiService = new CartApiService();
	private final UserApiService userApiService = new UserApiService();

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

	@Test
	public void testUserCanLoginWithValidCredentials(){
		UserPayload userPayload = new UserPayload()
				.username(randomAlphabetic(6))
				.email("demo@gmail.com")
				.password("test1234");

		userApiService.registerUser(userPayload)
				.shouldHave(statusCode(200));

		MainPage
				.open()
				.loginAs(userPayload.username(), userPayload.password());

		LoggedUserPage loggedUserPage = at(LoggedUserPage.class);
		loggedUserPage.logoutBtn().shouldHave(text("Logout"));
		loggedUserPage.userName().shouldHave(text("Logged in as"));

	}

	@AfterMethod
	public void tearDown(){
		Selenide.clearBrowserCookies();
	}
}
