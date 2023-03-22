
# SSL CERTIFICATE

> (this is a private README to remember me how to create a valid ssl certificate)

In order to create a new valid ssl certificate the things to do are:
- Go to zeroSsl and login
- Create a new certificate or renew an existing one for myportfolio-backend.it (domain hosted in register.it account google)
- download the zip folder containing the certificate.crt, private.key and the ca_bundle.crt and extract the three file into the folder openssl/bin
- follow the guide at https://medium.com/@JavaArchitect/configure-ssl-certificate-with-spring-boot-b707a6055f3 to convert those three files into a .p12 and .jks
- if the guide is not online anymore, these are the commands:
	- **Move to openssl folder**
		Be sure to use the 1.1 version
		Open a terminal as administrator
		cd /path/to/openssl/bin
		.\openssl pkcs12 -export -in certificate.crt -inkey private.key -name springboot -out springboot.p12
		use a password
	- **move to Java**
	  **create a new keystore .jks**
	  	Be sure to be in the 1.8 jdk bin folder
		keytool -importkeystore -deststorepass password -destkeystore springboot.jks -srckeystore springboot.p12 -srcstoretype PKCS12
	- **import intermediate certificate**
		keytool -import -alias bundle -trustcacerts -file ca_bundle.crt -keystore springboot.jks

**Thigs to make sure of:**
 - myportfolio-backend.it domain has to point to the public IP address of our personal PC
 - modem configuration of port forwarding to forward requests from myportfolio-backend.it from port 80 to 443 to internal port 8443
