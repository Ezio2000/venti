package org.venti.common.struc.dform.template;

import lombok.Getter;

public abstract class FormTemplate {

    @Getter
    protected String name;

    public FormTemplate(String name) {
        this.name = name;
    }

}
