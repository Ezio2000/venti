package org.venti.common.struc.form.cell;

import lombok.Data;

@Data
public abstract class Cell<T> implements CellFormater<T> {
    protected CellType type;
    protected String data;
    public Cell(CellType type, String data) {
        this.type = type;
        this.data = data;
    }
}
