package com.github.bottomlessarchive.smartlibrary.location.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bottomlessarchive.smartlibrary.location.configuration.BookLocationProperties;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookFile;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookLocationCreationContext;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookLocationEntity;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookMetadata;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
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
    public void newBookLocation(final BookLocationCreationContext bookLocationCreationContext) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(
                Path.of(bookLocationProperties.getLocation(),
                        bookLocationCreationContext.getMetadata().getTitle() + ".book").toFile())))) {

            // Cover
            ZipEntry cover = new ZipEntry("cover");

            zipOutputStream.putNextEntry(cover);

            byte[] bytes = new byte[1024];
            int count = bookLocationCreationContext.getCover().read(bytes);
            while (count > -1) {
                zipOutputStream.write(bytes, 0, count);
                count = bookLocationCreationContext.getCover().read(bytes);
            }

            // Content
            ZipEntry content = new ZipEntry("content");

            zipOutputStream.putNextEntry(content);

            bytes = new byte[1024];
            count = bookLocationCreationContext.getContent().read(bytes);
            while (count > -1) {
                zipOutputStream.write(bytes, 0, count);
                count = bookLocationCreationContext.getContent().read(bytes);
            }

            // Metadata
            ZipEntry metadata = new ZipEntry("metadata.json");

            byte[] meta = objectMapper.writeValueAsBytes(bookLocationCreationContext.getMetadata());

            zipOutputStream.putNextEntry(metadata);
            zipOutputStream.write(meta, 0, meta.length);
        }
    }

    @SneakyThrows
    private BookLocationEntity parseBookLocation(final Path path) {
        final BookFile bookFile = bookFileFactory.getBookFile(path);

        final BookMetadata bookMetadata = objectMapper.readValue(bookFile.getMetadata(), BookMetadata.class);

        return BookLocationEntity.builder()
                .metadata(bookMetadata)
                .bookFile(bookFile)
                .build();
    }
}
