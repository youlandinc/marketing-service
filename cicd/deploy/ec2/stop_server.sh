#!/bin/bash

#ltang: Search for CONTAINER_ID of IMAGE_NAME. If CONTAINER_ID is found, stop it

#ltang: Super critical to set the current dir the same dir containing this script and settings.sh
cd "$(dirname "$0")"
pwd
#ltang: Common settings
source ./settings.sh

echo "Searching container id of Docker image name...$IMAGE_NAME"
CONTAINER_ID=`get_container_id_all`

if [ -z "$CONTAINER_ID" ] ; then
  echo "None found => Remove all stopped containers..."
  docker rm $(docker ps -a -q)
else
  echo "Stopping and removing container id..."
  docker stop $CONTAINER_ID
  docker rm -f $CONTAINER_ID
fi
