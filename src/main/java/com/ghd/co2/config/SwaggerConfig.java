package com.ghd.co2.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * Instantiates a new swagger config.
	 */
	public SwaggerConfig() {
		System.out.println("-> SwaggerConfig");
	}

	/**
	 * Custom implementation.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket api() {
		List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
		responseMessages.add(new ResponseMessage(200, "Success", null, null, null));
		responseMessages.add(new ResponseMessage(400, "Bad Request", null, null, null));
		responseMessages.add(new ResponseMessage(401,
				"Unauthorized (Not logged in or invalid session or resource cannot be accessed)", null, null, null));
		responseMessages.add(new ResponseMessage(404, "Requested resource is not found", null, null, null));
		responseMessages.add(new ResponseMessage(500, "Internal Server Error", null, null, null));

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(Predicates.not(PathSelectors.regex("/error"))).build().apiInfo(metadata())
				.useDefaultResponseMessages(false).securitySchemes(Collections.singletonList(apiKey()))
				.securityContexts(Collections.singletonList(securityContext()))
				.genericModelSubstitutes(Optional.class);
//				.globalResponseMessage(RequestMethod.GET, responseMessages)
//				.globalResponseMessage(RequestMethod.POST, responseMessages)
//				.globalResponseMessage(RequestMethod.PUT, responseMessages)
//				.globalResponseMessage(RequestMethod.PATCH, responseMessages)
//				.globalResponseMessage(RequestMethod.DELETE, responseMessages);

	}

	/**
	 * Api info.
	 *
	 * @return the api info
	 */
	private ApiInfo metadata() {
		return new ApiInfoBuilder()//
				.title("Reference Template Project")
				.description("The project is a reference template for any starter projects")
				.version("1.0.0")
				.contact(new Contact("BridgeI2I", null, null))
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("Authorization", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
	}
}
