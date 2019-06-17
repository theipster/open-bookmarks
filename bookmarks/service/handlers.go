package service

import (
  "encoding/json"
  "github.com/gorilla/mux"
  "github.com/theipster/open-bookmarks/bookmarks/dbclient"
  "net/http"
  "strconv"
)

var DbClient dbclient.IBoltClient

func GetBookmark(w http.ResponseWriter, r *http.Request) {

  // Read the id
  var id = mux.Vars(r)["id"]

  // Read from the database
  bookmark, err := DbClient.QueryBookmark(id)

  // If error, return HTTP 404
  if err != nil {
    w.WriteHeader(http.StatusNotFound)
    return
  }

  // If found, marshal into JSON, write headers and content
  data, _ := json.Marshal(bookmark)
  w.Header().Set("Content-Type", "application/json")
  w.Header().Set("Content-Length", strconv.Itoa(len(data)))
  w.WriteHeader(http.StatusOK)
  w.Write(data)
}
