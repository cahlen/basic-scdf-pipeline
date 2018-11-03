### basic scdf pipeline

#### What does it do?
Right now the pipline simply reads a csv file full of starbucks locations in the United States.
The source applications pushes these payloads to a transform application which uppercases the
address and storeName values.  It then gets pushed to another transformer which uses a haversine 
formula to filter payloads within a 30km radius from the center of manhattan.  Finally a sink
applications pushes the resulting data to a mongodb.

This pipeline is meant to be a simple example of creating a pipeline in Spring Cloud Data Flow.

-----

- First you need to clone the repository
  ```
  git clone https://github.com/cahlen/basic-scdf-pipeline.git
  ```

- Use docker-compose to deploy the stream
  ```
  cd basic-scdf-pipeline
  docker-compose up -d
  ```
- Build each application and publish each artifact in your local maven repository
  ```
  (cd SCDF/source; ./gradlew clean build install)
  (cd SCDF/transform; ./gradlew clean build install)
  (cd SCDF/haversine-transform; ./gradlew clean build install)
  (cd SCDF/sink; ./gradlew clean build install)
  ```
  
- Register the applications using the following shell script so that
you can create the stream (give it a few minutes to boot up the docker-compose environment)
  ```
  ./tools/register-apps.sh
  ```
  
- Deploy the stream
  ```
  ./tools/deploy-stream.sh
  ```

- To view the stream in the Spring Cloud Data Flow UI point your browser
to the following URI
  ```
  http://localhost:9393/dashboard
  ```
  ![SCDF Stream Create UI](docs/images/create-stream-screen.png)
  
- Point your web browser to the mongo-express UI at
  ```
  http://localhost:8889 
  ```
  And choose the `test` database and the `starbucks` collection within that 
  database to view the results.
  
----
