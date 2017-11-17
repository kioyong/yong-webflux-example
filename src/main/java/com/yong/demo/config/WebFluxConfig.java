package com.yong.demo.config;

import com.yong.demo.handler.MarkHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @acthor yong.a.liang
 * @date 2017/11/17
 */
@Component
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    }


    @Bean
    public RouterFunction<ServerResponse> routerFunction(MarkHandler echoHandler) {
        return route(GET("/hello"), echoHandler::helloWorld)
                .andRoute(GET("/mark/{id}"), echoHandler::findOneMark)
                .andRoute(GET("/mark"), echoHandler::findAllMark)
                .andRoute(POST("/mark"), echoHandler::addMark)
                .andRoute(PUT("/mark"), echoHandler::updateMark)
                ;
    }
}
