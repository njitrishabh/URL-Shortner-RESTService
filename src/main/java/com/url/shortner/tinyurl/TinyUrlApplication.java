package com.url.shortner.tinyurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TinyUrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyUrlApplication.class, args);
	}

}
