package com.socks.api;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:config.properties"})
public interface ProjectConfig extends Config {

	@Key("baseUrl")
	String baseUrl();

	@DefaultValue("ru")
	String locale();

	boolean logging();
}
