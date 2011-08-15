package com.truward.langexp.typedisp.domain;


public interface ProfilePropertyValue {
    boolean isNull();

    String asString();

    boolean asBoolean();

    int asInt();

    int asDouble();
}
