curl https://api.openai.com/v1/audio/transcriptions \
-H "Authorization: Bearer $OPENAI_API_KEY" \
-H "Content-Type: multipart/form-data" \
-F file="@/home/cogalde/dev/architecture/proyectos/minutas-automaticas/audio.mp3" \
-F "timestamp_granularities[]=word" \
-F model="whisper-1" \
-F response_format="verbose_json"