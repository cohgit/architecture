asociacioncanalesdemaipo.cl
user: rleon@asocanalesmaipo.cl
pass: encomienda_2023


VPN
host: acm.opengato.cl
user: fergus
pass: Fergus.,3221


 docker run -d   --name test-webserver   -v $(pwd):/var/www/html   -v ~/.ssh:/root/.ssh   -e PHP_EXTENSIONS="apcu pdo_mysql opcache redis zip gd yaml exif xdebug"   -e PHP_EXTENSION_GD=1   -e PHP_EXTENSION_MYSQLI=1   -e APACHE_DOCUMENT_ROOT=application   -e APACHE_RUN_GROUP=www-data   -e APACHE_RUN_USER=www-data   -e PHP_EXTENSION_XDEBUG=1   -p 9090:80   thecodingmachine/php:7.4-v3-apache-node12