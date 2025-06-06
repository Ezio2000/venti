package org.venti.guarantee.entity.DO;

import lombok.Getter;
import org.venti.common.constant.ValidStatus;
import org.venti.jdbc.anno.Entity;

@Entity
@Getter
public class GuaranteeVerificationDO extends BaseDO {

    @Entity.Column("guarantee_number")
    private String guaranteeNumber;

    @Entity.Column("security_code")
    private String securityCode;

    @Entity.Column(value = "status")
    private ValidStatus status;

}
