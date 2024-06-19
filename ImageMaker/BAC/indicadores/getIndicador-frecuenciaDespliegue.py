import requests
from datetime import datetime, timedelta
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# Define your Jira credentials
# Se solicita a Roberto Rozas la generación de Token 
# Referencia : https://support.atlassian.com/atlassian-account/docs/manage-api-tokens-for-your-atlassian-account/
auth = ('roberto.rozas@cr.asesorexternoca.com', 'ATATT3xFfGF05PFfNVS1mzk1TJQ-MfVtQts-n6IIhqrBw2L6sMt8sxvxC9IOWVYrJt7M0la643SN7CkfYmtueqUKQAmkxiQJ3NZ24Pbwmg8lzpHb-VtC1t-AhBIBUsZDQ8rK_0QSysl_PBkNjNn8Ty4hvUaEasC0410PY1dcmWGPU4ddptWPpfg=C55B82AB')

#Consulta las pulicaciones y filtro por año 2024 agrupados por mes 
versions_url = 'https://bac-latam.atlassian.net/rest/api/2/project/EDPEH/versions'
versions_response = requests.get(versions_url, auth=auth, headers={'Accept': 'application/json'})

if versions_response.status_code != 200:
    print(f"Error fetching versions: {versions_response.status_code}")
    print(versions_response.text)
else:
    versions_data = versions_response.json()

    # Extraer las fechas de lanzamiento
    release_dates = [item['releaseDate'] for item in versions_data]

    # Convertir a formato de fecha
    release_dates = pd.to_datetime(release_dates)

    # Convertir a Serie de pandas
    release_dates_series = pd.Series(release_dates)

    # Filtrar fechas para el año 2024
    release_dates_2024 = release_dates_series[release_dates_series.dt.year == 2024]

    # Agrupar por mes y contar el número de despliegues por mes
    deployments_per_month = release_dates_2024.groupby(release_dates_2024.dt.to_period('M')).count()

    # Convertir el índice a un formato adecuado para el gráfico
    deployments_per_month.index = deployments_per_month.index.to_timestamp()

    # Visualizar los despliegues por mes
    plt.figure(figsize=(12, 6))
    bars = plt.bar(deployments_per_month.index.strftime('%B'), deployments_per_month, color='skyblue')
    plt.xlabel('Mes')
    plt.ylabel('Número de Despliegues')
    plt.title('Frecuencia de Despliegue Mensual en 2024')
    plt.grid(axis='y')

    # Añadir etiquetas de valor encima de cada barra
    for bar in bars:
        yval = bar.get_height()
        plt.text(bar.get_x() + bar.get_width()/2 - 0.1, yval + 0.1, int(yval), va='bottom')


    # Guardar el gráfico como archivo de imagen
    plt.savefig('frecuencia_despliegue.png')