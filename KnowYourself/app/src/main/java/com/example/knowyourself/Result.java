package com.example.knowyourself;

public class Result {
    int position;
    String type,ques;

    public Result(){

    }

    public Result(int position, String type, String ques) {
        this.position = position;
        this.type = type;
        this.ques = ques;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }
}
