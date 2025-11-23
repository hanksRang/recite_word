package com.myplus.engl.res;

// Content.java - 内容实体
public class Content {
    private String title;
    private String description;
    private String path;
    
    // 构造函数
    public Content() {}
    
    public Content(String title, String description, String path) {
        this.title = title;
        this.description = description;
        this.path = path;
    }
    
    // getter和setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}