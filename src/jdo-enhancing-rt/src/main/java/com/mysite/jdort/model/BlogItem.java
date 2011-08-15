package com.mysite.jdort.model;

import javax.jdo.annotations.PersistenceCapable;

/**
 * Represents a single blog item
 */
@PersistenceCapable
public final class BlogItem implements PropertyBag {
    String content = null;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStringProperty(String propertyName) {
        if ("content".equals(propertyName)) {
            return getContent();
        }
        throw new IllegalArgumentException(propertyName);
    }
}
