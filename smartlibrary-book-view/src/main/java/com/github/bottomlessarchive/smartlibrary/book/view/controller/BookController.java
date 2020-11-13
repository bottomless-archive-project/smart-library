package com.github.bottomlessarchive.smartlibrary.book.view.controller;

import com.github.bottomlessarchive.smartlibrary.book.domain.BookEntity;
import com.github.bottomlessarchive.smartlibrary.book.service.BookEntityFactory;
import com.github.bottomlessarchive.smartlibrary.book.view.response.BookResponse;
import com.github.bottomlessarchive.smartlibrary.book.view.service.BookResponseFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookEntityFactory bookEntityFactory;
    private final BookResponseFactory bookResponseFactory;

    @GetMapping("/book/")
    public List<BookResponse> getBooks() {
        return bookEntityFactory.getBooks().stream()
                .map(bookResponseFactory::getBookResponse)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    @GetMapping("/book/{id}")
    public BookResponse getBook(@PathVariable String id) {
        return bookEntityFactory.getBook(id)
                .map(bookResponseFactory::getBookResponse)
                .orElseThrow(() -> new IllegalStateException("Book not found with id: " + id + "!"));
    }

    @SneakyThrows
    @GetMapping("/book/{id}/content")
    public ResponseEntity<byte[]> readBook(@PathVariable String id) {
        try (BookEntity bookEntity = bookEntityFactory.getBook(id)
                .orElseThrow(() -> new IllegalStateException("Book not found with id: " + id + "!"))) {
            final byte[] contents = bookEntity.getContent().readAllBytes();

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(bookEntity.getContentType()));
            headers.setContentDisposition(
                    ContentDisposition
                            .builder("inline")
                            .filename(bookEntity.getTitle() + ".pdf")
                            .build()
            );

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(contents);
        }
    }
}
