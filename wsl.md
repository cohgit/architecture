
sudo bash -c 'echo "[network]" > /etc/wsl.conf'
sudo bash -c 'echo "generateResolvConf = false" >> /etc/wsl.conf'
sudo unlink /etc/resolv.conf
sudo bash -c 'echo "nameserver 10.120.147.80" > /etc/resolv.conf'
sudo bash -c 'echo "nameserver 10.120.157.73" > /etc/resolv.conf'
sudo chattr -f +i /etc/resolv.conf


sudo ip addr add 192.168.137.10/24 dev eth0
sudo ip route delete default
sudo ip route add default via 192.168.137.1

 sudo ip link set eth0 down

export http_proxy=http://10.120.136.40:8080/
export https_proxy=http://10.120.136.40:8080/