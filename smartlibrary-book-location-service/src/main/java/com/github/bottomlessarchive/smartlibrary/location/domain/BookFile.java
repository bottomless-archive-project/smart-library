package com.github.bottomlessarchive.smartlibrary.location.domain;

import java.io.InputStream;
import java.util.zip.ZipFile;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
@Builder
public class BookFile implements AutoCloseable {

    private final ZipFile file;

    @SneakyThrows
    public InputStream getMetadata() {
        return file.getInputStream(file.getEntry("metadata.json"));
    }

    @SneakyThrows
    public InputStream getCover() {
        return file.getInputStream(file.getEntry("cover"));
    }

    @SneakyThrows
    public InputStream getContent() {
        return file.getInputStream(file.getEntry("content"));
    }

    @Override
    public void close() throws Exception {
        file.close();
    }
}
