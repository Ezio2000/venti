package org.venti.agileform.agile.entity.DO;

import lombok.Data;
import org.venti.agileform.typehandler.CellTypeHandler;
import org.venti.common.struc.dform.cell.CellType;
import org.venti.jdbc.anno.Entity;
import org.venti.jdbc.typehandler.DatetimeHandler;
import org.venti.jdbc.typehandler.LongHandler;

import java.time.LocalDateTime;

@Data
@Entity
public class CellTemplateDO {

    @Entity.Column(value = "id", typeHandler = LongHandler.class)
    private long id;

    @Entity.Column("name")
    private String name;

    @Entity.Column(value = "type", typeHandler = CellTypeHandler.class)
    private CellType type;

    @Entity.Column("description")
    private String description;

    @Entity.Column("creator")
    private String creator;

    @Entity.Column(value = "created_at", typeHandler = DatetimeHandler.class)
    private LocalDateTime createDateTime;

    @Entity.Column(value = "updated_at", typeHandler = DatetimeHandler.class)
    private LocalDateTime updateDateTime;

}
