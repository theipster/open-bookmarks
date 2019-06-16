package main

import "net/http"

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
    "GetBookmark",  // Name
    "GET",  // Method
    "/bookmarks/{id}",  // Pattern
    func (w http.ResponseWriter, r *http.Request) {
      w.Header().Set("Content-Type", "application/json; charset=UTF-8")
      w.Write([]byte("{\"result\":\"OK\"}"))
    },
  },
}
