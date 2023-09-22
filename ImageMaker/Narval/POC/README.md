## POC de WP con SSO
El escenario consiste en tener un wordpress con sso keycloak

### Pasos con docker compose
1. Levanta docker-compose con mariadb, keycloak y wordpress.
1. Configura los nombres de host intranet.narval.es y sso.narval.es apuntando a localhost.
1. Ingresa a wordpress http://intranet.narval.es:8080/ para configurar el sitio intranet.
1. Ingresa a keycloak http://sso.narval.es:8081/auth/ para crear realm Narval, crea un usuario y define una credencial como lo indica el siguiente tutorial https://www.keycloak.org/getting-started/getting-started-docker.
1. Seguir los siguientes pasos para la configuración del SSO con keycloak https://www.youtube.com/watch?v=rPG8-lIIHHc
1. Ingrear a wordpress seleccionando el login sso.