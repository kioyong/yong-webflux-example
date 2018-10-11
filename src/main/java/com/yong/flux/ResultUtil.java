package com.yong.flux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
class ResultUtil<T> {

    private static Result result(Object data) {
        return new Result(200, "successful", data);
    }

    static Mono<ServerResponse> response(Object data) {
        return ok().body(fromObject(result(data)));
    }

//    static Mono<ServerResponse> monoResponse(Mono<T> data) {
//        return ok().body(data,T.class);
//    }

    static Mono<ServerResponse> response(Integer code, String msg) {
        return ok().body(fromObject(new Result(code, msg, null)));
    }

    static Mono<ServerResponse> error(Throwable error) {
        log.error("error handling : {}", error.getMessage());
        return ok().body(fromObject(new Result(400, error.getMessage(), null)));
    }



}
