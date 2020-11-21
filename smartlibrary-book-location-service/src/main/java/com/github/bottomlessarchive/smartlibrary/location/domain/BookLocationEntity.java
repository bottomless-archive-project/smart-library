package com.github.bottomlessarchive.smartlibrary.location.domain;

import java.io.InputStream;
import java.nio.file.Path;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
public class BookLocationEntity implements AutoCloseable {

    private final Path path;
    private final BookFile bookFile;
    private final BookMetadata metadata;

    public InputStream getCover() {
        return bookFile.getCover();
    }

    public InputStream getContent() {
        return bookFile.getContent();
    }

    @Override
    @SneakyThrows
    public void close() {
        bookFile.close();
    }
}
