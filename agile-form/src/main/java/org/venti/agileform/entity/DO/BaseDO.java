package org.venti.agileform.entity.DO;

import lombok.Getter;
import org.venti.jdbc.anno.Entity;
import org.venti.jdbc.typehandler.DateTimeHandler;
import org.venti.jdbc.typehandler.LongHandler;

import java.time.LocalDateTime;

@Getter
public class BaseDO {

    @Entity.Column(value = "id", typeHandler = LongHandler.class)
    private long id;

    @Entity.Column(value = "create_time", typeHandler = DateTimeHandler.class)
    private LocalDateTime createTime;

    @Entity.Column(value = "update_time", typeHandler = DateTimeHandler.class)
    private LocalDateTime updateTime;

}
