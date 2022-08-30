#!/bin/bash

#ltang: Super critical to set the current dir the same dir containing this script and settings.sh
cd "$(dirname "$0")"
pwd
#ltang: Common settings
source ./settings.sh

#ltang: Clean up the copy of the previous deployment
#Make sure DEPLOY_SCRIPT_COPY matches "destination" in appspec.yml
DEPLOY_SCRIPT_COPY="/home/ec2-user/codedeploy/java-marketing-service"
if [ -d "$DEPLOY_SCRIPT_COPY" ]; then
    rm -rf "$DEPLOY_SCRIPT_COPY"
fi
