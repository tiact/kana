package com.tiact.tiapay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Tia_ct
 */
@SpringBootApplication
public class TiaPayApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TiaPayApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(TiaPayApplication.class, args);
    }


}
