package com.yeoro.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "여로 API 명세서",
                description = "<h3>Yeoro API Reference for Developers</h3>",
                version = "v0"
        )
)
@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi allApi() {
        return GroupedOpenApi.builder().group("all").pathsToMatch("/**").build();
    }
    @Bean
    public GroupedOpenApi mapApi() {
        return GroupedOpenApi.builder().group("user").pathsToMatch("/user/**").build();
    }
    @Bean
    public GroupedOpenApi noticeApi() { return GroupedOpenApi.builder().group("notice").pathsToMatch("/notice/**").build(); }
    @Bean
    public GroupedOpenApi placeApi() { return GroupedOpenApi.builder().group("place").pathsToMatch("/places/**").build(); }
}


