package com.yong.demo.repository;

import com.yong.demo.model.Mark;
import reactor.core.publisher.Flux;

/**
 * @author LiangYong
 * @date 2017/11/23
 */
public interface MarkRepositoryCustom {

    Flux<Mark> findAllActivityItems();
}
