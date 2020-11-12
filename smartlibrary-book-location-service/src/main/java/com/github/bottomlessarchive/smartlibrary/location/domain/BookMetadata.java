package com.github.bottomlessarchive.smartlibrary.location.domain;

import lombok.Data;

@Data
public class BookMetadata {

    /**
     * The version of the standard that was used to create the book file.
     */
    private int version;

    /**
     * The title of the book.
     */
    private String title;

    /**
     * The MIME type of the cover.
     */
    private String coverType;

    /**
     * The MIME type of the content.
     */
    private String contentType;
}
