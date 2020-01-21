package com.github.theipster.openbookmarks.bookmarks.controllers;

import com.github.theipster.openbookmarks.bookmarks.services.Bookmark;
import com.github.theipster.openbookmarks.bookmarks.services.BookmarksService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetBookmarkController {

    @Autowired
    private BookmarksService service;

    @GetMapping(path = "/bookmarks/{uuid}")
    public ResponseEntity<Bookmark> getBookmarkById(@PathVariable UUID uuid) {
        return ResponseEntity.of(service.findById(uuid));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.notFound().build();
    }
}
