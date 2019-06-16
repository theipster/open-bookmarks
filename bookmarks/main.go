package main

import (
  "fmt"
  "os"
)

var serviceName = "bookmarks"

func main() {
  var portNumber = os.Getenv("PORT")
  fmt.Printf("Starting %v service on port %v\n", serviceName, portNumber)
  StartWebServer(portNumber)
}
