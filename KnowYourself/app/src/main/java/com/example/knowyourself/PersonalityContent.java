package com.example.knowyourself;

public class PersonalityContent {
    private String title, content;

    public PersonalityContent(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PersonalityContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
