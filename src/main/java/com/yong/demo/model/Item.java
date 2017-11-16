package com.yong.demo.model;

import lombok.Data;

/**
 * @acthor yong.a.liang
 * @date 2017/11/14
 */

@Data
public class Item {
    private String id;
    private String text;
    private boolean isActivity;
    private boolean isChecked;


}
