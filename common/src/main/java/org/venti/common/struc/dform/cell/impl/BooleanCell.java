package org.venti.common.struc.dform.cell.impl;

import org.venti.common.struc.dform.core.Cell;
import org.venti.common.struc.dform.cell.CellType;
import org.venti.common.struc.dform.format.impl.BooleanFormatter;
import org.venti.common.util.SingletonFactory;

public class BooleanCell extends Cell<Boolean> {

    public BooleanCell(String name, String data) {
        super(name, CellType.BOOLEAN, data, SingletonFactory.getInstance(BooleanFormatter.class));
    }

}
