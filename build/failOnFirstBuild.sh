#!/bin/sh

echo "${GITHUB_REPOSITORY}"
echo "${DOCKER_SERVICE}"
if [ "${GITHUB_REPOSITORY}" != "KvalitetsIT/rm-eboks-service" ] && [ "${DOCKER_SERVICE}" = "kvalitetsit/rm-eboks-service" ]; then
  echo "Please run setup.sh REPOSITORY_NAME"
  exit 1
fi
