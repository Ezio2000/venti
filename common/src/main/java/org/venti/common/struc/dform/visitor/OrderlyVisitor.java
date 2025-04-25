package org.venti.common.struc.dform.visitor;

import org.venti.common.struc.dform.core.Cell;
import org.venti.common.struc.dform.core.Form;
import org.venti.common.struc.dform.format.Formatter;
import org.venti.common.struc.dform.core.Sheet;

public class OrderlyVisitor implements CellVisitor, SheetVisitor, FormVisitor {

    @Override
    public void visit(Cell<?> cell) {
        try {
            System.out.println(STR."------ \{cell.getName()} : \{cell.format()}");
        } catch (Formatter.FormatException e) {
            System.out.println(STR."------ \{cell.getName()} : 格式错误[\{cell.getType()}][\{cell.getData()}]");
        }
    }

    @Override
    public void visit(Sheet sheet) {
        System.out.println(STR."--- \{sheet.getName()}");
        sheet.getCellMap().forEach((_, cell) -> {
            visit(cell);
        });
    }

    @Override
    public void visit(Form form) {
        System.out.println(STR."\{form.getName()}");
        form.getSheetMap().forEach((_, sheet) -> {
            visit(sheet);
        });
    }

}
