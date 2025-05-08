package org.venti.agileform.agile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.venti.agileform.agile.entity.VO.CellTemplateVO;
import org.venti.agileform.agile.service.CellTemplateService;

import java.util.ArrayList;
import java.util.List;

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
                .creator(cellTemplateDTO.getCreator())
                .createDateTime(cellTemplateDTO.getCreateDateTime())
                .updateDateTime(cellTemplateDTO.getUpdateDateTime())
                .build();
    }

    @GetMapping("/cellTemplates")
    public List<CellTemplateVO> getAllCellTemplates() {
        var list = new ArrayList<CellTemplateVO>();
        var cellTemplateDTOList = cellTemplateService.getAllCellTemplates();
        cellTemplateDTOList.forEach(cellTemplateDTO -> {
            var cellTemplateVO = CellTemplateVO.builder()
                    .id(cellTemplateDTO.getId())
                    .name(cellTemplateDTO.getName())
                    .type(cellTemplateDTO.getType())
                    .description(cellTemplateDTO.getDescription())
                    .creator(cellTemplateDTO.getCreator())
                    .createDateTime(cellTemplateDTO.getCreateDateTime())
                    .updateDateTime(cellTemplateDTO.getUpdateDateTime())
                    .build();
            list.add(cellTemplateVO);
        });
        return list;
    }

}
