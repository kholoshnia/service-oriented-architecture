# Service oriented architecture

Service oriented architecture lab.

### Generate SSL files

```shell
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout $SSL_DOMAIN.key -out $SSL_DOMAIN.crt -subj "/C=RU/CN=$SSL_DOMAIN"
openssl pkcs12 -export -in $SSL_DOMAIN.crt -inkey $SSL_DOMAIN.key -name $SSL_DOMAIN -out $SSL_DOMAIN.p12
cat $SSL_DOMAIN.crt $SSL_DOMAIN.key > $SSL_DOMAIN.pem
```
