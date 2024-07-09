# VPN WireGard
Referencia : https://www.wireguard.com/quickstart/

Estos son los pasos usados en Windows 10 Pro - WSL2 "Ubuntu 22.04.3.LTS"

## Instalacion
```console
$ sudo apt install wireguard
```

## Agrega nueva interfaz de red
```console
$ sudo ip link show
$ sudo ip link add dev wg0 type wireguard
```

## genera el strip
```console
$ sudo wg-quick strip ./cesar.conf > ./stripped_cesar.conf
```

## Configura con archivo strip generado
```console
$ sudo wg setconf wg0 stripped_cesar.conf
```

## levanta la inteerfaz de comunicación
```console
$ sudo ip link set up dev wg0
```

## muestra estado de la VPN
```console
$ sudo wg

interface: wg0
  public key: +Z4dfwT8ju6G1U7rIEPR6UE2kV3bJWkm+llEzzICKG8=
  private key: (hidden)
  listening port: 42449

peer: H99/fGQoGJzpA8YtjnigFPawUEqOODrp22Usf8bghFk=
  preshared key: (hidden)
  endpoint: 170.187.193.19:51820
  allowed ips: 0.0.0.0/0, ::/0
```