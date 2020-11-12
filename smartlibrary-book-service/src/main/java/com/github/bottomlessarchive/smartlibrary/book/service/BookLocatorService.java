package com.github.bottomlessarchive.smartlibrary.book.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bottomlessarchive.smartlibrary.book.configuration.BookProperties;
import com.github.bottomlessarchive.smartlibrary.book.domain.BookEntity;
import com.github.bottomlessarchive.smartlibrary.book.domain.BookMetadata;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

//TODO: Create a book-location module and differentiate between the location (zip representation) and a book
@Service
@RequiredArgsConstructor
public class BookLocatorService {

    private static final String BOOK_EXTENSION = ".book";

    private final BookProperties bookProperties;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public List<BookEntity> getBooks() {
        return Files.walk(Path.of(bookProperties.getLocation()))
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().endsWith(BOOK_EXTENSION))
                .map(this::parseBook)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private BookEntity parseBook(final Path path) {
        ZipFile zipFile = new ZipFile(path.toFile());

        BookMetadata bookMetadata = objectMapper.readValue(
                zipFile.getInputStream(zipFile.getEntry("metadata.json")), BookMetadata.class);

        return BookEntity.builder()
                .title(bookMetadata.getTitle())
                .build();
    }
}
