package com.github.theipster.openbookmarks.bookmarks.persistence;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface Repository extends CrudRepository<BookmarkEntity, UUID> {

    Optional<BookmarkEntity> findOptionalByUrlAndTitle(URL url, String title);
}
