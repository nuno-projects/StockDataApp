package com.tibss.StockPriceData;

import
		org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StockPriceDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockPriceDataApplication.class, args);
	}

}

