package com.github.bottomlessarchive.smartlibrary.book.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookCreationContext {

    private final String isbn;
    private final String title;
    private final String description;
    private final String author;
    private final String publisher;
    private final String published;
    private final int pageCount;
    private final byte[] cover;
    private final byte[] content;
}
