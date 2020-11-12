package com.github.bottomlessarchive.smartlibrary.location.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bottomlessarchive.smartlibrary.location.configuration.BookLocationProperties;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookFile;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookLocationEntity;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookMetadata;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookLocationEntityFactory {

    private static final String BOOK_EXTENSION = ".book";

    private final ObjectMapper objectMapper;
    private final BookFileFactory bookFileFactory;
    private final BookLocationProperties bookLocationProperties;

    @SneakyThrows
    public List<BookLocationEntity> getBookLocations() {
        return Files.walk(Path.of(bookLocationProperties.getLocation()))
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().endsWith(BOOK_EXTENSION))
                .map(this::parseBookLocation)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private BookLocationEntity parseBookLocation(final Path path) {
        final BookFile bookFile = bookFileFactory.getBookFile(path);

        final BookMetadata bookMetadata = objectMapper.readValue(bookFile.getMetadata(), BookMetadata.class);

        return BookLocationEntity.builder()
                .metadata(bookMetadata)
                .cover(bookFile.getCover())
                .content(bookFile.getContent())
                .build();
    }
}
