# Some helpers

## Build
docker build -f Dockerfile -t local/zookeeper .

## Run
docker run -p 2181:2181 -p 2888:2888 -p 3888:3888 --net=host local/zookeeper
