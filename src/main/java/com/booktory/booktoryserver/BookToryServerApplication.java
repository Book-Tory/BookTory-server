package com.booktory.booktoryserver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication()
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
//@EnableWebSecurity(debug = true)
public class BookToryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookToryServerApplication.class, args);
    }

}
