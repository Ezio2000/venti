package org.venti.guarantee.entity.RO;

import lombok.Data;
import org.venti.guarantee.entity.BO.Guarantee;
import org.venti.guarantee.entity.BO.GuaranteeVerification;

@Data
public class AddGuaranteeRO {

    private Guarantee guarantee;

    private GuaranteeVerification guaranteeVerification;

}
