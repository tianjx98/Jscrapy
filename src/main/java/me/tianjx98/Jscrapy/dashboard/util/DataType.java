package me.tianjx98.Jscrapy.dashboard.util;

public enum DataType {
    INTEGER("int"),
    ;
    private String name;

    private DataType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
