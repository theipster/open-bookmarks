package main

import (
  "github.com/gorilla/mux"
)

func NewRouter() *mux.Router {

  // Create instance of Gorilla router
  router := mux.NewRouter().StrictSlash(true)

  // Iterate over declared routes
  for _, route := range routes {

    // Attach route
    router.Methods(route.Method).
      Path(route.Pattern).
      Name(route.Name).
      Handler(route.HandlerFunc)
  }

  return router
}
