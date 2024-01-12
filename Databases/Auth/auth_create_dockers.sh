#!/bin/bash

# POSTGRESQL IMAGE


POSTGRESQL_DOCKER_IMAGE="bitnami/postgresql:latest"

POSTGRESQL_MASTER_NAME="master_auth_server"
POSTGRESQL_MASTER_USER="postmasteruser"
POSTGRESQL_MASTER_PASSWORD="postmasterpassword"
POSTGRESQL_MASTER_PORT="5432"

POSTGRESQL_SLAVE_NAME="slave_auth_server"
POSTGRESQL_SLAVE_USER="postslaveuser"
POSTGRESQL_SLAVE_PASSWORD="postslavepassword"
POSTGRESQL_SLAVE_PORT="5433"

# Running Commands
# Master Auth DB
if [[ $(docker ps -a -q -f name=${POSTGRESQL_MASTER_NAME}) ]]; then
    docker start ${POSTGRESQL_MASTER_NAME}
else
    docker run \
    --name ${POSTGRESQL_MASTER_NAME} \
    -v /path/to/postgresql-persistence:/persistenceFileDir \
    -d -p 5432:${POSTGRESQL_MASTER_PORT} \
    ${POSTGRESQL_DOCKER_IMAGE} 

# Slave Auth DB
if [[ $(docker ps -a -q -f name=${POSTGRESQL_SLAVE_NAME}) ]]; then
    docker start ${POSTGRESQL_SLAVE_NAME}
else
    docker run --name ${POSTGRESQL_SLAVE_NAME} -d -p 5432:${POSTGRESQL_SLAVE_PORT} ${POSTGRESQL_DOCKER_IMAGE} 