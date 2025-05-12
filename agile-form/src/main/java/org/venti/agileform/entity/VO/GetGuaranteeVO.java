package org.venti.agileform.entity.VO;

import lombok.Data;
import org.venti.agileform.entity.BO.Guarantee;
import org.venti.agileform.entity.BO.GuaranteeVerification;

@Data
public class GetGuaranteeVO {

    private Guarantee guarantee;

    private GuaranteeVerification guaranteeVerification;

}
