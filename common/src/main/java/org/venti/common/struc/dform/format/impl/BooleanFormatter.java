package org.venti.common.struc.dform.format.impl;

import org.venti.common.struc.dform.format.Formatter;

public class BooleanFormatter implements Formatter<Boolean> {

    @Override
    public Boolean format(String data) throws FormatException {
        return switch (data) {
            case "true" -> true;
            case "false" -> false;
            default -> throw new FormatException(data);
        };
    }

}
