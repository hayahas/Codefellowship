package com.example.LTUC.Codefellowship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
public class CodefellowshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodefellowshipApplication.class, args);
	}

}
