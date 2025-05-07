package org.venti.agileform.agile.entity.DO;

import lombok.Data;
import org.venti.agileform.typehandler.CellTypeHandler;
import org.venti.common.struc.dform.cell.CellType;
import org.venti.jdbc.anno.Entity;
import org.venti.jdbc.typehandler.LongHandler;

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

}
