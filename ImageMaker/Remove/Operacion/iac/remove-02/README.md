Instalacion

history
    3  sudo apt update
    5  sudo add-apt-repository ppa:ondrej/php
    6  sudo apt-get install apache2 libapache2-mod-php mariadb-client mariadb-server php7.4-bcmath php7.4-cli php7.4-common php7.4-curl php7.4-gd php7.4-json php7.4-ldap php7.4-mbstring php7.4-mysql php7.4-opcache php7.4-pgsql php7.4-soap php7.4-xml php7.4-zip php-imagick php-memcached memcached unzip moreutils
    7  php -v
   13  sudo vi /etc/php/8.2/mods-available/i-doit.ini
   14  sudo phpenmod i-doit
   15  sudo phpenmod memcached
   16  sudo systemctl restart apache2.service
   17  sudo a2dissite 000-default
   18  sudo vi /etc/apache2/sites-available/i-doit.conf
   19  sudo chown www-data:www-data -R /var/www/html/
   20  sudo chmod 755 /var/log/apache2
   21  sudo chmod 664 /var/log/apache2/*
   22  sudo a2ensite i-doit
   23  sudo a2enmod rewrite
   24  sudo systemctl restart apache2.service
   26  sudo mysql_secure_installation
   30  sudo mysql -uroot
   31  mysql -uroot -p -e"SET GLOBAL innodb_fast_shutdown = 0"
   32  sudo systemctl stop mysql.service
   33  sudo mv /var/lib/mysql/ib_logfile[01] /tmp
   34  sudo vi /etc/mysql/mariadb.conf.d/99-i-doit.cnf
   35  sudo systemctl start mysql.service
   36  wget https://wwwidoitorg.wpenginepowered.com/wp-content/uploads/2023/09/idoit-open-26.zip
   37  sudo mkdir /var/www/html/i-doit
   38  sudo cp idoit-open-26.zip /var/www/html/i-doit/
   39  cd /var/www/html/i-doit/
   40  sudo unzip idoit-open-26.zip 
   41  sudo rm idoit-open-26.zip 
   42  sudo chown www-data:www-data -R .
   43  sudo find . -type d -name \* -exec chmod 775 {} \;
   44  sudo find . -type f -exec chmod 664 {} \;
   76  sudo apt install php8.2-mysql php8.2-xml php8.2-common php8.2-mbstring
   78  sudo apt-get install libgd-dev
   80  sudo apt install php-gd
   82  sudo apt install php-curl
   83  sudo systemctl restart apache2
  