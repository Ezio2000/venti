package org.venti.guarantee.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.venti.guarantee.anno.TransactionMethod;
import org.venti.guarantee.entity.BO.Guarantee;
import org.venti.guarantee.entity.BO.GuaranteeVerification;
import org.venti.guarantee.entity.DO.GuaranteeDO;
import org.venti.guarantee.entity.DO.GuaranteeVerificationDO;
import org.venti.guarantee.entity.RO.AddGuaranteeRO;
import org.venti.guarantee.entity.RO.GetGuaranteeRO;
import org.venti.guarantee.entity.VO.AddGuaranteeVO;
import org.venti.guarantee.entity.VO.GetGuaranteeVO;
import org.venti.guarantee.mapper.GuaranteeMapper;
import org.venti.guarantee.mapper.GuaranteeVerificationMapper;
import org.venti.guarantee.service.GuaranteeService;
import org.venti.common.constant.ValidStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        var guaranteeNumber = ro.getGuaranteeVerification().getGuaranteeNumber();
        var beneficiary = ro.getGuarantee().getBeneficiary();
        var guaranteedParty = ro.getGuarantee().getGuaranteedParty();
        var projectName = ro.getGuarantee().getProjectName();
        var guaranteeAmount = ro.getGuarantee().getGuaranteeAmount();
        var guaranteeDeadline = ro.getGuarantee().getGuaranteeDeadline();
        var guarantor = ro.getGuarantee().getGuarantor();
        // guaranteeVerification
        var securityCode = ro.getGuaranteeVerification().getSecurityCode();
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
                .guarantee(ro.getGuarantee())
                .guaranteeVerification(ro.getGuaranteeVerification())
                .build();
    }

    @Override
    public GetGuaranteeVO getGuarantee(GetGuaranteeRO ro) {
        var guaranteeNumber = ro.getGuaranteeNumber();
        var securityCode = ro.getSecurityCode();
        var guaranteeVerificationDO = guaranteeVerificationMapper.getGuaranteeVerificationByCode(securityCode);
        if (tryVerify(guaranteeVerificationDO, guaranteeNumber)) {
            var guaranteeDO = guaranteeMapper.getGuaranteeByNumber(guaranteeNumber);
            var guarantee = new Guarantee();
            BeanUtils.copyProperties(guaranteeDO, guarantee);
            var guaranteeVerification = new GuaranteeVerification();
            BeanUtils.copyProperties(guaranteeVerificationDO, guaranteeVerification);
            return GetGuaranteeVO.builder()
                    .guarantee(guarantee)
                    .guaranteeVerification(guaranteeVerification)
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public List<GetGuaranteeVO> getAllGuarantees() {
        var guaranteeDOList = guaranteeMapper.getAllGuarantees();
        var guaranteeVerificationDOList = guaranteeVerificationMapper.getAllGuaranteeVerifications();
        var guaranteeMap = guaranteeDOList.stream()
                .collect(Collectors.toMap(
                        GuaranteeDO::getGuaranteeNumber,
                        _do -> {
                            var guarantee = new Guarantee();
                            BeanUtils.copyProperties(_do, guarantee);
                            return guarantee;
                        }
                ));
        var guaranteeVerificationMap = guaranteeVerificationDOList.stream()
                .collect(Collectors.toMap(
                        GuaranteeVerificationDO::getGuaranteeNumber,
                        _do -> {
                            var guaranteeVerification = new GuaranteeVerification();
                            BeanUtils.copyProperties(_do, guaranteeVerification);
                            return guaranteeVerification;
                        }
                ));
        var voList = new ArrayList<GetGuaranteeVO>();
        guaranteeMap.forEach((guaranteeNumber, guarantee) -> {
            var vo = GetGuaranteeVO.builder()
                    .guarantee(guarantee)
                    .guaranteeVerification(guaranteeVerificationMap.get(guaranteeNumber))
                    .build();
            voList.add(vo);
        });
        return voList;
    }

    private boolean tryVerify(GuaranteeVerificationDO guaranteeVerificationDO, String guaranteeNumber) {
        return guaranteeVerificationDO != null &&
                guaranteeVerificationDO.getStatus() == ValidStatus.VALID &&
                Objects.equals(guaranteeVerificationDO.getGuaranteeNumber(), guaranteeNumber);
    }

}
