package com.yong.demo.handler;

import com.yong.demo.model.Mark;
import com.yong.demo.repository.MarkRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @acthor yong.a.liang
 * @date 2017/11/14
 */
@Slf4j
@Component
@AllArgsConstructor
public class MarkHandler {

    private final MarkRepository repository;
    private final ErrorHandler errorHandler;

    public Mono<ServerResponse> findOneMark(final ServerRequest request) {
        return repository.findById(request.pathVariable("id"))
            .transform(this::serverResponse);
    }

    public Mono<ServerResponse> findAllMark(final ServerRequest request) {
        return ok().body(repository.findAll(new Sort(Sort.Direction.ASC,"id")), Mark.class);
    }

    public Mono<ServerResponse> addMark(final ServerRequest request) {
        return request.bodyToMono(Mark.class)
            .flatMap(repository::insert)
            .transform(this::serverResponse)
            .onErrorResume(errorHandler::error);
    }


    public Mono<ServerResponse> updateMark(final ServerRequest request) {
        return request.bodyToMono(Mark.class).flatMap(t ->
            repository.findById(t.getId())
                .switchIfEmpty(
                    Mono.error(new IllegalArgumentException("not found mark in DB, update failure."))
                )
                .flatMap(o ->  repository.save(Mark.updateMark(o,t))))
        .transform(this::serverResponse)
        .onErrorResume(errorHandler::error);
    }

    Mono<ServerResponse> serverResponse(Mono<Mark> markMono) {
        return markMono.flatMap(mark ->
                ok().body(Mono.just(mark), Mark.class));
    }

    public Mono<ServerResponse> helloWorld(final ServerRequest request) {
        return ok().body(Mono.just("helloWorld"), String.class);
    }


}
