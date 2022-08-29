#!/bin/bash

#ltang: Search for CONTAINER_ID of IMAGE_NAME. If CONTAINER_ID is found, stop it


#ltang: Super critical to set the current dir the same dir containing this script and settings.sh
cd "$(dirname "$0")"
pwd
#ltang: Common settings
source ./settings.sh

echo "Running container of image $IMAGE_NAME...$CONTAINER_NAME $HOST_PORT:$CONTAINER_PORT"
docker run -d --name $CONTAINER_NAME  -p $HOST_PORT:$CONTAINER_PORT $IMAGE_NAME

echo "Searching container id of Docker image name...$IMAGE_NAME"
CONTAINER_ID=`get_container_id_running`

if [ -z "$CONTAINER_ID" ] ; then
  echo "ERROR: Failed to run container"
else
  echo "SUCCESS: Found container id $CONTAINER_ID"
fi