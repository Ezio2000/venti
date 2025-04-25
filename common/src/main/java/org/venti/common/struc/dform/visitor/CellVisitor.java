package org.venti.common.struc.dform.visitor;

import org.venti.common.struc.dform.cell.Cell;

public interface CellVisitor {

    void visit(Cell<?> cell);

}
