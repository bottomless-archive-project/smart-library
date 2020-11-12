package com.github.bottomlessarchive.smartlibrary.book.service;

import com.github.bottomlessarchive.smartlibrary.book.domain.BookEntity;
import com.github.bottomlessarchive.smartlibrary.location.domain.BookLocationEntity;
import com.github.bottomlessarchive.smartlibrary.location.service.BookLocationEntityFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

    public List<BookEntity> getBooks() {
        return bookLocationEntityFactory.getBookLocations().stream()
                .map(this::transformBookLocation)
                .collect(Collectors.toList());
    }

    private BookEntity transformBookLocation(final BookLocationEntity bookLocationEntity) {
        return BookEntity.builder()
                .id(bookLocationEntity.getMetadata().getId())
                .version(bookLocationEntity.getMetadata().getVersion())
                .title(bookLocationEntity.getMetadata().getTitle())
                .description(bookLocationEntity.getMetadata().getDescription())
                .cover(bookLocationEntity.getCover())
                .coverType(bookLocationEntity.getMetadata().getCoverType())
                .content(bookLocationEntity.getContent())
                .contentType(bookLocationEntity.getMetadata().getContentType())
                .bookLocation(bookLocationEntity)
                .build();
    }
}
