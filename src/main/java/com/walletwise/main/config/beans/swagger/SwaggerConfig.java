package com.walletwise.main.config.beans.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.environment}")
    private String appEnvironment;

    @Value("${app.server.url}")
    private String appServerUrl;

    @Bean
    public OpenAPI openAPI() {
        String description = "The Personal Budgeting App" + this.appName + "is your comprehensive financial companion,\n" +
                "                                        designed to empower users in managing their finances effectively.\n" +
                "                                        With intuitive features, it allows you to track expenses,\n" +
                "                                        set realistic budgets, and achieve financial goals. Gain insights through visual reports,\n" +
                "                                        receive personalized spending suggestions, and take control of your financial well-being.\n" +
                "                                        Elevate your financial literacy with educational resources integrated within the app.\n" +
                "                                        Start your journey to financial wellness with the Personal Budgeting App today.";

        return new OpenAPI()
                .info(new Info()
                        .title(this.appName.toUpperCase() + " - " + this.appEnvironment.toLowerCase())
                        .version(this.appVersion)
                        .description(description))
                .servers(Collections.singletonList(
                        new Server().url(appServerUrl).description("Default url")
                ));
    }
}
