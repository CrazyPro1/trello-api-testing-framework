package com.trello.api.payloads;

public class CardPayload {
    private String name;
    private String idList;

    public CardPayload(String name, String idList) {
        this.name = name;
        this.idList = idList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }
}