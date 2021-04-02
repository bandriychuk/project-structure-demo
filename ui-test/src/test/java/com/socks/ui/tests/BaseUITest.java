package com.socks.ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.socks.api.ProjectConfig;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.Test;

public class BaseUITest {

	@Test
	public void setUp(){
		ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
		RestAssured.baseURI = config.baseUrl();
		Configuration.baseUrl  = "chrome";
	}

	protected <T> T at(Class <T> pageClass) {
		return Selenide.page(pageClass);
	}
}
