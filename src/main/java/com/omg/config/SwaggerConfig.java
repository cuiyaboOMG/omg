package com.omg.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enabled:true}")
    private boolean enabledSwagger;
/*
    @Value("#{'${kingdee.webservice.url}'.concat('/ormrpc/services/WSGLWebServiceFacade')}")
*/
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("omg开放接口API文档-E")
                .description("HTTP对外开放接口")
                .version("1.0.0")
                .license("LICENSE")
                .build();
    }

}
