package com.yong.demo.repository.impl;

import com.yong.demo.model.Mark;
import com.yong.demo.repository.MarkRepositoryCustom;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author LiangYong
 * @date 2017/11/23
 */
@AllArgsConstructor
@Repository
public class MarkRepositoryImpl implements MarkRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Flux<Mark> findAllActivityItems() {
        List<Mark> all = mongoTemplate.findAll(Mark.class);
        return Flux.fromIterable(all);
    }
}
