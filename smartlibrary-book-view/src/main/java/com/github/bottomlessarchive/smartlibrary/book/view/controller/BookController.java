package com.github.bottomlessarchive.smartlibrary.book.view.controller;

import com.github.bottomlessarchive.smartlibrary.book.service.BookEntityFactory;
import com.github.bottomlessarchive.smartlibrary.book.view.response.BookResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookEntityFactory bookLocatorService;

    @GetMapping("/book/")
    public List<BookResponse> getBooks() {
        return bookLocatorService.getBooks().stream()
                .map(bookEntity -> BookResponse.builder()
                        .title(bookEntity.getTitle())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
