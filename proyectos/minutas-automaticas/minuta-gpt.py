import subprocess
import requests
import os
import openai
from openai import OpenAI


# Lista de archivos
archivos = [
    "./grabaciones/Entrevista Técnica - Alfredo López Aránguiz.m4a",
    "./grabaciones/Entrevista Técnica - Alvaro Trewhela Pfeifer.m4a",
    "./grabaciones/Entrevista Técnica - Fabián Tello Díaz.m4a",
    "./grabaciones/Entrevista Técnica - José Miguel Herrera Vásquez.m4a"
]

# Función para comprimir los archivos
def comprimir_archivos(archivos):
    for archivo in archivos:
        archivo_salida = archivo.replace(".m4a", "_comprimido.m4a")

        # Verificar si el archivo de salida ya existe
        if os.path.exists(archivo_salida):
            print(f"El archivo {archivo_salida} ya existe. Omitiendo compresión.")
            continue  # Omitir este archivo y pasar al siguiente

         # Ejecutar el comando ffmpeg para comprimir
        comando_ffmpeg = [
            "ffmpeg", "-i", archivo, "-b:a", "64k", archivo_salida
        ]
        try:
            subprocess.run(comando_ffmpeg, check=True)
            print(f"Archivo comprimido: {archivo_salida}")
        except subprocess.CalledProcessError as e:
            print(f"Error al comprimir {archivo}: {e}")

# Función para transcribir usando la API oficial de OpenAI
def transcribir_archivos(archivos):
    for archivo in archivos:
        archivo_salida = archivo.replace(".m4a", "_comprimido.m4a")
        transcripcion_salida = archivo.replace(".m4a", "_transcripcion.txt")

         # Verificar si el archivo de salida ya existe
        if os.path.exists(transcripcion_salida):
            print(f"El archivo {transcripcion_salida} ya existe. Omitiendo transcripción.")
            continue  # Omitir este archivo y pasar al siguiente
        
        try:
            print(f"Transcribiendo {archivo_salida}...")

            audio_file= open(archivo_salida, "rb")
            client = OpenAI()
            transcription = client.audio.transcriptions.create(
                model="whisper-1", 
                file=audio_file
            )

            # Guardar la transcripción en un archivo
            with open(transcripcion_salida, "w") as f:
                f.write(transcription.text)
            print(f"Transcripción guardada: {transcripcion_salida}")
        except Exception as e:
            print(f"Error al transcribir {archivo_salida}: {e}")


def evalua_entrevista(archivos):
    client = OpenAI()
    for archivo in archivos:
        transcripcion_salida = archivo.replace(".m4a", "_transcripcion.txt")

         # Verificar si el archivo de salida ya existe
        if os.path.exists(transcripcion_salida):
            with open(transcripcion_salida, "r", encoding='utf-8') as f:
                transcript_content = f.read()
            
                #print(f"El archivo {transcripcion_salida} se adjuntará")
                #file = client.files.create( file=open(transcripcion_salida, "rb"), purpose="assistants" ) 
                #print(f"Archivo adjunto {file.id}")
                print(f"Solicitando evaluación {transcripcion_salida}")
                completion = client.chat.completions.create(
                    model="gpt-4o",
                    messages=[
                        {"role": "system", "content": "Tu eres un entrevistador técnico para perfil Data Engineer."},
                        {
                            "role": "user",
                            "content": f"Segun esta entrevista ( texto trasncrito  {transcript_content}) entre un techmaster (entrevistador) y un postulante (entrevistado) \
                                        \
                                        Evalua estos items : \
                                            -Experiencia en GCP (excluyente) \
                                            -Experiencia en Python \
                                            -Experiencia en POO \
                                            -Experiencia en SQL (optimización y consultas avanzadas) \
                                            -Experiencia en modelamiento, integración y procesamiento de datos. \
                                            -Experiencia en BigQuery \
                                            -Experiencia en ETL \
                                            -Experiencia en Apache Airflow \
                                            -Experiencia en Git \
                                            -Experiencia en Dataflow \
                                            -Conocimientos en Terraform \
                                            -Conocimientos en Jenkins \
                                            -Conocimientos en Matemáticas \
                                            -Conocimientos en CI/CD \
                                            \
                                            con estos criterios de eveluacion: \
                                            \
                                            una nota 3 Posee conocimientos/nociones básicas en el uso de la herramienta. \
                                                Capaz de realizar tareas simples y seguir instrucciones básicas. \
                                                Tiene experiencia limitada en la aplicación de la herramienta en entornos reales (académicos y laborales) \
                                                Ha trabajado en proyectos asumiendo tareas simples o de baja complejidad y/o con acompañamiento constante \
                                                Comprende los conceptos básicos de los procesos y metodologías relacionados con la herramienta. \
                                                Puede seguir procesos establecidos con supervisión constante \
                                                Está familiarizado con los estándares básicos asociados con la herramienta y sigue las mejores prácticas. \
                                            una nota 4 Tiene conocimientos/nociones más profundas y es capaz de reconocer e incluso utilizar características avanzadas de la herramienta. \
                                                Ha trabajado en distintos o variados proyectos y tiene experiencia práctica en aplicar la herramienta en uno o más contextos. \
                                                Tiene experiencia en la aplicación de la herramienta en entornos reales (académicos y laborales) \
                                                Comprende los procesos y metodologías relacionados con la herramienta y puede aplicarlos en proyectos más complejos con supervisión constante \
                                                Sigue los estándares de aplicación y está al tanto de las últimas tendencias y actualizaciones en la herramienta y sus estándares asociados. \
                                            una nota 6 Conoce de la herramienta y es capaz de resolver problemas técnicos de manera independiente. \
                                                Tiene experiencia significativa en la aplicación de la herramienta en diferentes proyectos y contextos \
                                                Puede asumir tareas transversales y de mayor criticidad \
                                                Entiende los procesos y metodologías de desarrollo de software y puede contribuir a su mejora. \
                                                Contribuye al desarrollo y mantenimiento de estándares internos y sigue las mejores prácticas de la industria \
                                            una nota 7 Tiene conocimientos sólidos en el uso de la herramienta y puede resolver problemas complejos de manera efectiva. \
                                                Tiene una experiencia significativa en la aplicación de la herramienta, he incluso puede haber liderado fases o proyectos utilizando la herramienta. \
                                                Contribuye activamente al desarrollo y mejora de los procesos y metodologías dentro del equipo y la organización. \
                                                Puede llegar a ser un referente técnico dentro del equipo en cuanto a estándares y mejores prácticas relacionadas con uno o varios aspectos de la herramienta y su campo de aplicación. \
                                                Apoya en la definición y estándares que signifiquen oportunidades de mejora en el proceso o el equipo \
                                            una nota 8  Tiene conocimientos sólidos y es reconocido en su equipo de trabajo en el uso de la herramienta. Puede resolver problemas de alta complejidad \
                                                Tiene una amplia experiencia en la aplicación de la herramienta, llegando incluso a liderar equipos y proyectos en el uso de la herramienta. \
                                                Define y establece los estándares de procesos y metodologías en la organización y contribuye a la mejora de procesos y equipos \
                                                Define y promueve estándares a nivel de la organización y es un líder reconocido en el campo de la herramienta y sus estándares asociados.\
                                                \
                                                y agrega un comentario a cada nota \
                                            ",
                            #"attachments": [
                            #    {
                            #        "file_id": file.id
                            #    }   
                            #]
                        }
                    ]
                    
                )
                print(completion.choices[0].message.content)
            
# Ejecutar las funciones
comprimir_archivos(archivos)
transcribir_archivos(archivos)
evalua_entrevista(archivos)
