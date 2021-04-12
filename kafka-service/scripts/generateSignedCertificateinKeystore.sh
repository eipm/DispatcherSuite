#!/usr/bin/env bash
echo "
######################################################################
   Welcome to the Keystore generation script for Kafka-Dispatcher
######################################################################
"
if [[ -e kd-keystore.jks ]]; then
    echo "The keystore file kd-keystore.jks already exists in $PWD. "
    echo "kd-keystore.jks already exists in $PWD. Please, delete/move it and run the script again."
    exit 1
fi
echo -n "Enter a new valid keystore password.
Requirements:
 1) 6 to 30 characters, begin with an alphabetic character
 2) only alphanumeric characters and special characters like Underscore (_), Dollar ($), Pound (#)
 3) at least one number
 "
echo -n New password:
read -s password
echo

# see https://docs.oracle.com/javase/7/docs/technotes/tools/solaris/keytool.html
keytool -genkey -alias kafka-dispatcher-certificate -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore kd-keystore.jks -validity 3650 \
     -keypass ${password} -storepass ${password} -dname "cn=Kafka Dispatcher, ou=EIPM, o=Weill Cornell Medicine, c=US"
if [[ $? -ne "0" ]]; then
    echo "Failed to generate the Keystore."
    exit 1
fi
echo "Keystore successfully generated."
echo -n "
######################################################################
   How to use the new Keystore
######################################################################
"
echo "1) Edit your application.yml as follows:
server:
  port: 8443
  servlet:
    context-path: /dispatcher
  ssl:
    key-store: /config/kd-keystore.jks
    key-store-password: <replace with the password>
    keyStoreType: PKCS12
    keyAlias: kafka-dispatcher-certificate

2) Mount the keystore in your docker container as follows:
docker run <option> \
    -v $PWD/kd-keystore.jks:/config/kd-keystore.jks \
    <image>:<tag>
"
