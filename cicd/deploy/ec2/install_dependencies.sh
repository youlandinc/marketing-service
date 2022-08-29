#!/bin/bash

#ltang: Super critical to set the current dir the same dir containing this script and settings.sh
cd "$(dirname "$0")"
pwd
#ltang: Common settings
source ./settings.sh

sudo usermod -a -G docker ec2-user
sudo service docker start

aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin $IMAGE_SERVER

echo Docker pull if the local image is not up to date...$IMAGE_NAME
docker pull $IMAGE_NAME

IMAGE_COUNT_MAX=3
IMAGE_COUNT=`count_docker_images`
if [ "$IMAGE_COUNT" -gt "$IMAGE_COUNT_MAX" ] ; then
  echo "Found $IMAGE_COUNT images. Pruning unused images that are more than 1-day old..."
  docker image prune --force --filter "until=24h"
fi
