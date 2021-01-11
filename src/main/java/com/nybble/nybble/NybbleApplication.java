package com.nybble.nybble;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(value="com.nybble.nybble.security")
public class NybbleApplication {

	public static void main(String[] args) {
		SpringApplication.run(NybbleApplication.class, args);
	}

}
