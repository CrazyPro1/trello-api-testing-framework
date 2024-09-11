package com.trello.api.payloads;

public class BoardPayload {
    private String name;

    public BoardPayload(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}