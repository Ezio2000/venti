package org.venti.agileform.agile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.venti.agileform.agile.entity.DO.GuaranteeVerificationDO;
import org.venti.agileform.agile.mapper.GuaranteeMapper;
import org.venti.agileform.agile.mapper.GuaranteeVerificationMapper;
import org.venti.common.constant.ValidStatus;

@RestController
public class GuaranteeService {

    @Autowired
    private GuaranteeMapper guaranteeMapper;

    @Autowired
    private GuaranteeVerificationMapper guaranteeVerificationMapper;

    public GuaranteeVerificationDO getGuaranteeVerificationByNumber(String guaranteeNumber) {
        return null;
    }

    public GuaranteeVerificationDO getGuaranteeVerificationByCode(String securityCode) {
        return guaranteeVerificationMapper.getGuaranteeVerificationByCode(securityCode);
    }

    public int addGuaranteeVerification(String guaranteeNumber, String securityCode, ValidStatus status) {
        return guaranteeVerificationMapper.addGuaranteeVerification(guaranteeNumber, securityCode, status);
    }

}
