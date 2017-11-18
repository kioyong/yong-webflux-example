package com.yong.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @acthor yong.a.liang
 * @date 2017/11/14
 */
@Data
public class Mark {
    @Id private String id;
    @NotNull
    private String title;
    private boolean isActivity,isLocked;
    private List<Item> item;

    public static Mark updateMark(Mark oldRecord, Mark newRecord){
        oldRecord.setActivity(newRecord.isActivity());
        oldRecord.setItem(newRecord.getItem());
        oldRecord.setLocked(newRecord.isLocked());
        oldRecord.setTitle(newRecord.getTitle());
        return oldRecord;
    }

}
