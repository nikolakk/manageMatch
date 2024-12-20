package com.manage.match.manageMatch.model;

public enum SportEnum {

    FOOTBALL(1),
    BASKETBALL(2);

    private int identifier;

    SportEnum(int identifier) {
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
}
