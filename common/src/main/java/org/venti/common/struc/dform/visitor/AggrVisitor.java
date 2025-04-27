package org.venti.common.struc.dform.visitor;

import org.venti.common.struc.dform.core.Cell;
import org.venti.common.struc.dform.core.Form;
import org.venti.common.struc.dform.core.Sheet;

public abstract class AggrVisitor implements CellVisitor, SheetVisitor, FormVisitor {

    @Override
    public void visit(Cell<?> cell) {
        visit0(cell);
    }

    @Override
    public void visit(Sheet sheet) {
        visit0(sheet);
        sheet.getCellMap().forEach((_, cell) -> cell.accept(this));
    }

    @Override
    public void visit(Form form) {
        visit0(form);
        form.getSheetMap().forEach((_, sheet) -> sheet.accept(this));
    }

    public void visit0(Cell<?> cell) {};

    public void visit0(Sheet sheet) {};

    public void visit0(Form form) {};

}
