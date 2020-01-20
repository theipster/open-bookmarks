package com.github.theipster.openbookmarks.bookmarks.persistence;

import java.net.URL;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity(name = "bookmarks")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"url", "title"}))
public class BookmarkEntity {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private final UUID id;

    private URL url;

    private String title;
}
