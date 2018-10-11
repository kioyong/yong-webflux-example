package com.yong.flux;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.yong.flux.ResultUtil.response;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@AllArgsConstructor
class PersonHandler {

    private PersonRepository repository;

    Mono<ServerResponse> findByPersonId(ServerRequest request) {
        String id = request.pathVariable("personId");
        return repository.findByPersonId(id)
            .map(t -> {
                t.setStatus(t.getStatus().concat("test"));
                return t;
            })
            .collectList()
            .flatMap(t -> t.size() == 0 ? Mono.empty() : Mono.just(t))
            .flatMap(ResultUtil::response)
            .switchIfEmpty(response(400, "data not found"))
            .onErrorResume(ResultUtil::error);
    }

    Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
            .flatMap(ResultUtil::response)
            .switchIfEmpty(response(400, "data not found"))
            .onErrorResume(ResultUtil::error);

    }

    Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<Person> people = repository.findAll();
        return ok().body(people, Person.class);
    }

    Mono<ServerResponse> createPerson(ServerRequest request) {
        Mono<Person> person = request.bodyToMono(Person.class);
        return person.log()
            .flatMap(t -> {
                if (t.getPersonId().length() > 8) {
                    return Mono.error(new IllegalArgumentException("personId length not should be > 8 ."));
                }
                return repository.save(t).flatMap(ResultUtil::response);
            })
            .onErrorResume(ResultUtil::error);
    }


}
