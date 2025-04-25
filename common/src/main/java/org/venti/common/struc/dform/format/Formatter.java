package org.venti.common.struc.dform.format;

public interface Formatter<T> {

    T format(String data) throws FormatException;

    class FormatException extends Exception {

        public FormatException(String msg) {
            super(msg);
        }

        public FormatException(String msg, Throwable cause) {
            super(msg, cause);
        }

    }

}
