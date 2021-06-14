package com.shark.view;

import java.util.List;

/**
 * View视图信息
 */
public class ViewInfo {
    private String className;
    private boolean enabled;
    private boolean shown;
    private int id;
    private String text;

    private List<ViewInfo> childList;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ViewInfo> getChildList() {
        return childList;
    }

    public void setChildList(List<ViewInfo> childList) {
        this.childList = childList;
    }
}
