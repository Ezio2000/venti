package org.venti.agileform.agile.entity.DO;

import lombok.Data;
import org.venti.common.constant.ValidStatus;
import org.venti.jdbc.anno.Entity;
import org.venti.jdbc.typehandler.DateTimeHandler;
import org.venti.jdbc.typehandler.LongHandler;
import org.venti.jdbc.typehandler.ValidStatusHandler;

import java.time.LocalDateTime;

@Data
@Entity
public class GuaranteeVerificationDO {

    @Entity.Column(value = "id", typeHandler = LongHandler.class)
    private long id;

    @Entity.Column("guarantee_number")
    private String guaranteeNumber;

    @Entity.Column("security_code")
    private String securityCode;

    @Entity.Column(value = "status", typeHandler = ValidStatusHandler.class)
    private ValidStatus status;

    @Entity.Column(value = "create_time", typeHandler = DateTimeHandler.class)
    private LocalDateTime createTime;

    @Entity.Column(value = "update_time", typeHandler = DateTimeHandler.class)
    private LocalDateTime updateTime;

}
