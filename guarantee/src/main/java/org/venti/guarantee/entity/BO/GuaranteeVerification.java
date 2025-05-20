package org.venti.guarantee.entity.BO;

import lombok.Data;
import org.venti.common.constant.ValidStatus;

@Data
public class GuaranteeVerification {

    private String guaranteeNumber;

    private String securityCode;

    private ValidStatus status;

}
