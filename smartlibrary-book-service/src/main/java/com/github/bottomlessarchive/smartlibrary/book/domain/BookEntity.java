package com.github.bottomlessarchive.smartlibrary.book.domain;

import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookEntity {

    private final int version;
    private final String title;
    private final InputStream cover;
    private final InputStream content;
}
