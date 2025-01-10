import openai

# Configura tu clave de API
openai.api_key = "tu_api_key_aquí"

# Ruta al archivo de audio
audio_file_path = "ruta_a_tu_archivo_de_audio.mp3"

# Abre el archivo de audio
with open(audio_file_path, "rb") as audio_file:
    # Llama a la API de transcripción
    response = openai.Audio.transcribe("whisper-1", audio_file)

# Imprime el texto transcrito
print("Transcripción:")
print(response["text"])


