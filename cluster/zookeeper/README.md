# Some helpers

## Build
docker build -f Dockerfile -t local/zookeeper .

## Run
docker run -p 2181:2181 --net=host local/zookeeper
