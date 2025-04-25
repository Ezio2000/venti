package org.venti.common.struc.dform.format.impl;

import org.venti.common.struc.dform.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;

public class NumberFormatter implements Formatter<Number> {

    @Override
    public Number format(String data) throws FormatException {
        try {
            return NumberFormat.getInstance().parse(data).doubleValue();
        } catch (ParseException e) {
            throw new FormatException(data, e);
        }
    }

}
