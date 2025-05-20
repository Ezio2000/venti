package org.venti.guarantee.entity.VO;

import lombok.Builder;
import lombok.Data;
import org.venti.guarantee.entity.BO.Guarantee;
import org.venti.guarantee.entity.BO.GuaranteeVerification;

@Data
@Builder
public class GetGuaranteeVO {

    private Guarantee guarantee;

    private GuaranteeVerification guaranteeVerification;

}
