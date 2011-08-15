package com.truward.hdbdemo.dal.services;

public class DummyHashCalculator implements HashCalculator {
    public String calcHash(String source) {
        return "DUMMY-" + source;
    }
}
