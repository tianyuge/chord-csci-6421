package org.gty.chord.model.fingertable;

public class FingerTableIdInterval {

    private Long begin;
    private Long end;

    public FingerTableIdInterval(Long begin, Long end) {
        this.begin = begin;
        this.end = end;
    }

    public Long getBegin() {
        return begin;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "[" + begin + ", " + end + ")";
    }
}
