package ru.ticketapp.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Ticket rent service",
                description = "Spring app for searching and buying tickets ", version = "1.0.0",
                contact = @Contact(
                        name = "Semekhin Vasiliy"
                )
        )
)
public class OpenApiConf {

}