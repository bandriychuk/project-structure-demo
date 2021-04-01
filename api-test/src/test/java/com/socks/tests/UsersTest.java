package com.socks.tests;

import com.github.javafaker.Faker;
import com.socks.api.ProjectConfig;
import com.socks.api.payloads.UserPayload;
import com.socks.api.responses.UserRegistrationResponse;
import com.socks.api.services.UserApiService;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Locale;

import static com.socks.api.conditions.Conditions.bodyField;
import static com.socks.api.conditions.Conditions.statusCode;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class UsersTest {

	private final UserApiService userApiService = new UserApiService();
	private Faker faker;

	@BeforeClass
	public void setUp() {
		ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
		faker = new Faker(new Locale(config.locale()));
		RestAssured.baseURI = config.baseUrl();
	}

	@Test
	public void testsCanRegisterNewUser() {
		//given
		UserPayload user = new UserPayload()
				.username(faker.name().username())
				.firstName(faker.name().firstName())
				.lastName(faker.name().lastName())
				.phone(faker.phoneNumber().cellPhone())
				.email("test1@gmail.com")
				.password("test1234");
		//expect
		userApiService.registerUser(user)
				.shouldHave(statusCode(200))
				.shouldHave(bodyField("id", not(isEmptyOrNullString())));
	}

	@Test
	public void testRegisterNewUser() {
		UserPayload user = new UserPayload()
				.username(faker.name().username())
				.firstName(faker.name().firstName())
				.lastName(faker.name().lastName())
				.email("test1@gmail.com")
				.password("test1234");

		userApiService.registerUser(user)
				.shouldHave(statusCode(200));
	}

	@Test(enabled=false)
	public void testsCanRegisterNewUserPojo() {
		//given
		UserPayload user = new UserPayload()
				.username(RandomStringUtils.randomAlphabetic(6))
				.email("test2@gmail.com")
				.password("test1234");
		//expect
		UserRegistrationResponse response = userApiService.registerUser(user)
				.shouldHave(statusCode(200))
				.asPojo(UserRegistrationResponse.class);

		response.id();
	}

//	@Test
//	public void testCanNotRegisterSameUserTwice() {
//		UserPayload user = new UserPayload()
//				.firstName(faker.name().firstName())
//				.lastName(faker.name().lastName())
//				.email("test1@gmail.com")
//				.password("test1234");
//
//		userApiService.registerUser(user)
//				.shouldHave(statusCode(200));
//
//		userApiService.registerUser(user)
//				.shouldHave(statusCode(500));
//
//	}
}
