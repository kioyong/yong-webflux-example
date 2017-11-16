package com.yong.demo.handler;

import com.yong.demo.model.Mark;
import com.yong.demo.repository.MarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @acthor yong.a.liang
 * @date 2017/11/14
 */
@AllArgsConstructor
@Component
public class MarkHandler {

    private final MarkRepository repository;

    public Mono<ServerResponse> findOneTodo(ServerRequest request) {
        return repository.findById(request.pathVariable("id"))
            .transform(this::serverResponse);
    }

    public Flux<ServerResponse> findAllTodo(ServerRequest request) {
        return repository.findAll().flatMap( mark ->
            ServerResponse.ok().body(Flux.just(mark), Mark.class)
        );
    }

    public Mono<ServerResponse> addTodo(ServerRequest request) {
        return request.bodyToMono(Mark.class)
            .flatMap(repository::save)
            .transform(this::serverResponse);
    }


    public Mono<ServerResponse> updateTodo(ServerRequest request) {
        return request.bodyToMono(Mark.class)
            .flatMap(t ->
                repository.findById(t.getId())
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("not found mark in DB, update failure.")))
                    .flatMap(o -> {
                        o.setActivity(t.isActivity());
                        o.setItem(t.getItem());
                        o.setLocked(t.isLocked());
                        o.setTitle(t.getTitle());
                        return repository.save(o);
                    })
            ).transform(this::serverResponse)
        ;
    }

     public Mono<ServerResponse> helloWorld(ServerRequest request) {
        return ServerResponse.ok().body(Mono.just("helloWorld"), String.class);
    }

    Mono<ServerResponse> serverResponse(Mono<Mark> markMono) {
        return markMono.flatMap(mark ->
                ServerResponse.ok().body(Mono.just(mark), Mark.class));
    }

}
