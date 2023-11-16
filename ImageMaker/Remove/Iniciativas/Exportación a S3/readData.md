Ejeucción de PHP con Docker
docker run -it --rm --name my-running-script -v "$PWD":/usr/src/myapp -w /usr/src/myapp php:8.2-cli php readData.php

docker run -d -p 80:80 --name my-apache-php-app -v "$PWD":/var/www/html php:7.2-apache

dev_access_key="AKIATV2KEPWAAKHQDIIX"
dev_secret_key="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"