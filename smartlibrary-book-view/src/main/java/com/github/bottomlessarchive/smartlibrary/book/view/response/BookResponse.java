package com.github.bottomlessarchive.smartlibrary.book.view.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookResponse {

    private final String title;
    private final String cover;
    private final String coverType;
}
