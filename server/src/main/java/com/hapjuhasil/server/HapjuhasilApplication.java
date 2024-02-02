package com.hapjuhasil.server;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

@SpringBootApplication
public class HapjuhasilApplication {

	public static void main(String[] args) {
		SpringApplication.run(HapjuhasilApplication.class, args);
	}

}
