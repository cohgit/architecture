services:
    acm-manager:
      image: nginx:latest    
      ports:
        - "8080:80"
      volumes:
        - ./acm-manager/:/var/www/html
        - ./acm-manager/nginx.conf:/etc/nginx/conf.d/default.conf
        - ./acm-manager/fastcgi-php.conf:/etc/nginx/snippets/fastcgi-php.conf
      env_file:
        - ./.env
      depends_on:
        - php-fpm
    php-fpm:
      build:
        context: ./acm-manager/
        dockerfile: Dockerfile
      env_file:
        - ./.env
      volumes:
        - ./acm-manager/:/var/www/html
      depends_on:
        mysql :
          condition : service_healthy
    mysql:
      image: mysql:5.7
      command: mysqld --general-log=1 --general-log-file=/var/log/mysql/general-log.log
      ports:
        - '3306:3306'
      container_name: mysql
      volumes:
       - ./install_dump_p:/docker-entrypoint-initdb.d
       - /etc/timezone:/etc/timezone:ro
       - /etc/localtime:/etc/localtime:ro
      env_file:
       - .env
      healthcheck:
        test: "cat /var/log/mysql/general-log.log | grep \"root@localhost on  using Socket\""
        interval: 1s
        retries: 120
