package main

import (
  "github.com/theipster/open-bookmarks/bookmarks/service"
  "net/http"
)

// Define a route
type Route struct {
  Name string
  Method string
  Pattern string
  HandlerFunc http.HandlerFunc
}

// Define a collection of routes
type Routes []Route

// Initialise routes
var routes = Routes{
  Route {
    "GetBookmark",
    "GET",
    "/bookmarks/{id}",
    service.GetBookmark,
  },
}
