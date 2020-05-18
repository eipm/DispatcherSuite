#!/usr/bin/env sh

LOOKUP_USER=`getent passwd <HOST_USER_ID> | cut -d: -f1`
echo "LOOKUP_USER=${LOOKUP_USER}"
CURRENT_ID=`id -u`
if [[ "kduser" == "${LOOKUP_USER}" ]]; then
    echo "the kduser was already created with the same HOST_USER_ID"
    su kduser -c 'ssh -o StrictHostKeyChecking=no -i /ssh/id_rsa -T -v <HOST_USER>@<HOST_HOSTNAME> "<SSH_COMMAND>"'
elif [[ "<HOST_USER_ID>" == "${CURRENT_ID}" ]]; then
    echo "the current user owns the ID HOST_USER_ID"
    ssh -o StrictHostKeyChecking=no -i /ssh/id_rsa -T -v <HOST_USER>@<HOST_HOSTNAME> "<SSH_COMMAND>"
elif [[ -z "${LOOKUP_USER}" ]]; then
    echo "the ID <HOST_USER_ID> is free"
    id -u kduser || adduser -u <HOST_USER_ID> kduser -D -h /home/kduser
    su kduser -c 'ssh -o StrictHostKeyChecking=no -i /ssh/id_rsa -T -v <HOST_USER>@<HOST_HOSTNAME> "<SSH_COMMAND>"'
else
    >&2 echo "ID <HOST_USER_ID> is reserved and cannot be used inside this Docker container, please choose another user"
    exit 1
fi
