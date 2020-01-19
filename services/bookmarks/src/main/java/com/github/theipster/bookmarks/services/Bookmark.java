package com.github.theipster.openbookmarks.bookmarks.services;

import java.net.URL;
import lombok.NonNull;
import lombok.Value;

@Value
public class Bookmark {
    public @NonNull URL url;
    public @NonNull String title;
}
