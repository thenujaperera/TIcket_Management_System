package com.thenuja.oopcw;
import com.thenuja.oopcw.config.LoggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */

@SpringBootApplication(scanBasePackages = "com.thenuja.oopcw")
public class OopcwApplication {
	public static void main(String[] args) {
		LoggerConfig.configureLogger();
		SpringApplication.run(OopcwApplication.class, args);
	}


}
