package com.github.theipster.openbookmarks.bookmarks.services;

import com.github.theipster.openbookmarks.bookmarks.persistence.BookmarkEntity;
import com.github.theipster.openbookmarks.bookmarks.persistence.Repository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarksService {

    @Autowired
    private Repository repository;

    private BookmarkEntity buildEntityFromModel(Bookmark bookmark) {
        return BookmarkEntity.builder()
            .url(bookmark.getUrl())
            .title(bookmark.getTitle())
            .build();
    }

    private Bookmark buildModelFromEntity(BookmarkEntity entity) {
        return new Bookmark(entity.getUrl(), entity.getTitle());
    }

    public Optional<Bookmark> findById(UUID id) {
        return repository.findById(id)
            .map(this::buildModelFromEntity);
    }

    public UUID save(Bookmark bookmark) {
        return repository.findOptionalByUrlAndTitle(bookmark.getUrl(), bookmark.getTitle())
            .orElseGet(() -> repository.save(buildEntityFromModel(bookmark)))
            .getId();
    }
}
