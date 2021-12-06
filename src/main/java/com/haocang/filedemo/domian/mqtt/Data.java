package com.haocang.filedemo.domian.mqtt;

public class Data {

    private String name;
    private String value;

    public Data(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
