package com.example.knowyourself;

public class Result {
    String type,ques;

    public Result(){

    }

    public Result(String type, String ques) {
        this.type = type;
        this.ques = ques;
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
