package org.venti.guarantee.entity.DO;

import lombok.Getter;
import org.venti.jdbc.anno.Entity;

import java.time.LocalDateTime;

@Getter
public class BaseDO {

    @Entity.Column(value = "id")
    private long id;

    @Entity.Column(value = "create_time")
    private LocalDateTime createTime;

    @Entity.Column(value = "update_time")
    private LocalDateTime updateTime;

}
