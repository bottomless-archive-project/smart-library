package com.github.bottomlessarchive.smartlibrary.location.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bottomlessarchive.smartlibrary.location.configuration.BookLocationProperties;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookLocationEntity;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookMetadata;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookLocationEntityFactory {

    private static final String BOOK_EXTENSION = ".book";

    private final ObjectMapper objectMapper;
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
        final ZipFile zipFile = new ZipFile(path.toFile());

        final BookMetadata bookMetadata = objectMapper.readValue(
                zipFile.getInputStream(zipFile.getEntry("metadata.json")), BookMetadata.class);

        return BookLocationEntity.builder()
                .metadata(bookMetadata)
                .cover(zipFile.getInputStream(zipFile.getEntry("cover.png")))
                .content(zipFile.getInputStream(zipFile.getEntry("content.pdf")))
                .build();
    }
}
