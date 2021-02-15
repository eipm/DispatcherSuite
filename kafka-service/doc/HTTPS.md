Enable HTTPS on Kafka-Dispatcher 
--
By default, Kafka-Dispatcher uses HTTP as a transport. 
You can switch to HTTPS easily, by 
1) importing the host certificate and key into a Java KeyStore (JKS).
The keystore is a repository of security certificates plus corresponding private keys, used in SSL encryption. 
2) adding it to your configuration in application.yml. 

# Requirements
* keytool, part of the standard java distribution.

# CA-signed Certificates
CA-signed certificate must be used in the dev, staging and production environments.

The following steps explain how to create a keystore for the certificate(s) and key and configure 
the kafka-dispatcher instance to use it. 

### Step1. Host Certificate and Key Files

To enable HTTPS we must obtain the certificate for the current node and the related private key. A recognized Certification Authority (CA) must release these files. 
The server connection is verified by making sure the server's certificate contains the right hostname and verifies successfully using the cert store.

The following is a typical set of files related received after the Certificate Security Request is sent to the CA:
<pre>
intermediate-root.cer
<hostname>.med.cornell.edu.cer
<hostname>.med.cornell.edu.csr
<hostname>.med.cornell.edu.key
</pre>
A passphrase to access to the key must be also obtained from the CA.

### Step 2. Concatenate the certificates

Build the certificate chain by concatenating the host and intermediate certificates:

````bash
svcusr@machine $ cat <hostname>.med.cornell.edu.cer \
 intermediate-root.cer > import.cer
````
If there are more *.cer files, concatenate all of them.

### Step 3. Create the PKCS12 keystore

Convert the private key and the certificate chain into a PKCS12 keystore:

````bash
svcusr@machine $ openssl pkcs12 -export -in import.cer \
-inkey <hostname>.med.cornell.edu.key \
-name kafka-dispatcher-certificate > host.p12

Enter pass phrase for <hostname>.med.cornell.edu.key:
Enter Export Password:
Verifying - Enter Export Password:

````
The command requests to:
1. insert passphrase for the private key. It must be obtained along with the key and certificates from the CA
2. set the password for the PKCS12 keystore. It will be used in step 4.

### Step 4. Import the PKCS12 keystore into the KD keystore

````bash
svcusr@machine $ keytool -importkeystore -srckeystore host.p12 \
-destkeystore path/kd-store.keys -deststorepass <my password> -srcstoretype pkcs12 \
-alias kafka-dispatcher-certificate
````
Enter source keystore password:  
Where: 

 * _my password_ is the password to protect the keystore. It will be used to configure the instance.
 * <code>path/kd-store.keys</code> is the location and name of the file containing the keystore. 
It will mounted on the docker container to start the instance.
* the requested source keystore password is the one set on <code>host.p12</code> in step 3.

### Step 5. Verify the keystore

The following command prints out the content of the keystore. It should be used to check if the certificates have been imported correctly.

````bash
svcusr@machine $ keytool -list -v -keystore path/kd-store.keys -alias kafka-dispatcher-certificate

Enter keystore password:  
Alias name: kafka-dispatcher-certificate
Creation date: Feb 9, 2021
Entry type: PrivateKeyEntry
Certificate chain length: 4
Certificate[1]:
Owner: CN=<hostname>.cornell.edu, OU=Information Technologies and Services, O=Weill Cornell Medical College, STREET=1300 York Avenue, L=New York, ST=New York, OID.2.5.4.17=10065, C=US
Issuer: CN=InCommon RSA Server CA, OU=InCommon, O=Internet2, L=Ann Arbor, ST=MI, C=US
...
Certificate[2]:
...
Certificate[3]:
...
Certificate[4]:
...
````

## Self-signed Certificates
Self-signed certificates are not issued by a recognized Certification Authority. 
They should be used only in one of these cases:
* testing purposes
* the docker container is started with the --net=host option
* a CA-signed certificate cannot be obtained for the node

If we are in one of these situations, it’s acceptable to generate and use self-signed certificates.  

The following command generates the host certificate, key and stores them in the keystore.

````bash
keytool -genkey -alias kafka-dispatcher-certificate \
  -storetype PKCS12 -keyalg RSA -keysize 2048 \
  -keystore path/kd-store.keys \
  -validity 3650 \
  -keypass <my password> -storepass <my password> \
  -dname "cn=Kafka Dispatcher, ou=EIPM, o=Weill Cornell Medicine, c=US"
````
Where: 

* _my password_ is the password to protect the keystore. It will be used to configure the instance.
* <code>path/kd-store.keys</code> is the location and name of the file containing the keystore. It will mounted on the docker container to start the instance.

## Configure the dispatcher instance with keystore
First, we have to grant permission to read the keystore file to all users with:
````bash
svcusr@machine $ chmod a+r path/kd-store.keys
````
### With Docker
The server section of <code>application.yml</code> must be changed as follows:
````json
server:
  port: 8443
  servlet:
    context-path: /dispatcher
  ssl:
    key-store: /config/kd-store.keys
    key-store-password: <my password>
    keyStoreType: jks
    keyAlias: kafka-dispatcher-certificate
````
Where:
* _my password_ must be replaced with the password set on the keystore

Then, the <code>docker run</code> command must be modified to add the following option:
````bash
-v path/kd-store.keys:/config/kd-store.keys
````
### Without Docker
The server section of <code>application.yml</code> must be changed as follows:
````json
server:
  port: 8443
  servlet:
    context-path: /dispatcher
  ssl:
    key-store: path/kd-store.keys
    key-store-password: <my password>
    keyStoreType: jks
    keyAlias: kafka-dispatcher-certificate
````
Where:
* _my password_ must be replaced with the password set on the keystore
* _path/kd-store.keys_ is the absolute path of the keystore
