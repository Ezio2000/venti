package org.venti.agileform.entity.VO;

import lombok.Builder;
import lombok.Data;
import org.venti.agileform.entity.BO.Guarantee;
import org.venti.agileform.entity.BO.GuaranteeVerification;

@Data
@Builder
public class GetGuaranteeVO {

    private Guarantee guarantee;

    private GuaranteeVerification guaranteeVerification;

}
