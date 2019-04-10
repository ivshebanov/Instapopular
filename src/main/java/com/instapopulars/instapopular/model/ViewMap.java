package com.instapopulars.instapopular.model;

public class ViewMap implements Comparable<ViewMap> {

    private String key;
    private Integer value;

    public ViewMap(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public int compareTo(ViewMap o) {
        return o.value.compareTo(value);
    }
}
