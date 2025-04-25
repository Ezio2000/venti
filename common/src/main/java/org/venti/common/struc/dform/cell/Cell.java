package org.venti.common.struc.dform.cell;

import lombok.Getter;
import lombok.Setter;
import org.venti.common.struc.dform.format.Formatter;
import org.venti.common.struc.dform.template.CellTemplate;
import org.venti.common.struc.dform.visitor.CellVisitor;

public abstract class Cell<T> extends CellTemplate {

    @Getter
    @Setter
    protected String data;

    @Setter
    protected Formatter<T> formatter;

    public Cell(String name, CellType type, String data, Formatter<T> formatter) {
        super(name, type);
        this.data = data;
        this.formatter = formatter;
    }

    public T format() throws Formatter.FormatException {
        return formatter.format(data);
    }

    public void accept(CellVisitor visitor) {
        visitor.visit(this);
    }

}
