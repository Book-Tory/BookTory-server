package com.booktory.booktoryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication()
@EnableWebSecurity(debug = true)
public class BookToryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookToryServerApplication.class, args);
    }

}
