package com.mysite.jdosample.model;

import javax.jdo.annotations.PersistenceCapable;

/**
 * Represents a single blog item
 */
@PersistenceCapable
public final class BlogItem {
    String content = null;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
