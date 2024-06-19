import pandas as pd

# Load the provided template CSV file to understand the required format
template_path = 'PracticeTestBulkQuestionUploadTemplate_v2.csv'
template_df = pd.read_csv(template_path)

# Extract the column names from the template
columns = template_df.columns

# Create a DataFrame with the provided quiz data
quiz_data = {
    "Question": [
        "¿Qué es la computación en la nube?", 
        "Menciona tres proveedores principales de servicios en la nube.", 
        "Define IaaS, PaaS y SaaS.", 
        "¿Qué es el escalado vertical y horizontal en la nube?", 
        "¿Cuál es la diferencia entre almacenamiento en bloque y almacenamiento de archivos?", 
        "¿Qué es una máquina virtual?", 
        "¿Qué significa el término 'multi-tenancy'?", 
        "¿Qué es la región en el contexto de la nube?", 
        "Menciona una ventaja de usar servicios en la nube.", 
        "Define la virtualización.", 
        "¿Qué es un contenedor?", 
        "¿Cuál es el propósito de una red privada virtual (VPN)?", 
        "¿Qué es un balanceador de carga?", 
        "¿Qué es una API?", 
        "¿Qué significa SLA?", 
        "Menciona una herramienta de gestión de la nube.", 
        "Define 'backup' y 'recovery'.", 
        "¿Qué es DevOps?", 
        "Menciona una medida de seguridad en la nube.", 
        "¿Qué es el almacenamiento en caché?"
    ],
    "Question Type": [
        "Multiple Choice"] * 20,
    "Answer Option 1": [
        "Un tipo de almacenamiento físico en discos duros.", 
        "Google Cloud Platform, AWS, Azure.", 
        "Instrucciones, Procesos, Servicios.", 
        "Mejorar el rendimiento del hardware de una sola máquina.", 
        "El almacenamiento en bloque se utiliza para bases de datos, el de archivos para documentos.", 
        "Un dispositivo físico que ejecuta múltiples sistemas operativos.", 
        "Un único usuario por servidor.", 
        "Un área geográfica con uno o más centros de datos.", 
        "Alta latencia.", 
        "La creación de una versión virtual de algo, como hardware o software.", 
        "Un archivo comprimido.", 
        "Crear una red pública.", 
        "Un dispositivo que almacena datos.", 
        "Una interfaz de usuario.", 
        "Acceso de baja latencia.", 
        "Microsoft Word.", 
        "Proceso de migración de datos.", 
        "Una metodología para integrar desarrollo y operaciones.", 
        "Ignorar el cifrado de datos.", 
        "Almacenamiento permanente de datos."
    ],
    "Explanation 1": [""] * 20,
    "Answer Option 2": [
        "La entrega de servicios de computación a través de internet.", 
        "Dropbox, iCloud, OneDrive.", 
        "Infraestructura, Plataforma, Software.", 
        "Añadir más máquinas para distribuir la carga.", 
        "El almacenamiento en bloque es más lento que el de archivos.", 
        "Un software que emula un sistema operativo dentro de otro.", 
        "Varios usuarios compartiendo los mismos recursos físicos o lógicos.", 
        "Un servidor específico en una ubicación remota.", 
        "Alta disponibilidad y escalabilidad.", 
        "El proceso de almacenamiento de datos en la nube.", 
        "Un método de empaquetar una aplicación y sus dependencias.", 
        "Proporcionar una conexión segura a través de una red no segura.", 
        "Un sistema que distribuye el tráfico de red entre varios servidores.", 
        "Un sistema de autenticación.", 
        "Acuerdo de nivel de servicio.", 
        "Google Drive.", 
        "Proceso de copiar y restaurar datos.", 
        "Un lenguaje de programación.", 
        "Implementar autenticación multifactor.", 
        "Almacenamiento temporal de datos para un acceso más rápido."
    ],
    "Explanation 2": [""] * 20,
    "Answer Option 3": [
        "Un lenguaje de programación específico para redes.", 
        "Slack, Trello, Jira.", 
        "Instalación, Aplicación, Sistema.", 
        "Mejorar el rendimiento del hardware y añadir más máquinas para distribuir la carga.", 
        "El almacenamiento de archivos se utiliza solo para multimedia.", 
        "Un lenguaje de programación para la nube.", 
        "Múltiples servidores dedicados a un solo usuario.", 
        "Un tipo de red local.", 
        "Requiere mucho hardware físico.", 
        "La transferencia de datos entre redes.", 
        "Una máquina virtual ligera.", 
        "Almacenar datos en una nube pública.", 
        "Un programa para la creación de contenedores.", 
        "Un conjunto de reglas para que las aplicaciones se comuniquen entre sí.", 
        "Aplicación de seguridad en la nube.", 
        "AWS Management Console.", 
        "Proceso de creación de redes.", 
        "Un tipo de base de datos.", 
        "Compartir contraseñas.", 
        "Almacenamiento de datos en la nube."
    ],
    "Explanation 3": [""] * 20,
    "Answer Option 4": [
        "Un dispositivo que almacena datos en la nube.", 
        "GitHub, Bitbucket, GitLab.", 
        "Integración, Asimilación, Suministro.", 
        "Reducir el número de usuarios.", 
        "No hay diferencias significativas entre ambos.", 
        "Un sistema operativo independiente.", 
        "Una configuración de red única.", 
        "Un sistema de almacenamiento específico.", 
        "Menor flexibilidad.", 
        "La codificación de datos para seguridad.", 
        "Un tipo de base de datos.", 
        "Crear un sistema operativo nuevo.", 
        "Un tipo de firewall.", 
        "Un dispositivo de red.", 
        "Autenticación de usuario.", 
        "Slack.", 
        "Proceso de instalación de software.", 
        "Un sistema operativo.", 
        "Utilizar redes públicas sin protección.", 
        "Proceso de cifrado de datos."
    ],
    "Explanation 4": [""] * 20,
    "Answer Option 5": [""] * 20,
    "Explanation 5": [""] * 20,
    "Answer Option 6": [""] * 20,
    "Explanation 6": [""] * 20,
    "Correct Answers": [
        "La entrega de servicios de computación a través de internet.", 
        "Google Cloud Platform, AWS, Azure.", 
        "Infraestructura, Plataforma, Software.", 
        "Mejorar el rendimiento del hardware y añadir más máquinas para distribuir la carga.", 
        "El almacenamiento en bloque se utiliza para bases de datos, el de archivos para documentos.", 
        "Un software que emula un sistema operativo dentro de otro.", 
        "Varios usuarios compartiendo los mismos recursos físicos o lógicos.", 
        "Un área geográfica con uno o más centros de datos.", 
        "Alta disponibilidad y escalabilidad.", 
        "La creación de una versión virtual de algo, como hardware o software.", 
        "Un método de empaquetar una aplicación y sus dependencias.", 
        "Proporcionar una conexión segura a través de una red no segura.", 
        "Un sistema que distribuye el tráfico de red entre varios servidores.", 
        "Un conjunto de reglas para que las aplicaciones se comuniquen entre sí.", 
        "Acuerdo de nivel de servicio.", 
        "AWS Management Console.", 
        "Proceso de copiar y restaurar datos.", 
        "Una metodología para integrar desarrollo y operaciones.", 
        "Implementar autenticación multifactor.", 
        "Almacenamiento temporal de datos para un acceso más rápido."
    ],
    "Overall Explanation": [
        "La computación en la nube ofrece servicios a través de internet.", 
        "Los principales proveedores son Google Cloud, AWS y Azure.", 
        "IaaS, PaaS y SaaS son modelos de servicios en la nube.", 
        "El escalado vertical mejora el hardware, el horizontal añade máquinas.", 
        "El almacenamiento en bloque es para bases de datos, el de archivos para documentos.", 
        "Una máquina virtual es un software que emula un sistema operativo dentro de otro.", 
        "Multi-tenancy permite que varios usuarios compartan recursos físicos o lógicos.", 
        "Una región es un área geográfica con uno o más centros de datos.", 
        "La alta disponibilidad y escalabilidad son ventajas clave de la nube.", 
        "La virtualización crea versiones virtuales de hardware o software.", 
        "Un contenedor empaqueta una aplicación y sus dependencias.", 
        "Una VPN proporciona una conexión segura a través de una red no segura.", 
        "Un balanceador de carga distribuye el tráfico de red entre varios servidores.", 
        "Una API permite la comunicación entre aplicaciones a través de un conjunto de reglas.", 
        "SLA significa acuerdo de nivel de servicio.", 
        "AWS Management Console es una herramienta de gestión de la nube.", 
        "Backup y recovery son procesos de copiar y restaurar datos.", 
        "DevOps integra desarrollo y operaciones en una metodología.", 
        "La autenticación multifactor es una medida de seguridad en la nube.", 
        "El almacenamiento en caché guarda datos temporalmente para acceso rápido."
    ],
    "Domain": ["General Cloud"] * 20
}

# Convert the quiz data into a DataFrame
quiz_df = pd.DataFrame(quiz_data, columns=columns)

# Save the quiz DataFrame to a new CSV file
output_path = 'GeneratedQuiz_Udemy.csv'
quiz_df.to_csv(output_path, index=False)

output_path
