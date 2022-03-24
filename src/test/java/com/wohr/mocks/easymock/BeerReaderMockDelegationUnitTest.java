package com.wohr.mocks.easymock;

import org.easymock.*;
import org.junit.*;

import static org.easymock.EasyMock.*;

public class BeerReaderMockDelegationUnitTest {

    EasyMockSupport easyMockSupport = new EasyMockSupport();

    @Test
    public void givenBeerReader_whenReadAndWriteSequencially_thenWorks() {
        ArticleReader mockArticleReader = easyMockSupport.createMock(ArticleReader.class);
        IArticleWriter mockArticleWriter = easyMockSupport.createMock(IArticleWriter.class);
        BeerReader baeldungReader = new BeerReader(mockArticleReader, mockArticleWriter);

        expect(mockArticleReader.next()).andReturn(null);
        expect(mockArticleWriter.write("title", "content")).andReturn("");
        easyMockSupport.replayAll();

        baeldungReader.readNext();
        baeldungReader.write("title", "content");
        easyMockSupport.verifyAll();
    }

}