package com.instapopulars.instapopular.model;

public class ViewMap implements Comparable<ViewMap>{

    private String key;
    private Integer value;

    public ViewMap(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public int compareTo(ViewMap o) {
        return o.value.compareTo(value);
    }
}
