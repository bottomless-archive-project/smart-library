package com.github.bottomlessarchive.smartlibrary.book.domain;

import com.github.bottomlessarchive.smartlibrary.location.domain.BookLocationEntity;
import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookEntity implements AutoCloseable {

    private final int version;
    private final String title;
    private final String coverType;
    private final InputStream cover;
    private final String contentType;
    private final InputStream content;
    private final BookLocationEntity bookLocation;

    @Override
    public void close() throws Exception {
        bookLocation.close();
    }
}
