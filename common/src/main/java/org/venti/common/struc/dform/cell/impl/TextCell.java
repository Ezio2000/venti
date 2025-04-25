package org.venti.common.struc.dform.cell.impl;

import org.venti.common.struc.dform.cell.Cell;
import org.venti.common.struc.dform.cell.CellType;
import org.venti.common.struc.dform.format.impl.TextFormatter;
import org.venti.common.util.SingletonFactory;

public class TextCell extends Cell<String> {

    public TextCell(String name, String data) {
        super(name, CellType.TEXT, data, SingletonFactory.getInstance(TextFormatter.class));
    }

}
