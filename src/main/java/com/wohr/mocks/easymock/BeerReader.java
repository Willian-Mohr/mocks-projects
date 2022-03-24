package com.wohr.mocks.easymock;

import java.util.List;

public class BeerReader {

	private ArticleReader articleReader;
	private IArticleWriter articleWriter;

	public BeerReader() {
	}

	public BeerReader(ArticleReader articleReader) {
		this.articleReader = articleReader;
	}

	public BeerReader(IArticleWriter writer) {
		this.articleWriter = writer;
	}

	public BeerReader(ArticleReader articleReader, IArticleWriter writer) {
		this.articleReader = articleReader;
		this.articleWriter = writer;
	}

	public BeerArticle readNext() {
		return articleReader.next();
	}

	public List<BeerArticle> readTopic(String topic) {
		return articleReader.ofTopic(topic);
	}

	public String write(String title, String content) {
		return articleWriter.write(title, content);
	}

}
