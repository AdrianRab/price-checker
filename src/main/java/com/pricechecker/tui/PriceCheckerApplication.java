package com.pricechecker.tui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.pricechecker.tui.pricechecker"})
@EnableScheduling
public class PriceCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceCheckerApplication.class, args);
	}
}
