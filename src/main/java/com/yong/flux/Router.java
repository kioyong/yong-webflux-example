package com.yong.flux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> route(PersonHandler personHandler) {
        return RouterFunctions
            .route(GET("/person/{id}")
                .and(accept(APPLICATION_JSON)), personHandler::findById)
            .andRoute(GET("/person/findByPersonId/{personId}")
                .and(accept(APPLICATION_JSON)), personHandler::findByPersonId)
            .andRoute(GET("/person")
                .and(accept(APPLICATION_JSON)), personHandler::findAll)
            .andRoute(POST("/person")
                .and(accept(APPLICATION_JSON)), personHandler::createPerson)
            ;
    }

}
