package org.venti.guarantee.entity.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteGuaranteeVO {

    private boolean isOk;

    public static DeleteGuaranteeVO success() {
        return DeleteGuaranteeVO.builder().isOk(true).build();
    }

    public static DeleteGuaranteeVO fail() {
        return DeleteGuaranteeVO.builder().isOk(false).build();
    }

}
