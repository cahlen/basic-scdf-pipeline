#!/usr/bin/env bash

curl -H "Accept: application/json" -X POST "http://localhost:9393/apps/source/source" -i \
    -d "uri=maven://com.example.pipeline:source:0.0.1-SNAPSHOT" \
    -d "force=true"

curl -H "Accept: application/json" -X POST "http://localhost:9393/apps/processor/transform" -i \
    -d "uri=maven://com.example.pipeline:transform:0.0.1-SNAPSHOT" \
    -d "force=true"

curl -H "Accept: application/json" -X POST "http://localhost:9393/apps/sink/sink" -i \
    -d "uri=maven://com.example.pipeline:sink:0.0.1-SNAPSHOT" \
    -d "force=true"
