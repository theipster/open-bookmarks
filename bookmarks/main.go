package main

import (
  "fmt"
  "os"
  "github.com/theipster/open-bookmarks/bookmarks/dbclient"
  "github.com/theipster/open-bookmarks/bookmarks/service"
)

var serviceName = "bookmarks"

func main() {
  var portNumber = os.Getenv("PORT")
  fmt.Printf("Starting %v service on port %v\n", serviceName, portNumber)
  initializeBoltClient()
  StartWebServer(portNumber)
}

func initializeBoltClient() {
  service.DbClient = &dbclient.BoltClient{}
  service.DbClient.OpenBoltDb()
  service.DbClient.Seed()
}
