package com.github.bottomlessarchive.smartlibrary.location.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookLocationEntity {

    private final BookMetadata metadata;
    private final byte[] cover;
    private final byte[] content;
}
