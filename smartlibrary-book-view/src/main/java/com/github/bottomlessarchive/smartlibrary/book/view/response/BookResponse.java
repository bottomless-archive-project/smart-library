package com.github.bottomlessarchive.smartlibrary.book.view.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookResponse {

    private final String id;
    private final String title;
    private final String isbn;
    private final String description;
    private final String cover;
    private final String coverType;
    private final List<String> author;
    private final String publisher;
    private final String published;
    private final int pageCount;
}
