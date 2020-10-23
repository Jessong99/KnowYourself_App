package com.example.knowyourself;

public class ResultHistory {
    private String timestamp;

    public ResultHistory(){

    }

    public ResultHistory(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
