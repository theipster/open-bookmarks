FROM golang:alpine
ENV PKG="github.com/theipster/open-bookmarks/bookmarks"

# Copy current dir into container
WORKDIR $GOPATH/src/$PKG
COPY . .

# Download deps and install
RUN apk add git && \
    go get -v ./... && \
    go install -v

# Run
WORKDIR $GOPATH/bin
CMD ["bookmarks"]
