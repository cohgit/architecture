```json
steps:
- script: |
    # Instalar dependencias
    npm install markdownlint-cli

    # Ejecutar validación
    npx markdownlint ./*.md
  displayName: 'Validar archivos .md'

```