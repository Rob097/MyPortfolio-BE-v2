
# SSL CERTIFICATE

> (this is a private README to remember me how to create a valid ssl certificate)

In order to create a new valid ssl certificate the things to do are:
- Go to zeroSsl and login
- Create a new certificate or renew an existing one for secure-backend.my-portfolio.it (or the current domain hosted in register.it account google)
- download the zip folder containing the certificate.crt, private.key and the ca_bundle.crt and copy them in a folder in ubuntu wsl
- open a terminal and execute these three commands:
  - openssl pkcs12 -export -in certificate.crt -inkey private.key -name springboot -out springboot.p12
  - keytool -importkeystore -deststorepass password -destkeystore springboot.jks -srckeystore springboot.p12 -srcstoretype PKCS12
  - keytool -import -alias bundle -trustcacerts -file ca_bundle.crt -keystore springboot.jks
- Copy the springboot.jks into the resources folder of myapigw and push to the github repository and most importantly, on docker hub.
- Connect to the hosting provider (or open the local machine) through ssh.
- Stop the container of myapigw and delete the image
- Run "docker compose up -d"
- If some problems occurs, reboot the VPS and restart the containers.