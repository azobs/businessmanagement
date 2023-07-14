package com.c2psi.businessmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfoBuilder()
                        .description("Bussiness management API documentation")
                        .title("Bussiness Management REST API")
                        .build()
                )
                .groupName("REST API V1")
                /****
                 * Ajouts pour la security
                 */
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.c2psi.businessmanagement"))
                //.paths(PathSelectors.ant(APP_ROOT+"/**")) valeur avant l'ajout de la securite
                .paths(PathSelectors.any())
                .build();
    }

    private ApiKey apiKey(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEveryThing: " +
                "Access qui donne access a toutes les routes de mon API");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

}
