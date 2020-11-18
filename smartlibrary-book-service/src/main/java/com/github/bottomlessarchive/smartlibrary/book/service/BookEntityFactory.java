package com.github.bottomlessarchive.smartlibrary.book.service;

import com.github.bottomlessarchive.smartlibrary.book.domain.BookEntity;
import com.github.bottomlessarchive.smartlibrary.book.domain.BookCreationContext;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookLocationCreationContext;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookLocationEntity;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookMetadata;
import com.github.bottomlessarchive.smartlibrary.location.service.BookLocationEntityFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookEntityFactory {

    private final BookLocationEntityFactory bookLocationEntityFactory;

    public Optional<BookEntity> getBook(final String id) {
        return bookLocationEntityFactory.getBookLocations().stream()
                .filter(bookLocationEntity -> bookLocationEntity.getMetadata().getId().equals(id))
                .map(this::transformBookLocation)
                .findFirst();
    }

    public void newBook(final BookCreationContext bookEntityCreationContext) {
        final BookMetadata bookMetadata = new BookMetadata();

        bookMetadata.setVersion(1);
        bookMetadata.setId(UUID.randomUUID().toString());
        bookMetadata.setIsbn("");
        bookMetadata.setTitle(bookEntityCreationContext.getTitle());
        bookMetadata.setAuthor(Arrays.asList(bookEntityCreationContext.getAuthor().split(";")));
        bookMetadata.setDescription(bookEntityCreationContext.getDescription());
        bookMetadata.setPublisher(bookEntityCreationContext.getPublisher());
        bookMetadata.setPublished(bookEntityCreationContext.getPublished());

        //TODO: Detect mime type
        bookMetadata.setContentType(MediaType.APPLICATION_PDF.toString());
        bookMetadata.setCoverType(MediaType.IMAGE_JPEG.toString());

        bookLocationEntityFactory.newBookLocation(
                BookLocationCreationContext.builder()
                        .metadata(bookMetadata)
                        .cover(bookEntityCreationContext.getCover())
                        .content(bookEntityCreationContext.getContent())
                        .build()
        );
    }

    public List<BookEntity> getBooks() {
        return bookLocationEntityFactory.getBookLocations().stream()
                .map(this::transformBookLocation)
                .collect(Collectors.toList());
    }

    private BookEntity transformBookLocation(final BookLocationEntity bookLocationEntity) {
        return BookEntity.builder()
                .id(bookLocationEntity.getMetadata().getId())
                .version(bookLocationEntity.getMetadata().getVersion())
                .isbn(bookLocationEntity.getMetadata().getIsbn())
                .title(bookLocationEntity.getMetadata().getTitle())
                .author(bookLocationEntity.getMetadata().getAuthor())
                .publisher(bookLocationEntity.getMetadata().getPublisher())
                .published(bookLocationEntity.getMetadata().getPublished())
                .pageCount(bookLocationEntity.getMetadata().getPageCount())
                .description(bookLocationEntity.getMetadata().getDescription())
                .cover(bookLocationEntity.getCover())
                .coverType(bookLocationEntity.getMetadata().getCoverType())
                .content(bookLocationEntity.getContent())
                .contentType(bookLocationEntity.getMetadata().getContentType())
                .bookLocation(bookLocationEntity)
                .build();
    }
}
