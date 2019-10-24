package org.gty.chord.exception;

public class ChordHealthCheckException extends RuntimeException {

    public ChordHealthCheckException() {
    }

    public ChordHealthCheckException(String msg) {
        super(msg);
    }

    public ChordHealthCheckException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
