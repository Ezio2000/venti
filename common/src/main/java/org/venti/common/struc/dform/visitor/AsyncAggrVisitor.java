package org.venti.common.struc.dform.visitor;

import org.venti.common.struc.dform.core.Cell;

public abstract class AsyncAggrVisitor extends AggrVisitor {

    @Override
    public void visit(Cell<?> cell) {
        Thread.ofVirtual().name(STR."async-aggr-visitor-thread-\{cell.getName()}").start(() -> visit0(cell));
    }

}
