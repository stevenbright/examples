package com.truward.blogboard.domain;

public final class TextBlogPost extends AbstractBlogPost {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
