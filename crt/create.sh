#! /bin/sh

#server part
keytool -genkeypair -alias serverkeys -keyalg RSA -dname "CN=Server,OU=fab,O=tech,L=Grenoble,ST=IS,C=FR" -keypass fablab -keystore server.jks -storepass fablab
keytool -exportcert -alias serverkeys -file serverpub.cer -keystore server.jks -storepass fablab
keytool -importcert -keystore serverpub.jks -alias serverpub -file serverpub.cer -storepass fablab
#client part
keytool -genkeypair -alias clientkeys -keyalg RSA -dname "CN=Client,OU=fab,O=tech,L=Grenoble,ST=IS,C=FR" -keypass fablab -keystore client.jks -storepass fablab
keytool -exportcert -alias clientkeys -file clientpub.cer -keystore client.jks -storepass fablab
keytool -importcert -keystore clientpub.jks -alias clientpub -file clientpub.cer -storepass fablab