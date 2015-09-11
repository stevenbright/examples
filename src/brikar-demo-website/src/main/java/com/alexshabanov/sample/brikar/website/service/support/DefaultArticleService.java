package com.alexshabanov.sample.brikar.website.service.support;

import com.alexshabanov.sample.brikar.website.model.Article;
import com.alexshabanov.sample.brikar.website.service.ArticleService;
import com.truward.brikar.common.log.LogLapse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public final class DefaultArticleService implements ArticleService {

  @LogLapse("GetArticles")
  @Override
  public List<Article> getArticles() {
    try {
      Thread.sleep(200 + ThreadLocalRandom.current().nextInt(200));
    } catch (InterruptedException e) {
      Thread.interrupted();
    }

    return Arrays.asList(new Article("Top Article Sample Heading And Also the Longest One", "<p>Sample 1</p>"),
        new Article("Article #2", "<p>Sample 2</p>\n<p>Sample</p>"),
        new Article("Shrt Hdr", "<p>Sample 3</p>\n<p>Sample 3</p>\n<p>Sample 3</p>"),
        new Article("Last Article", "<p>Lorem ipsum dolorem sic amet</p>\n<br/>\n<br/></p>The End</p>"));
  }
}
