package com.example.gogoalfight.exploratory.model;

/**
 * Created by GoGoalFight on 2017/12/3.
 */

public class Link {
    private String source;
    private String target;
    private String value;

    public Link(String source, String target, String value) {
        this.source = source;
        this.target = target;
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getValue() {
        return value;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
