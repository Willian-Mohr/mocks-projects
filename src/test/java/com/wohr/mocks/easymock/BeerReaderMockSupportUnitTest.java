package com.wohr.mocks.easymock;

import org.easymock.*;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.NoSuchElementException;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class BeerReaderMockSupportUnitTest extends EasyMockSupport {

	@TestSubject
    BeerReader beerReader = new BeerReader();
	
	@Mock
	ArticleReader mockArticleReader;
	
	@Mock
	IArticleWriter mockArticleWriter;

	
    @Test
    public void givenBeerReader_whenReadAndWriteSequencially_thenWorks() {
        expect(mockArticleReader.next())
          .andReturn(null)
          .times(2)
          .andThrow(new NoSuchElementException());
        
        expect(mockArticleWriter.write("title", "content")).andReturn("BAEL-201801");
        
        replayAll();

        Exception expectedException = null;
        try {
            for (int i = 0; i < 3; i++) {
                beerReader.readNext();
            }
        } catch (Exception exception) {
            expectedException = exception;
        }
        String articleId = beerReader.write("title", "content");
        
        verifyAll();
        
        assertEquals(NoSuchElementException.class, expectedException.getClass());
        assertEquals("BAEL-201801", articleId);
    }
    
    @Test
    public void givenBeerReader_whenReadAndWriteSequencially_thenWorks2() {
        expect(mockArticleReader.next())
          .andReturn(null)
          .times(2)
          .andThrow(new NoSuchElementException());
        expect(mockArticleWriter.write("title", "content")).andReturn("BAEL-201801");
        
        replay(mockArticleReader);
        replay(mockArticleWriter);

        Exception expectedException = null;
        try {
            for (int i = 0; i < 3; i++) {
                beerReader.readNext();
            }
        } catch (Exception exception) {
            expectedException = exception;
        }
        String articleId = beerReader.write("title", "content");
        
        verify(mockArticleReader);
        verify(mockArticleWriter);
        
        assertEquals(NoSuchElementException.class, expectedException.getClass());
        assertEquals("BAEL-201801", articleId);
    }

}