package org.venti.common.struc.dform.cell.impl;

import org.venti.common.struc.dform.cell.CellType;
import org.venti.common.struc.dform.core.Cell;
import org.venti.common.struc.dform.format.impl.DateFormatter;
import org.venti.common.util.SingletonFactory;

import java.time.LocalDate;

public class DateCell extends Cell<LocalDate> {

    public DateCell(String name, String data) {
        super(name, CellType.DATE, data, SingletonFactory.getInstance(DateFormatter.class));
    }

}
