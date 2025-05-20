package org.venti.guarantee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.venti.guarantee.entity.RO.GetGuaranteeRO;
import org.venti.guarantee.entity.RO.AddGuaranteeRO;
import org.venti.guarantee.entity.VO.AddGuaranteeVO;
import org.venti.guarantee.entity.VO.GetGuaranteeVO;
import org.venti.guarantee.service.GuaranteeService;

@RestController
@RequestMapping("/guarantee/v1")
public class GuaranteeController {

    @Autowired
    private GuaranteeService guaranteeService;

    @PostMapping("/add")
    public ResponseEntity<AddGuaranteeVO> addGuarantee(@RequestBody AddGuaranteeRO ro) {
        var vo = guaranteeService.addGuarantee(ro);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/get")
    public ResponseEntity<GetGuaranteeVO> getGuarantee(@RequestBody GetGuaranteeRO ro) {
        var vo = guaranteeService.getGuarantee(ro);
        return ResponseEntity.ok(vo);
    }

}
