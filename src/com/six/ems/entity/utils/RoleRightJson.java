package com.six.ems.entity.utils;

import java.util.List;

public class RoleRightJson {
    private Integer id;
    private String text;
    private List<RoleRightJson> children;
    private String state ;
    private String attributes;
    private boolean checked =false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public RoleRightJson() {
    }

    public RoleRightJson(Integer id) {
        this.id = id;
    }

    public RoleRightJson(String text, List<RoleRightJson> children, String state, String attributes) {
        this.text = text;
        this.children = children;
        this.state = state;
        this.attributes = attributes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<RoleRightJson> getChildren() {
        return children;
    }

    public void setChildren(List<RoleRightJson> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
}
