# some helpers

## to build
docker build -f Dockerfile -t local/kafka .

## to run
docker run -p 9092:9092 --net=host local/kafka
