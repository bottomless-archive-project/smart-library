package com.github.bottomlessarchive.smartlibrary.book.domain;

import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookCreationContext {

    private final String title;
    private final String description;
    private final String author;
    private final String publisher;
    private final String published;
    private final InputStream cover;
    private final InputStream content;
}
