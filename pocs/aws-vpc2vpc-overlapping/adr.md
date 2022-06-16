# AWS VPC to VPC with overlapping
## Escenario
Comunicación entre MiWom con TMF y con ECommerce en AWS.

## Situación Actual
Las redes están con overlapping

## Solución 
La solución es redefinir las IP's de Redes VPC para poder realizar el peering, sin embargo se necesita una solución táctica para mitigar.

## Mitigación y POC
De acuerdo a lo indicado por AWS la solución es [AWS PrivateLink](https://aws.amazon.com/es/privatelink)

Presentación de PrivateLink [AWS PrivateLink First Call Deck](AWS&#32;PrivateLink&#32;First&#32;Call&#32;Deck.pdf)

