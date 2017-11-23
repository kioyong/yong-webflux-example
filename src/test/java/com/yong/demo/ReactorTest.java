package com.yong.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LiangYong
 * @date 2017/11/22
 */
@Slf4j
public class ReactorTest {

    @Test
    public void test1(){
        Flux<String> map = Flux.just("foo", "bar", "boom")
            .log()
            .map(t -> t.concat("1"));
        log.info("already init Flux, try to subscribe");
        map.subscribe(System.out::println);
    }

    @Test
    public void test2(){
        List<String> foo1 = Flux.just("foo").map(foo -> {
            log.info("test Flux.map");
            return "bar";
        }).collect(Collectors.toList()).block();
//        log.info("foo1 = {}",foo1);
    }

    @Test
    public void test3(){
        Flux.just("foo").map(foo -> {
            log.info("test Flux.map");
            return "bar";
        })
//            .subscribe(bar -> log.info("list = :"))
        .collectList().subscribe(list -> log.info("list ={}",list));
    }

    @Test
    public void test4(){
        String s = Flux.just("foo", "bar").blockFirst();
        log.info("s = {}",s)
        ;
    }



}