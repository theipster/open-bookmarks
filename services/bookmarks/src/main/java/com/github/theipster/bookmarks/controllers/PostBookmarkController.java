package com.github.theipster.openbookmarks.bookmarks.controllers;

import com.github.theipster.openbookmarks.bookmarks.services.Bookmark;
import com.github.theipster.openbookmarks.bookmarks.services.BookmarksService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestController
public class PostBookmarkController {

    @Autowired
    private BookmarksService service;

    private Bookmark buildBookmarkFromCommand(AddBookmarkCommand command) {
        return new Bookmark(command.getUrl(), command.getTitle());
    }

    @PostMapping(path = "/bookmarks", consumes = "application/json")
    public ResponseEntity<String> addBookmark(@RequestBody AddBookmarkCommand command) throws URISyntaxException {
        Bookmark bookmark = buildBookmarkFromCommand(command);
        UUID id = service.save(bookmark);
        return ResponseEntity
            .status(HttpStatus.SEE_OTHER)
            .location(getLocation(id))
            .build();
    }

    private URI getLocation(UUID uuid) throws URISyntaxException {
        String uri = String.format("/bookmarks/%s", uuid.toString());
        return new URI(uri);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().build();
    }
}
