package com.shawn.jooo.framework.core.option;

public class Option {

    private Object value;

    private String label;

    private Option(Object value, String label) {
        this.value = value;
        this.label = label;
    }

    public static Option of(Object value, String label) {
        return new Option(value, label);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
