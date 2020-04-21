package com.example.restservice;

public class RestReponse {

    private final String content;

    public RestReponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

}