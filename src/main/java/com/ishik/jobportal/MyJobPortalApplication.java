package com.ishik.jobportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the MyJobPortal application.
 * @SpringBootApplication enables auto-configuration, component scanning, and configuration.
 */
@SpringBootApplication
public class MyJobPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyJobPortalApplication.class, args);
    }
}
