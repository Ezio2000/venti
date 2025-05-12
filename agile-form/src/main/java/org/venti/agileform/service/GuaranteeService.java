package org.venti.agileform.service;

import org.venti.agileform.entity.RO.AddGuaranteeRO;
import org.venti.agileform.entity.RO.GetGuaranteeRO;
import org.venti.agileform.entity.VO.AddGuaranteeVO;
import org.venti.agileform.entity.VO.GetGuaranteeVO;

public interface GuaranteeService {

    AddGuaranteeVO addGuarantee(AddGuaranteeRO ro);

    GetGuaranteeVO getGuarantee(GetGuaranteeRO ro);

}
