package org.venti.agileform.agile.entity.DTO;

import lombok.Builder;
import lombok.Data;
import org.venti.common.struc.dform.cell.CellType;

@Data
@Builder
public class CellTemplateDTO {

    private long id;

    private String name;

    private CellType type;

    private String description;

}
