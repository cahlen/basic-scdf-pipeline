#!/usr/bin/env sh

sleep 10

mongo --port 27017 \
    -u root \
    -p 'example' \
    --authenticationDatabase 'admin' \
    --host mongo.localhost \
    --eval  "db.createCollection('starbucks'); db.getSiblingDB('test').createUser({user:'testUser', pwd:'testPassword', roles:[{role:'readWrite',db:'test'}]});"