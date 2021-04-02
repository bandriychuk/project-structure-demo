package com.socks.api.services;


import com.socks.api.assertions.AssertableResponse;

import java.util.HashMap;
import java.util.Map;

public class CartApiService extends ApiService{

	private Map<String, String> getCookie(String sid) {
		Map<String, String> cookie = new HashMap<>();
		cookie.put("", sid);
		return cookie;
	}

	public AssertableResponse addItemToCart(String id, String sid) {
		return new AssertableResponse(
				setUp()
				.cookies(getCookie(sid))
				.when()
				.body("{\"id\":\"" + id + "\"}")
				.post("/cart")
		);
	}

	public AssertableResponse getCartItems(String sid) {
		return new AssertableResponse(
				setUp()
						.cookies(getCookie(sid))
						.when()
						.get("/cart"));
	}

}
