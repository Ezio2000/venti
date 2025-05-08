package org.venti.agileform.agile.entity.DTO;

import lombok.Builder;
import lombok.Data;
import org.venti.common.struc.dform.cell.CellType;

import java.time.LocalDateTime;

@Data
@Builder
public class CellTemplateDTO {

    private long id;

    private String name;

    private CellType type;

    private String description;

    private String creator;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;

}
