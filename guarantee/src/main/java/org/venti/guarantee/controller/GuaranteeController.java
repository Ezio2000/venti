package org.venti.guarantee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.venti.guarantee.entity.RO.DeleteGuaranteeRO;
import org.venti.guarantee.entity.RO.GetGuaranteeRO;
import org.venti.guarantee.entity.RO.AddGuaranteeRO;
import org.venti.guarantee.entity.RO.UpdateGuaranteeRO;
import org.venti.guarantee.entity.VO.AddGuaranteeVO;
import org.venti.guarantee.entity.VO.DeleteGuaranteeVO;
import org.venti.guarantee.entity.VO.GetGuaranteeVO;
import org.venti.guarantee.entity.VO.UpdateGuaranteeVO;
import org.venti.guarantee.service.GuaranteeService;

import java.util.List;

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

    @PostMapping("/delete")
    public ResponseEntity<DeleteGuaranteeVO> deleteGuarantee(@RequestBody DeleteGuaranteeRO ro) {
        var vo = guaranteeService.deleteGuarantee(ro);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateGuaranteeVO> updateGuarantee(@RequestBody UpdateGuaranteeRO ro) {
        var vo = guaranteeService.updateGuarantee(ro);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/get")
    public ResponseEntity<GetGuaranteeVO> getGuarantee(@RequestBody GetGuaranteeRO ro) {
        var vo = guaranteeService.getGuarantee(ro);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/all")
    public ResponseEntity<List<GetGuaranteeVO>> getAllGuarantees() {
        var voList = guaranteeService.getAllGuarantees();
        return ResponseEntity.ok(voList);
    }

}
