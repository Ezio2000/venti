package org.venti.agileform.agile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.venti.agileform.agile.entity.DO.GuaranteeVerificationDO;
import org.venti.agileform.agile.service.GuaranteeService;
import org.venti.common.constant.ValidStatus;

@RestController
public class GuaranteeController {

    @Autowired
    private GuaranteeService guaranteeService;

    @GetMapping("/get")
    public GuaranteeVerificationDO getGuaranteeVerificationByCode(@RequestParam(value = "securityCode") String securityCode) {
        return guaranteeService.getGuaranteeVerificationByCode(securityCode);
    }

    @GetMapping("/set")
    public int addGuaranteeVerification(@RequestParam(value = "guaranteeNumber") String guaranteeNumber,
                                        @RequestParam(value = "securityCode") String securityCode,
                                        @RequestParam(value = "status") int status) {
        return guaranteeService.addGuaranteeVerification(guaranteeNumber, securityCode, ValidStatus.fromCode(status));
    }

}
