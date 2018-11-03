#!/usr/bin/env bash

set -x
set -eu

curl -H "Accept: application/json" -X POST "http://localhost:9393/streams/definitions" -i \
    -d "name=basic-stream" \
    -d "definition=source|transform|haversine-transform|sink"

curl -H "Content-type: application/json" -X POST "http://localhost:9393/streams/deployments/basic-stream" -i \
    -d "{\"app.*.--spring.profiles.active\":\"docker\",\"app.*.--spring.cloud.config.uri\":\"http://config-server.localhost:8888\"}"
