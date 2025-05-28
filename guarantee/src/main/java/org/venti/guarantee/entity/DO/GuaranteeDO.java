package org.venti.guarantee.entity.DO;

import lombok.Getter;
import org.venti.jdbc.anno.Entity;

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

    @Entity.Column(value = "guarantee_amount")
    private double guaranteeAmount;

    @Entity.Column(value = "guarantee_deadline")
    private LocalDateTime guaranteeDeadline;

    @Entity.Column("guarantor")
    private String guarantor;

}
