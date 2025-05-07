package org.venti.agileform.agile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.venti.agileform.agile.entity.DTO.CellTemplateDTO;
import org.venti.agileform.agile.mapper.CellTemplateMapper;
import org.venti.common.struc.dform.cell.CellType;

@Service
public class CellTemplateService {

    @Autowired
    private CellTemplateMapper cellTemplateMapper;

    public CellTemplateDTO getCellTemplateByName(String name) {
        var builder = CellTemplateDTO.builder();
        cellTemplateMapper.getCellTemplateByName(name, map -> {
            builder.id((Long) map.get("id"));
            builder.name((String) map.get("name"));
            builder.type((CellType) map.get("type"));
            builder.description((String) map.get("description"));
        });
        return builder.build();
    }

}
