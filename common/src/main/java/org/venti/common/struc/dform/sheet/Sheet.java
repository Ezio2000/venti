package org.venti.common.struc.dform.sheet;

import lombok.Getter;
import org.venti.common.struc.dform.cell.Cell;
import org.venti.common.struc.dform.template.SheetTemplate;
import org.venti.common.struc.dform.visitor.SheetVisitor;

import java.util.LinkedHashMap;
import java.util.Map;

public class Sheet extends SheetTemplate {

    @Getter
    protected Map<String, Cell<?>> cellMap = new LinkedHashMap<>();

    public Sheet(String name) {
        super(name);
    }

    public void addCell(Cell<?> cell) {
        cellMap.put(cell.getName(), cell);
    }

    public void accept(SheetVisitor visitor) {
        visitor.visit(this);
    }

}
