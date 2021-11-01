#!/bin/bash

if docker pull kvalitetsit/rm-eboks-service-documentation:latest; then
    echo "Copy from old documentation image."
    docker cp $(docker create kvalitetsit/rm-eboks-service-documentation:latest):/usr/share/nginx/html target/old
fi
