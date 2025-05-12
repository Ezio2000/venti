package org.venti.agileform.entity.DO;

import lombok.Getter;
import org.venti.jdbc.anno.Entity;
import org.venti.jdbc.typehandler.DateTimeHandler;
import org.venti.jdbc.typehandler.DoubleHandler;

import java.time.LocalDateTime;

@Entity
@Getter
public class GuaranteeDO extends BaseDO {

    @Entity.Column("guarantee_number")
    private String guaranteeNumber;

    @Entity.Column("beneficiary")
    private String beneficiary;

    @Entity.Column("guaranteed_party")
    private String guaranteedParty;

    @Entity.Column("project_name")
    private String projectName;

    @Entity.Column(value = "guarantee_amount", typeHandler = DoubleHandler.class)
    private double guaranteeAmount;

    @Entity.Column(value = "guarantee_deadline", typeHandler = DateTimeHandler.class)
    private LocalDateTime guaranteeDeadline;

    @Entity.Column("guarantor")
    private String guarantor;

}
