package org.venti.common.struc.dform.cell.impl;

import org.venti.common.struc.dform.cell.Cell;
import org.venti.common.struc.dform.cell.CellType;
import org.venti.common.struc.dform.format.impl.NumberFormatter;
import org.venti.common.util.SingletonFactory;

public class NumberCell extends Cell<Number> {

    public NumberCell(String name, String data) {
        super(name, CellType.NUMBER, data, SingletonFactory.getInstance(NumberFormatter.class));
    }

}
