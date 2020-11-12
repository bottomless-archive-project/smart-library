package com.github.bottomlessarchive.smartlibrary.book.domain;

import com.github.bottomlessarchive.smartlibrary.location.domain.BookLocationEntity;
import java.io.InputStream;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookEntity implements AutoCloseable {

    private final String id;
    private final int version;
    private final String isbn;
    private final String title;
    private final String description;
    private final List<String> author;
    private final String publisher;
    private final String published;
    private final int pageCount;
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
