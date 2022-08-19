Acuerdo de Arquitcetura

## Definción Arquitectura
Los proyectos deben aderirse a SSO toBe AWS Cloud

## Requerimiento proyecto
Sitio público sin identificación de cliented ni login

## Integraciones de servicios
Carlos Ramirez indica que no hay impacto que se puede usar token de anonimo

## POC validar token anonimo con los TMF del proyecto
Escenario : Generar Token anónimo y usar los TMF con ese token
Objetivo es validar no impacto en los TMF y validación del token sea correcta.

Dependiendo del resultado vemos desiciones con los GAP's.


Arquitectura Técnica
1. Uso publico de TMF sin usuario autenticado (sitio público).
1. Integración con devetel para los botones de pago que el tiene (documentación)
1. Buscar token en el onPremise esto cambiaría por el SSO Cloud si es que funciona con la POC, sino funcuona solicitaríamos token onpremise y quedamos con el GAP SSO Cloud.
1. Estará en la cuenta de AWS de E-Commerce.