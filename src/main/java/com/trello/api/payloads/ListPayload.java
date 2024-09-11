package com.trello.api.payloads;

public class ListPayload {
    private String name;
    private String idBoard;

    public ListPayload(String name) {
        this.name = name;
    }

    public ListPayload(String name, String idBoard) {
        this.name = name;
        this.idBoard = idBoard;
    }


}