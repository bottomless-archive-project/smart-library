package com.github.bottomlessarchive.smartlibrary.location.domain;

import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookLocationCreationContext {

    private final BookMetadata metadata;
    private final InputStream cover;
    private final InputStream content;
}
