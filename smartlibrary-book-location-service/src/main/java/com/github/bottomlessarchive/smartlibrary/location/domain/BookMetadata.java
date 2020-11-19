package com.github.bottomlessarchive.smartlibrary.location.domain;

import java.util.List;
import lombok.Data;

@Data
public class BookMetadata {

    private String id;

    /**
     * The version of the standard that was used to create the book file.
     */
    private int version;

    /**
     * The title of the book.
     */
    private String title;

    private String isbn;

    /**
     * The MIME type of the cover.
     */
    private String coverType;

    /**
     * The MIME type of the content.
     */
    private String contentType;

    private String description;
    private List<String> author;
    private List<List<String>> subject;
    private String publisher;
    private String published;
    private int pageCount;
}
