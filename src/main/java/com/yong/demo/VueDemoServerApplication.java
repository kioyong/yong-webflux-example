package com.yong.demo;

import com.yong.demo.handler.MarkHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author yong.a.liang
 */
@SpringBootApplication
@EnableWebFlux
@Slf4j
@RestControllerAdvice
public class VueDemoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueDemoServerApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(MarkHandler echoHandler) {
        return route(GET("/helloWorld"), echoHandler::helloWorld)
                .andRoute(GET("/todo/{id}"), echoHandler::findOneTodo)
                .andRoute(GET("/todo"), echoHandler::findAllTodo)
                .andRoute(POST("/todo"), echoHandler::addTodo)
                .andRoute(PUT("/todo"), echoHandler::updateTodo)
                ;
    }
}

