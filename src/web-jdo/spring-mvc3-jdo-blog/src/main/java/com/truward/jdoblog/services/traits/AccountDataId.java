package com.truward.jdoblog.services.traits;


public enum AccountDataId {
    DISPLAY_NAME(0),
    AVATAR_URL(1);

    int id;

    AccountDataId(int id) {
        this.id = id;
    }
}
