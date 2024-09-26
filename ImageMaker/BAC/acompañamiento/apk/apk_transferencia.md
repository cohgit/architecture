# Compilación y Distribución de Aplicaciones Moviles (APK)

Se necesita poder resolver la transferencias de archivos APK para la distribución desde un contexto de imagemaker
hacia un contexto de BAC.


## Contexto
Las aplicaciones moviles requiren de un entorno de compilación, simulación con bastantes recursos
es por ello que pueden surgir diferentes problematicas y retrazos de desarrollo dado esto.

Un factor importande con el cliente BAC es que tiene una restricción  a nivel de redes
importante es por ello que no todas las formas de transferencia de archivos están permitidas.

Además es importante señalar que no está el respositorio Azure DevOps BAC disponible para hacer uso
de el para el control de versiones de el desarrollo y tampoco se puede acceder externamente.


## Escenario : Maker necesita distribuir su APK para BAC, desde un computador externo.

principalmente es resolver la distribución de una aplicación desde el desarrollo hasta el dispositivo.

## Ostáculos
1. Correo externo de BAC  no permite comunicación con imagemaker.com, si se envia un mail al maker este rebota.
1. Uso de OneDrive es posible solo en redes BAC, desde el exterior está bloqueado al hacer login si no estas dentro de la red de BAC no te permite.
1. Por VPN, en este caso para el maker no puede instalar VPN BAC en computador externo para poder acceder  la red y poder copiar, su perfil no tiene ese acceso.
1. Puertos USB/Bluetooth para transferencias de archivos están bloqueados.
1. Acceso a Google está restringido, esto genera un problema ya que al estar conecatdo con BAC pierdes comunicacion con imagemaker (Slack, Mail, WhatsApp desde el Computador conectado).
1. otros caminos se copia también fallaron.

## Solución definitiva
Activar un proceso de Integración Continua es la solución:

1. Contar con Repositorio Azure DevOps Git de BAC
1. Crear Pipelines de compilación, pruebas y distribuacion Automaticos con Azure DevOps Pipelines de BAC
1. La ejecución de la compilación debe darse en el contexto de BAC y no en el computador (aunque esto no exime que el maker deba tener una maquina adecuada para desarrollar aplicaciones moviles).
1. Usar un Store de Aplicaciones como Apple Store, Google Play para la publicacion, pruebas y analisis del aplicativo movile en un contexto empresarial para el gobierno de estas aplicaciones.
1. Se identificó una solucion de almacenamiento de aplicaciones tipo store de Miscrosoft llamado AppCenter que permite el almacenamiento y distribucion de aplicaciones, este se terminará en 2025 por tanto hay que ir al punto de Google Play / Apple Store u otro que permita el gobierno de aplicaciones moviles de BAC.

## Solución de comunicación

En el contexto de la aplicación AppCenter que permite Mantener, Versionar y Dsitribuir APK es que :

1. Se pudo crear una organizacion de aplicaciones lamada  Apks-BAC con la cuenta de Fernando B. fernando.betancourt@cr.asesorexternoca.com
1. Se agrego mi usuario microsoft del Active Directory de Azure DevOps Imagemaker "cesar.ogalde@imagemakerit.onmicrosoft.com"
1. Instalé un cliente de lineas de comando para AppCenter y pude subir el apk, con los pasos más abajo descritos.
1. Fernando desde su cuenta en el compoutador de BAC pudo ver el apk y descargar, además en esta aplicacion indica como debemos usarlo para poder instalar las aplicaciones en la tablet de pruebas a traves de una url publica que el AppCenter destina para instalar en dispositivos moviles.
1. Agregué a Fernando como usuario en Active Directory de Imagemaker "fernando.betancourt@imagemakeraz.onmicrosoft.com".
1. Fernano desde la computadora externa puso subir el apk con su usario de Microsoft de Imaker, logrando poder transferir a BAC el aplicativo.

Con esto Activamos una solucion de Aplication Store para movile que además permite analizar el aplicativo, distribuirlo. Esto permite que Fernando pueda hacer las versiones que estime necesario y distribuir rápidamente al BAC, notificando incluso a quienes estén registrados, como por ejemplo avisarle por mail Luis Navarro que hay una nueva versión para probar y revsiar.

## Siguientes pasos

1. Se activo una capacitación a Fernando de 7 horas vía Udemy de Imagemaker de la solución "Microsoft App Center" de tal manera de aumentar su performance de desarrollo de Fernando B.
1. Validaré el estándar de arquitectura de BAC en el desarrollo de aplicaciones moviles en el contexto de aplicaciones internas, si no hay definición hay que considerar las opciones que indica Microsoft sobre la retiurada de AppCenter https://learn.microsoft.com/es-es/appcenter/retirement.


## Observaciones Generales
1. Solicitar relación de confianza con los dominios de imagemaker.com para la comunicación de correos, y visita a sitios de la compañia.
1. Evaluar Compartir una red segura con BAC y equipo Maker.




## Pasos para subir el APK con lineas de comando :

To upload a compiled APK to App Center, follow these steps:

### Prerequisites:
- Ensure you have an App Center account and a project created.
- Install the **App Center CLI** if you haven’t done so.

```bash
npm install -g appcenter-cli
```

### Steps:

1. **Login to App Center CLI**:
   Open your terminal and log in to App Center.

   ```bash
   appcenter login
   ```

   Follow the instructions to authenticate.

2. **Navigate to the APK location**:
   Move to the directory where your compiled APK is located.

   ```bash
   cd /path/to/your/apk
   ```

