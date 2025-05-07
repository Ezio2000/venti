package org.venti.agileform.agile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.venti.agileform.agile.entity.VO.CellTemplateVO;
import org.venti.agileform.agile.service.CellTemplateService;

@RestController
public class CellTemplateController {

    @Autowired
    private CellTemplateService cellTemplateService;

    @GetMapping("/cellTemplate")
    public CellTemplateVO getCellTemplateByName(@RequestParam(value = "name") String name) {
        var cellTemplateDTO = cellTemplateService.getCellTemplateByName(name);
        return CellTemplateVO.builder()
                .id(cellTemplateDTO.getId())
                .name(cellTemplateDTO.getName())
                .type(cellTemplateDTO.getType())
                .description(cellTemplateDTO.getDescription())
                .build();
    }

}
