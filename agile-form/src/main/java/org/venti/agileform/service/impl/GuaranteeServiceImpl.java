package org.venti.agileform.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.venti.agileform.anno.TransactionMethod;
import org.venti.agileform.entity.BO.Guarantee;
import org.venti.agileform.entity.BO.GuaranteeVerification;
import org.venti.agileform.entity.DO.GuaranteeVerificationDO;
import org.venti.agileform.entity.RO.AddGuaranteeRO;
import org.venti.agileform.entity.RO.GetGuaranteeRO;
import org.venti.agileform.entity.VO.AddGuaranteeVO;
import org.venti.agileform.entity.VO.GetGuaranteeVO;
import org.venti.agileform.mapper.GuaranteeMapper;
import org.venti.agileform.mapper.GuaranteeVerificationMapper;
import org.venti.agileform.service.GuaranteeService;
import org.venti.agileform.util.GuaranteeUtil;
import org.venti.common.constant.ValidStatus;

import java.util.Objects;

@Service
public class GuaranteeServiceImpl implements GuaranteeService {

    @Autowired
    private GuaranteeMapper guaranteeMapper;

    @Autowired
    private GuaranteeVerificationMapper guaranteeVerificationMapper;

    @Override
    @TransactionMethod
    public AddGuaranteeVO addGuarantee(AddGuaranteeRO ro) {
        // guarantee
        var guaranteeNumber = GuaranteeUtil.generateGuaranteeNumber();
        var beneficiary = ro.getGuarantee().getBeneficiary();
        var guaranteedParty = ro.getGuarantee().getGuaranteedParty();
        var projectName = ro.getGuarantee().getProjectName();
        var guaranteeAmount = ro.getGuarantee().getGuaranteeAmount();
        var guaranteeDeadline = ro.getGuarantee().getGuaranteeDeadline();
        var guarantor = ro.getGuarantee().getGuarantor();
        // guaranteeVerification
        var securityCode = GuaranteeUtil.generateSecurityCode();
        var status = ValidStatus.VALID;
        guaranteeMapper.addGuarantee(
                guaranteeNumber,
                beneficiary,
                guaranteedParty,
                projectName,
                guaranteeAmount,
                guaranteeDeadline,
                guarantor
        );
        guaranteeVerificationMapper.addGuaranteeVerification(
                guaranteeNumber,
                securityCode,
                status
        );
        return AddGuaranteeVO.builder()
                .guaranteeNumber(guaranteeNumber)
                .securityCode(securityCode)
                .build();
    }

    @Override
    public GetGuaranteeVO getGuarantee(GetGuaranteeRO ro) {
        var guaranteeNumber = ro.getGuaranteeNumber();
        var securityCode = ro.getSecurityCode();
        var guaranteeVerificationDo = guaranteeVerificationMapper.getGuaranteeVerificationByCode(securityCode);
        if (tryVerify(guaranteeVerificationDo, guaranteeNumber)) {
            var guaranteeDo = guaranteeMapper.getGuaranteeByNumber(guaranteeNumber);
            var guarantee = new Guarantee();
            BeanUtils.copyProperties(guaranteeDo, guarantee);
            var guaranteeVerification = new GuaranteeVerification();
            BeanUtils.copyProperties(guaranteeVerificationDo, guaranteeVerification);
            return GetGuaranteeVO.builder()
                    .guarantee(guarantee)
                    .guaranteeVerification(guaranteeVerification)
                    .build();
        } else {
            return null;
        }
    }

    private boolean tryVerify(GuaranteeVerificationDO guaranteeVerificationDo, String guaranteeNumber) {
        return guaranteeVerificationDo != null &&
                guaranteeVerificationDo.getStatus() == ValidStatus.VALID &&
                Objects.equals(guaranteeVerificationDo.getGuaranteeNumber(), guaranteeNumber);
    }

}
