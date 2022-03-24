package com.wohr.mocks.easymock;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.NoSuchElementException;

import static org.easymock.EasyMock.*;

@RunWith(EasyMockRunner.class)
public class BeerReaderAnnotatedUnitTest {

	@Mock
	ArticleReader mockArticleReader;

	@Mock
	IArticleWriter mockArticleWriter;
	
	@TestSubject
    BeerReader baeldungReader = new BeerReader();

    @Test
    public void givenBeerReader_whenReadNext_thenNextArticleRead() {
        expect(mockArticleReader.next()).andReturn(null);
        replay(mockArticleReader);
        baeldungReader.readNext();
        verify(mockArticleReader);
    }

    @Test
    public void givenBeerReader_whenWrite_thenWriterCalled() {
        expect(mockArticleWriter.write("title", "content")).andReturn(null);
        replay(mockArticleWriter);
        baeldungReader.write("title", "content");
        verify(mockArticleWriter);
    }

    @Test
    public void givenArticlesInReader_whenReadTillEnd_thenThrowException() {
        
    	expect(mockArticleReader.next())
          .andReturn(null)
          .times(2)
          .andThrow(new NoSuchElementException());
    	
        replay(mockArticleReader);
        
        try {
            for (int i = 0; i < 3; i++) {
                baeldungReader.readNext();
            }
        } catch (Exception ignored) {
        }
        
        verify(mockArticleReader);
    }

}