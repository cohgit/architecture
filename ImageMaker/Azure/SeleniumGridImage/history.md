$sudo apt-get -y update
$sudo apt-get -y install ca-certificates curl gnupg lsb-release
$mkdir -m 0755 -p /etc/apt/keyrings
$sudo mkdir -m 0755 -p /etc/apt/keyrings
$sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
$echo 'deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable' | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
$chmod a+r /etc/apt/keyrings/docker.gpg
$sudo echo 'deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable' | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
$sudo chmod a+r /etc/apt/keyrings/docker.gpg
$sudo apt-get update -y
$echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
$sudo apt-get update -y
$sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
$mkdir docker
$cd docker
copia archivo docker compose.yml
copia archivo config.toml
$sudo docker compose -f docker-compose-v3-dynamic-grid.yml create
$sudo mkdir /tmp/videos
$sudo docker compose -f docker-compose-v3-dynamic-grid.yml start
$sudo apt-get install nginx
copia nginx config
$cd /var/www/html/
$sudo ln -s /tmp/videos .
$sudo chown www-data:www-data -R videos
$sudo service nginx restart
   