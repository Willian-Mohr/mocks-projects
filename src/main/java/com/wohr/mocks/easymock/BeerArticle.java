package com.wohr.mocks.easymock;

public class BeerArticle {

	public static BeerArticle simpleArticle(String title, String content) {
		return new BeerArticle(title, content);
	}

	private String title;
	private String content;

	private BeerArticle(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String title() {
		return this.title;
	}

	public String content() {
		return this.content;
	}

}
