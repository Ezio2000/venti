package org.venti.guarantee.service;

import org.venti.guarantee.entity.RO.AddGuaranteeRO;
import org.venti.guarantee.entity.RO.DeleteGuaranteeRO;
import org.venti.guarantee.entity.RO.GetGuaranteeRO;
import org.venti.guarantee.entity.RO.UpdateGuaranteeRO;
import org.venti.guarantee.entity.VO.AddGuaranteeVO;
import org.venti.guarantee.entity.VO.DeleteGuaranteeVO;
import org.venti.guarantee.entity.VO.GetGuaranteeVO;
import org.venti.guarantee.entity.VO.UpdateGuaranteeVO;

import java.util.List;

public interface GuaranteeService {

    AddGuaranteeVO addGuarantee(AddGuaranteeRO ro);

    DeleteGuaranteeVO deleteGuarantee(DeleteGuaranteeRO ro);

    UpdateGuaranteeVO updateGuarantee(UpdateGuaranteeRO ro);

    GetGuaranteeVO getGuarantee(GetGuaranteeRO ro);

    List<GetGuaranteeVO> getAllGuarantees();

}
