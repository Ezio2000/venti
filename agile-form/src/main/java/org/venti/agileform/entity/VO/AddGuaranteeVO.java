package org.venti.agileform.entity.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddGuaranteeVO {

    private String guaranteeNumber;

    private String securityCode;

}
