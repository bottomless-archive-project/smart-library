package com.github.bottomlessarchive.smartlibrary.location.domain;

import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookLocationEntity implements AutoCloseable {

    private final BookFile bookFile;
    private final BookMetadata metadata;

    public InputStream getCover() {
        return bookFile.getCover();
    }

    public InputStream getContent() {
        return bookFile.getContent();
    }

    @Override
    public void close() throws Exception {
        bookFile.close();
    }
}
