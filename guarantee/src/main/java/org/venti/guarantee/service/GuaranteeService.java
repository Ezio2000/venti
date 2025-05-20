package org.venti.guarantee.service;

import org.venti.guarantee.entity.RO.AddGuaranteeRO;
import org.venti.guarantee.entity.RO.GetGuaranteeRO;
import org.venti.guarantee.entity.VO.AddGuaranteeVO;
import org.venti.guarantee.entity.VO.GetGuaranteeVO;

public interface GuaranteeService {

    AddGuaranteeVO addGuarantee(AddGuaranteeRO ro);

    GetGuaranteeVO getGuarantee(GetGuaranteeRO ro);

}
