FROM haskell:8

# Download dependencies (GraphViz)
RUN apt-get update && \
    apt-get install -y --no-install-recommends graphviz && \
    rm -rf /var/lib/apt/lists/*

# Build in an isolated dir
WORKDIR /app

# Copy source
COPY . .

# Download compiler
RUN stack setup

# Compile and install
RUN stack install

# Run
ENTRYPOINT ["erd"]
