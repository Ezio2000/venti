package org.venti.common.struc.dform.template;

import lombok.Getter;

public abstract class SheetTemplate {

    @Getter
    protected String name;

    public SheetTemplate(String name) {
        this.name = name;
    }

}
