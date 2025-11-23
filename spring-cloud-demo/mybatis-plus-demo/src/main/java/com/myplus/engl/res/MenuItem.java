package com.myplus.engl.res;

import java.util.List;

// MenuItem.java - 菜单项实体
public class MenuItem {
    private Integer id;
    private String name;
    private List<MenuItem> children;
    private List<Content> contents;
    
    // 构造函数
    public MenuItem() {}
    
    public MenuItem(Integer id, String name, List<MenuItem> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }
    
    // getter和setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<MenuItem> getChildren() { return children; }
    public void setChildren(List<MenuItem> children) { this.children = children; }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}

