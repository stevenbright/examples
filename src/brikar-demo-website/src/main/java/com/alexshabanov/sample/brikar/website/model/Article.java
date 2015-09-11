package com.alexshabanov.sample.brikar.website.model;

public final class Article {
  private final String heading;
  private final String content;

  public Article(String heading, String content) {
    this.heading = heading;
    this.content = content;
  }

  public String getHeading() {
    return heading;
  }

  public String getContent() {
    return content;
  }


  @Override
  public String toString() {
    return "Article{" +
        "heading='" + getHeading() + '\'' +
        ", content='" + getContent().substring(0, Math.max(10, getContent().length())) + "...'" +
        '}';
  }
}
