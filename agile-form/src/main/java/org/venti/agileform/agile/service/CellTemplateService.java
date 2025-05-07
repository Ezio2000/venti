package org.venti.agileform.agile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.venti.agileform.agile.entity.DTO.CellTemplateDTO;
import org.venti.agileform.agile.mapper.CellTemplateMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class CellTemplateService {

    @Autowired
    private CellTemplateMapper cellTemplateMapper;

    public CellTemplateDTO getCellTemplateByName(String name) {
        var cellTemplateDO = cellTemplateMapper.getCellTemplateByName(name);
        return CellTemplateDTO.builder()
                .id(cellTemplateDO.getId())
                .name(cellTemplateDO.getName())
                .type(cellTemplateDO.getType())
                .description(cellTemplateDO.getDescription())
                .build();
    }

    public List<CellTemplateDTO> getAllCellTemplates() {
        var list = new ArrayList<CellTemplateDTO>();
        var cellTemplateDOList = cellTemplateMapper.getAllCellTemplates();
        cellTemplateDOList.forEach(cellTemplateDO -> {
            var cellTemplateDTO = CellTemplateDTO.builder()
                    .id(cellTemplateDO.getId())
                    .name(cellTemplateDO.getName())
                    .type(cellTemplateDO.getType())
                    .description(cellTemplateDO.getDescription())
                    .build();
            list.add(cellTemplateDTO);
        });
        return list;
    }

}
