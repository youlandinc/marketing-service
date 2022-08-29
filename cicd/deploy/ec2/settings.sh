#!/bin/bash

#ltang: Place all settings and functions that are shared by all deployment scripts in appspec.yml

cd "$(dirname "$0")"
pwd

AWS_DEFAULT_REGION="us-west-1"
AWS_ACCOUNT_ID="348152033681"
IMAGE_SERVER="$AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com"

IMAGE_REPO_NAME="java-markting-service"
IMAGE_TAG="latest"
IMAGE_NAME="$IMAGE_SERVER/$IMAGE_REPO_NAME:$IMAGE_TAG"

CONTAINER_NAME="jjava-markting-service-service"
CONTAINER_PORT=8080
HOST_PORT=8080

function get_container_id_all () {
  #container_id = `docker ps -aqf "name=$IMAGE_NAME"`
  container_id=`docker ps -a | awk '{ print $1,$2 }' | grep $IMAGE_NAME | awk '{ print $1 }'`
  echo $container_id
}

function get_container_id_running () {
  #container_id = `docker ps -aqf "name=$IMAGE_NAME"`
  container_id=`docker ps -a | awk '{ print $1,$2 }' | grep $IMAGE_NAME | awk '{ print $1 }'`
  echo $container_id
}

function count_docker_images() {
  count=`docker images | wc -l`
  return $count
}

export -f get_container_id_all
export -f get_container_id_running
export -f count_docker_images

