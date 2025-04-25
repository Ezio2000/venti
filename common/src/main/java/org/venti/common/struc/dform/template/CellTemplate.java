package org.venti.common.struc.dform.template;

import lombok.Getter;
import org.venti.common.struc.dform.cell.CellType;

public abstract class CellTemplate {

    @Getter
    protected String name;

    @Getter
    protected CellType type;

    public CellTemplate(String name, CellType type) {
        this.name = name;
        this.type = type;
    }

}
