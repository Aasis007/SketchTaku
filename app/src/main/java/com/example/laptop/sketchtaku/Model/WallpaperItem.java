package com.example.laptop.sketchtaku.Model;

/**
 * Created by Laptop on 4/27/2018.
 */

public class WallpaperItem {
    public String url;
    public String categoryId;

    public WallpaperItem() {
    }

    public WallpaperItem(String url, String categoryId) {
        this.url = url;
        this.categoryId = categoryId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
