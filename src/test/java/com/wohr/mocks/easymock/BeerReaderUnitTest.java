package com.wohr.mocks.easymock;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.niceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.strictMock;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.Test;

public class BeerReaderUnitTest {
	
    private BeerReader beerReader;

    private ArticleReader mockArticleReader;

    private IArticleWriter mockArticleWriter;

    @Test
    public void givenBeerReader_whenReadNext_thenNextArticleRead() {
        mockArticleReader = mock(ArticleReader.class);
        beerReader = new BeerReader(mockArticleReader);

        expect(mockArticleReader.next()).andReturn(null);
        replay(mockArticleReader);

        BeerArticle article = beerReader.readNext();
        verify(mockArticleReader);
        assertEquals(null, article);
    }
    
    @Test
    public void givenBeerReader_whenReadNextAndSkimTopics_thenAllAllowed() {
        mockArticleReader = strictMock(ArticleReader.class);
        beerReader = new BeerReader(mockArticleReader);

        expect(mockArticleReader.next()).andReturn(null);
        expect(mockArticleReader.ofTopic("easymock")).andReturn(null);
        replay(mockArticleReader);

//        beerReader.readTopic("easymock");
        beerReader.readNext();
        beerReader.readTopic("easymock");
        verify(mockArticleReader);
    }
    
    @Test
    public void givenBeerReader_whenReadNextAndOthers_thenAllowed() {
        mockArticleReader = niceMock(ArticleReader.class);
        beerReader = new BeerReader(mockArticleReader);

        expect(mockArticleReader.next()).andReturn(null);
        replay(mockArticleReader);

        beerReader.readNext();
        beerReader.readTopic("easymock");
        verify(mockArticleReader);
    }
    
    @Test
    public void givenBeerReader_whenWriteMaliciousContent_thenArgumentIllegal() {
        mockArticleWriter = mock(IArticleWriter.class);
        beerReader = new BeerReader(mockArticleWriter);
        expect(mockArticleWriter.write("easymock", "<body onload=alert('beer')>"))
        						.andThrow(new IllegalArgumentException());
        replay(mockArticleWriter);

        Exception expectedException = null;
        try {
            beerReader.write("easymock", "<body onload=alert('beer')>");
        } catch (Exception exception) {
            expectedException = exception;
        }

        verify(mockArticleWriter);
        assertEquals(IllegalArgumentException.class, expectedException.getClass());
    }
    
    @Test
    public void givenBeerReader_whenWrite_thenWriterCalled() {
        mockArticleWriter = mock(IArticleWriter.class);
        beerReader = new BeerReader(mockArticleWriter);
        expect(mockArticleWriter.write("title", "content")).andReturn(null);
        replay(mockArticleWriter);
        String articleId = beerReader.write("title", "content");
        verify(mockArticleWriter);
        assertEquals(null, articleId);
    }

    @Test
    public void givenArticlesInReader_whenReadTillEnd_thenThrowException() {
        ArticleReader mockArticleReader = mock(ArticleReader.class);
        beerReader = new BeerReader(mockArticleReader);
        expect(mockArticleReader.next())
          .andReturn(null)
          .times(2)
          .andThrow(new NoSuchElementException());
        replay(mockArticleReader);
        try {
            for (int i = 0; i < 3; i++) {
                beerReader.readNext();
            }
        } catch (Exception ignored) {
        }
        verify(mockArticleReader);
    }

}

