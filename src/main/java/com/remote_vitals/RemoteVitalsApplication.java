package com.remote_vitals;

import com.remote_vitals.entities.StaticDataClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class
 * This class is a placeholder and doesn't use Spring Boot annotations 
 * to avoid module issues
 */

@SpringBootApplication
public class RemoteVitalsApplication {

	/**
	 * Main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		// For now, just launch JavaFX directly
		StaticDataClass.context = SpringApplication.run(RemoteVitalsApplication.class, args);
		JavaFXApplication.main(args);

	}
}
