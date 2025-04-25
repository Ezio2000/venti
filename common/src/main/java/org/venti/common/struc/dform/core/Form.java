package org.venti.common.struc.dform.core;

import lombok.Getter;
import org.venti.common.struc.dform.template.FormTemplate;
import org.venti.common.struc.dform.visitor.FormVisitor;

import java.util.LinkedHashMap;
import java.util.Map;

public class Form extends FormTemplate {

    @Getter
    protected Map<String, Sheet> sheetMap = new LinkedHashMap<>();

    public Form(String name) {
        super(name);
    }

    public void addSheet(Sheet sheet) {
        this.sheetMap.put(sheet.getName(), sheet);
    }

    public void accept(FormVisitor visitor) {
        visitor.visit(this);
    }

}
