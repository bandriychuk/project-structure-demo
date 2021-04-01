package com.socks.api.services;

import java.util.HashMap;
import java.util.Map;

public class CartApiService {

	private Map<String, String> getCookie(String sid) {
		Map<String, String> cookie = new HashMap<>();
		cookie.put("mk.dir", sid);
		return cookie;
	}


}
