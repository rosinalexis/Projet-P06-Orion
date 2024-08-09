package com.openclassrooms.mddapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Alexis",
                        email = "default@oc-alexis.com"
                ),
                description = "OpenApi documentation for MDD REST API ",
                title = "OpenApi Documentation - MDD REST API",
                license = @License(
                        name = "License GNU",
                        url = "https://www.gnu.org/licenses/"
                ),
                termsOfService = "Default usage",
                version = "0.1"
        ),
        servers = {
                @Server(
                        description = "Local DEV",
                        url = "http://localhost:3001/api"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "Connect to get a JWT token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