3. **Upload the APK to App Center**:
   Use the following command to upload the APK. Replace placeholders with your actual information.

   ```bash
   appcenter distribute release --app "<owner-name>/<app-name>" \
   --group "Testers" --file "your-apk-file.apk" --release-notes "Release notes here"
   ```

   **Parameters**:
   - `<owner-name>/<app-name>`: Your app identifier in App Center.
   - `--group "Testers"`: The distribution group (e.g., testers).
   - `--file "your-apk-file.apk"`: Path to your APK file.
   - `--release-notes "Release notes here"`: Provide any notes for the release.

4. **Verify the Upload**:
   After successful upload, check the release on the App Center dashboard to verify if the APK has been uploaded and distributed.


## Pasos para descragar un apk de appcenter con lineas de comando :
To **download an APK** from App Center, you can either do it manually via the App Center portal or automate it with the **App Center CLI**. Here’s how you can do both:

### Option 1: Manually Download via App Center Portal

1. **Go to App Center**: 
   - Open your browser and log in to [App Center](https://appcenter.ms).
   
2. **Select Your App**: 
   - Navigate to the app you want to download the APK from.

3. **Go to Releases**: 
   - Select the app version you want to download from the "Distribute" tab (look under "Releases").

4. **Download APK**: 
   - Find the APK under the version you’re interested in and click **Download**.

---

### Option 2: Download APK using App Center CLI

1. **Log in to App Center via CLI**:

   If you haven't logged in yet:

   ```bash
   appcenter login
   ```

2. **List Available Releases**:

   Use the following command to list the releases for your app.

   ```bash
   appcenter distribute releases list --app "<owner-name>/<app-name>"
   ```

   Replace `<owner-name>/<app-name>` with your App Center project identifier. This will show you all the releases and their version numbers.

3. **Download APK**:

   To download a specific release, use the release ID from the previous command and specify the output directory for the APK file.

   ```bash
   appcenter distribute release download --app "<owner-name>/<app-name>" --id <release-id> --destination /path/to/download
   ```

   **Parameters**:
   - `<owner-name>/<app-name>`: Your app identifier in App Center.
   - `<release-id>`: The release ID of the version you want to download.
   - `/path/to/download`: The directory where you want to download the APK file.

This will download the APK file from App Center to the specified location on your machine.

## Pasos para distribuir
Para distribuir una aplicación en una **tablet** utilizando **App Center**, tienes varias opciones dependiendo del tipo de distribución y la configuración de la tablet. A continuación te explico las formas más comunes de hacerlo:

### Opción 1: Distribución mediante enlace público (más simple)

1. **Subir el APK a App Center**:
   - Sigue los pasos mencionados anteriormente para subir tu APK a App Center.

2. **Crear un enlace de distribución**:
   - Cuando distribuyas la aplicación en App Center, puedes distribuirla a un grupo específico (e.g., testers) o crear un **enlace público**. Para crear un enlace público:
     - En el panel de App Center, ve a la pestaña de **Distribución**.
     - Selecciona el grupo de distribución y elige la opción para generar un enlace de distribución público.

3. **Compartir el enlace con la tablet**:
   - Envía el enlace por correo electrónico, chat o cualquier otro medio a la tablet donde quieres instalar la app.
   - En la tablet, abre el enlace en el navegador.

4. **Instalar el APK en la tablet**:
   - Al abrir el enlace, App Center te permitirá descargar e instalar la aplicación directamente.
   - Asegúrate de que la tablet tenga habilitada la opción de **instalar aplicaciones de fuentes desconocidas** si no estás usando una tienda oficial como Google Play.

---

### Opción 2: Distribución a dispositivos registrados (Instalación OTA)

Si tienes un grupo de tablets para pruebas o distribución y deseas mayor control, puedes **registrar los dispositivos** en App Center y distribuir de manera controlada:

1. **Obtener el ID del dispositivo (opcional)**:
   Si es necesario, puedes obtener el ID del dispositivo (Device ID o IMEI) para registrar las tablets en App Center.

2. **Distribuir a un grupo específico**:
   - En App Center, crea un grupo de dispositivos registrados o testers.
   - Agrega los correos electrónicos de las personas que usarán las tablets.
   - App Center enviará una notificación con el enlace de instalación a los correos electrónicos registrados.

3. **Instalación OTA** (Over-The-Air):
   - Los usuarios de las tablets recibirán un correo con instrucciones para instalar la app.
   - Solo tienen que abrir el correo en la tablet y seguir el enlace para descargar e instalar el APK directamente.

---

### Opción 3: Instalar manualmente el APK

Si prefieres no usar App Center para la instalación final, también puedes distribuir el APK manualmente:

1. **Descargar el APK desde App Center**:
   - Sigue los pasos anteriores para **descargar el APK** en tu computadora o tablet.

2. **Transferir el APK a la tablet**:
   - Conecta la tablet a la computadora mediante USB y copia el APK.
   - O envíalo por correo o servicios en la nube (Google Drive, Dropbox, etc.) para descargarlo en la tablet.

3. **Instalar el APK manualmente**:
   - En la tablet, navega al archivo APK.
   - Toca el archivo y sigue los pasos para instalarlo. Recuerda activar la opción de **fuentes desconocidas** si es necesario.

### Consideraciones adicionales:
- **Instalación de fuentes desconocidas**: Para instalar el APK en la tablet desde fuera de Google Play, ve a los **Ajustes > Seguridad** y habilita "Instalar aplicaciones de fuentes desconocidas".
- **Actualizaciones**: Si necesitas distribuir actualizaciones frecuentemente, el uso de App Center es ideal ya que facilita la notificación y gestión de versiones nuevas.

Esto debería permitirte distribuir y manejar la instalación de tu aplicación en una tablet de manera eficiente.