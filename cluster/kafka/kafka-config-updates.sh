#!/bin/bash
## This script is here to update kafka settings.  This file will be copied into the docker image and run locally 
##      so it updates the relevant kafka config file.  Any updates to this file should be done in a version 
##      agnostic manner please.

echo "auto.create.topics.enable=false" >> /opt/kafka_*/config/server.properties

