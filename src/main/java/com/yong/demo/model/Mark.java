package com.yong.demo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @acthor yong.a.liang
 * @date 2017/11/14
 */
@Data
@Document
public class Mark {
    private String id;
    private String title;
    private boolean isActivity;
    private boolean isLocked;
    private List<Item> item;


}
