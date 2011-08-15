package com.mysite.jdort.rtone;

import java.util.Map;

public interface InitializableRunner extends Runnable {
    void init(Map<String, ?> properties);
}
