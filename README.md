### basic scdf pipeline

To deploy

```
docker-compose up -d
```

Register the apps

```
./tools/register-apps.sh
```

Log into `http://localhost:9393/dashboard` to create the stream.  Deploy the stream and point your web browser to `http://localhost:8889` to the test collection to see the results.
