package com.jodelin.javaguides;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "api documentation for my api",
        version = "1.0",
        license = @License(name = "apache 2.0",url = "https://google.com"),
        description = "this a very good api documentstion",
        contact = @Contact(
                name = "Jodelin Desrameaux",
                email = "djodelin93@gmail.com",
                url = "https://jodelindesrameaux.com"
        )
),externalDocs = @ExternalDocumentation(url = "https://springboot.org"))
public class JavaguidesApplication implements CommandLineRunner {
    private  final  PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(JavaguidesApplication.class, args);
    }

@Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
}

    @Override
    public void run(String... args) throws Exception {
        System.out.println(passwordEncoder.encode("admin"));
        System.out.println(passwordEncoder.encode("user"));
    }
}
