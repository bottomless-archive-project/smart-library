package com.github.bottomlessarchive.smartlibrary.book.view.service;

import com.github.bottomlessarchive.smartlibrary.book.domain.BookEntity;
import com.github.bottomlessarchive.smartlibrary.book.view.response.BookResponse;
import java.util.Base64;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class BookResponseFactory {

    @SneakyThrows
    public BookResponse getBookResponse(final BookEntity bookEntity) {
        return BookResponse.builder()
                .title(bookEntity.getTitle())
                .cover(Base64.getEncoder().encodeToString(bookEntity.getCover().readAllBytes()))
                .coverType(bookEntity.getCoverType())
                .build();
    }
}
