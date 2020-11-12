package com.github.bottomlessarchive.smartlibrary.book.view.controller;

import com.github.bottomlessarchive.smartlibrary.book.service.BookEntityFactory;
import com.github.bottomlessarchive.smartlibrary.book.view.response.BookResponse;
import com.github.bottomlessarchive.smartlibrary.book.view.service.BookResponseFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/book/{id}")
    public BookResponse getBook(@PathVariable String id) {
        return bookEntityFactory.getBook(id)
                .map(bookResponseFactory::getBookResponse)
                .orElseThrow(() -> new IllegalStateException("Book not found with id: " + id + "!"));
    }
}
