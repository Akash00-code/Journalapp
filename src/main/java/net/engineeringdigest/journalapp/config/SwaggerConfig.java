package net.engineeringdigest.journalapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title("JournalAPP APIs")
                        .description("This is the Journal Application API.")
        )
                .servers(List.of(new Server().url("http://localhost:9090").description("server 1")
                        ,new  Server().url("http://localhost:8080").description("server 2")))
                .tags(List.of(
                        new Tag().name("Public APIs").description("public controller"),
                        new Tag().name("Journal APIs").description("Journal controller"),
                        new Tag().name("User APIs").description("user controller"),
                        new Tag().name("Admin APIs").description("admin controller"),
                        new Tag().name("Email APIs").description("email controller")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth",new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));
    }
}
