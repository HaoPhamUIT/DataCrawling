package vn.ecoe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author nhanvt
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("vn.ecoe.api"))
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("STOCK REST API for stock service", "See the information.", "API", "Terms of service",
                new Contact("Research group", "https://group.com", "info@mail.vn"), "License of Research Group",
                "API license URL", Collections.emptyList());
    }

    /*
     * @Bean public Docket productApi() { return new
     * Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any
     * ()) .paths(PathSelectors.any()).build(); }
     */
}