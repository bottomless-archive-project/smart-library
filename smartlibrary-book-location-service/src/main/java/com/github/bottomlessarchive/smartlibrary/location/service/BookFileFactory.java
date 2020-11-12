package com.github.bottomlessarchive.smartlibrary.location.service;

import com.github.bottomlessarchive.smartlibrary.location.domain.BookFile;
import java.nio.file.Path;
import java.util.zip.ZipFile;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class BookFileFactory {

    @SneakyThrows
    public BookFile getBookFile(final Path path) {
        return BookFile.builder()
                .file(new ZipFile(path.toFile()))
                .build();
    }
}
