## Escenario IA
Actores: TechMaster (persona que entrevista), Postulante 
Perfil: Perfil solicitando 
Skills : Segun la lista que tiene la planilla compartida.
Calificación : segun tabla del excel. 
Accion: Evaluar tecnicamente a un postulante
Input : Grabación de la reunion de evaluación del techmaster
Proceso : Grabacion de la reunion, transcripcion y evaluacion
Output : Calificación segun la tabla de evaluación en la planilla compartida


```console
ffmpeg -i "../grabaciones/Entrevista Técnica - Diego Cabezas Vásquez.m4a" -b:a 64k "../grabaciones/Entrevista Técnica - Diego Cabezas Vásquez_comprimido.m4a"

ffmpeg -i "../grabaciones/Entrevista Técnica - Eduardo Álvarez Rivas.m4a" -b:a 64k "../grabaciones/Entrevista Técnica - Eduardo Álvarez Rivas_comprimido.m4a"

ffmpeg -i "../grabaciones/Entrevista Técnica - Juan David Torres Jimenez.m4a" -b:a 64k "../grabaciones/Entrevista Técnica - Juan David Torres Jimenez_comprimido.m4a"

```

```console
curl --request POST --url https://api.openai.com/v1/audio/transcriptions --header "Authorization: Bearer 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" --header 'Content-Type: multipart/form-data' --form "file=@/home/cogalde/dev/architecture/proyectos/minutas-automaticas/grabaciones/Entrevista Técnica - Diego Cabezas Vásquez_comprimido.m4a" --form model=whisper-1 --form response_format=text > Entrevista_Técnica_Diego_Cabezas_Vasquez_transcripcion.txt

curl --request POST --url https://api.openai.com/v1/audio/transcriptions --header "Authorization: Bearer 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" --header 'Content-Type: multipart/form-data' --form "file=@/home/cogalde/dev/architecture/proyectos/minutas-automaticas/grabaciones/Entrevista Técnica - Eduardo Álvarez Rivas_comprimido.m4a" --form model=whisper-1 --form response_format=text > Entrevista_Técnica_Eduardo_Álvarez_Rivas_transcripcion.txt

curl --request POST --url https://api.openai.com/v1/audio/transcriptions --header "Authorization: Bearer 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" --header 'Content-Type: multipart/form-data' --form "file=@/home/cogalde/dev/architecture/proyectos/minutas-automaticas/grabaciones/Entrevista Técnica - Juan David Torres Jimenez_comprimido.m4a" --form model=whisper-1 --form response_format=text > Entrevista_Técnica_Juan_David_Torres_Jimenez_transcripcion.txt

curl --request POST --url https://api.openai.com/v1/audio/transcriptions --header "Authorization: Bearer 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" --header 'Content-Type: multipart/form-data' --form "file=@/workspaces/architecture/proyectos/minutas-automaticas/grabaciones/Hospitalizacion Fdo_comprimido.m4a" --form model=whisper-1 --form response_format=text > Hospitalizacion_Fdo_transcripcion.txt

```


export OPENAI_API_KEY="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"
