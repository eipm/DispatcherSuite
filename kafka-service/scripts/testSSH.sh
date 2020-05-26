#!/usr/bin/env bash
host=`hostname`
user="${USER}"

echo "Checking ssh key..."
if [[ ! -e ~/.ssh/id_rsa.pub ]]; then
 echo "ERROR: ssh key is not available for the current user"
 exit 1
fi
found=`grep -c ${user}@${host}  ~/.ssh/authorized_keys`
if [[ "${found}" == "1" ]]; then
   echo "key found"
else
    echo "Key not found"
    ssh-copy-id ${user}@${host}
fi

echo "Testing SSH connection..."
ssh ${user}@${host} 'echo "Connected successfully"; exit'
