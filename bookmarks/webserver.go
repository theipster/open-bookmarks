package main

import (
  "net/http"
  "log"
)

func StartWebServer(port string) {

  // Attach router
  r := NewRouter()
  http.Handle("/", r);

  // Start listening
  log.Println("Starting HTTP service at " + port)
  err := http.ListenAndServe(":" + port, nil)

  if err != nil {
    log.Println("An error occurred starting HTTP listener at port " + port)
    log.Println("Error: " + err.Error())
  }
}
