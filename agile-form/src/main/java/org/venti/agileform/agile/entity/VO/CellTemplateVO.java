package org.venti.agileform.agile.entity.VO;

import lombok.Builder;
import lombok.Data;
import org.venti.common.struc.dform.cell.CellType;

@Data
@Builder
public class CellTemplateVO {

    private long id;

    private String name;

    private CellType type;

    private String description;

}
