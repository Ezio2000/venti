package org.venti.common.struc.form.cell;

public class StringCell extends Cell<String> {

    public StringCell(String data) {
        super(CellType.STRING, data);
    }

    @Override
    public String format(String data) {
        return data;
    }

}
