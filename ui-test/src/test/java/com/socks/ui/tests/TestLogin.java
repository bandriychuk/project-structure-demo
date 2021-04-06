package com.socks.ui.tests;

import com.socks.api.payloads.UserPayload;
import com.socks.api.services.UserApiService;
import com.socks.ui.LoggedUserPage;
import com.socks.ui.MainPage;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.socks.api.conditions.Conditions.statusCode;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class TestLogin extends BaseUITest {

	private final UserApiService userApiService = new UserApiService();

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
}
