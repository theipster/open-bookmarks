package com.github.theipster.openbookmarks.bookmarks.controllers;

import java.net.URL;
import lombok.NonNull;
import lombok.Value;

@Value
public class AddBookmarkCommand {
    public final @NonNull URL url;
    public final @NonNull String title;
}
