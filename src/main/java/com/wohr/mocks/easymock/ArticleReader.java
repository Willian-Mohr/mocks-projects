package com.wohr.mocks.easymock;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArticleReader {

    private List<BeerArticle> articles;
    private Iterator<BeerArticle> articleIter;

    public ArticleReader() {
        this(new ArrayList<>());
    }

    public ArticleReader(List<BeerArticle> articles) {
        this.articles = articles;
        this.articleIter = this.articles.iterator();
    }

    public List<BeerArticle> ofTopic(String topic) {
        return articles
          .stream()
          .filter(article -> article
            .title()
            .contains(topic))
          .collect(toList());
    }

    public BeerArticle next() {
        return this.articleIter.next();
    }

}
