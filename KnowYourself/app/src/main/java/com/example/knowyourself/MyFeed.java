package com.example.knowyourself;

public class MyFeed {
    private String timeStamp, title, article, fileName;

    public MyFeed() {
    }

    public MyFeed(String timeStamp, String title, String article, String fileName) {
        this.timeStamp = timeStamp;
        this.title = title;
        this.article = article;
        this.fileName = fileName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
