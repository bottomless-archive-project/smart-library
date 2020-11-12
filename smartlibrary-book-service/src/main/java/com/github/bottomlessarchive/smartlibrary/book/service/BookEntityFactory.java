package com.github.bottomlessarchive.smartlibrary.book.service;

import com.github.bottomlessarchive.smartlibrary.book.domain.BookEntity;
import com.github.bottomlessarchive.smartlibrary.location.service.BookLocationEntityFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookEntityFactory {

    private final BookLocationEntityFactory bookLocationEntityFactory;

    public List<BookEntity> getBooks() {
        return bookLocationEntityFactory.getBookLocations().stream()
                .map(bookLocationEntity ->
                        BookEntity.builder()
                                .version(bookLocationEntity.getMetadata().getVersion())
                                .title(bookLocationEntity.getMetadata().getTitle())
                                .cover(bookLocationEntity.getCover())
                                .content(bookLocationEntity.getContent())
                                .build()
                )
                .collect(Collectors.toList());
    }
}
