package dbclient

import (
  "encoding/json"
  "fmt"
  "github.com/boltdb/bolt"
  "github.com/theipster/open-bookmarks/bookmarks/model"
  "log"
  "strconv"
)

type IBoltClient interface {
  OpenBoltDb()
  QueryBookmark(id string) (model.Bookmark, error)
  Seed()
}

type BoltClient struct {
  boltDb *bolt.DB
}

func (bc *BoltClient) OpenBoltDb() {
  var err error
  bc.boltDb, err = bolt.Open("bookmarks.db", 0600, nil)
  if err != nil {
    log.Fatal(err)
  }
}

func (bc *BoltClient) Seed() {
  bc.initializeBucket()
  bc.seedBookmarks()
}

func (bc *BoltClient) initializeBucket() {
  bc.boltDb.Update(
    func(tx *bolt.Tx) error {
      _, err := tx.CreateBucket([]byte("BookmarkBucket"))
      if err != nil {
        return fmt.Errorf("create bucket failed: %s", err)
      }
      return nil
    },
  )
}

func (bc *BoltClient) seedBookmarks() {
  total := 10
  for i := 0; i < total; i++ {

    // Generate a key
    key := strconv.Itoa(10000 + i)

    // Create instance of struct
    bookmark := model.Bookmark{
      Id: key,
      Name: "Bookmark_" + strconv.Itoa(i),
    }

    // Serialize struct to JSON
    jsonBytes, _ := json.Marshal(bookmark)

    // Write data to bucket
    bc.boltDb.Update(
      func (tx *bolt.Tx) error {
        b := tx.Bucket([]byte("BookmarkBucket"))
        err := b.Put([]byte(key), jsonBytes)
        return err
      },
    )
  }
  fmt.Printf("Seeded %v fake bookmarks...\n", total)
}

func (bc *BoltClient) QueryBookmark(id string) (model.Bookmark, error) {
  bookmark := model.Bookmark{}

  err := bc.boltDb.View(
    func(tx *bolt.Tx) error {

      // Read bucket from DB
      b := tx.Bucket([]byte("BookmarkBucket"))

      // Read value identified by our supplied ID
      bookmarkBytes := b.Get([]byte(id))
      if bookmarkBytes == nil {
        return fmt.Errorf("No bookmark found for " + id)
      }

      // Unmarshal
      json.Unmarshal(bookmarkBytes, &bookmark)

      // Return nil to indicate nothing went wrong
      return nil
    },
  )

  // If there was an error, return it
  if err != nil {
    return model.Bookmark{}, err
  }

  // Return the struct and nil error
  return bookmark, nil
}
