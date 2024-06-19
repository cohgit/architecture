import time
import gspread
from gspread_dataframe import set_with_dataframe
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request
import pickle
import pandas as pd
from glob import glob
import os
import csv

# Lista los archivos CSV y los ordena por fecha de modificación, del más reciente al más antiguo
csv_files = glob('/home/cogalde/dev/architecture/ImageMaker/ControlDeGestion/csv/*csv')
csv_files.sort(key=lambda x: os.path.getmtime(x), reverse=True)

# Crea listas para almacenar los DataFrames
dfs_10 = []
dfs_14 = []

# Itera sobre los archivos CSV y los carga en DataFrames
for csv_file in csv_files:
    try:
        df = pd.read_csv(csv_file, sep=';')
        print(f'Reading file {csv_file} columns {df.shape[1]}')
        if df.shape[1] == 11:
            dfs_10.append(df)
        elif df.shape[1] == 15:
            dfs_14.append(df)
        else:
            print(f"Archivo {csv_file} tiene un número inesperado de columnas: {df.shape[1]}")
    except Exception as e:
        print(f"Error leyendo {csv_file}: {e}")

# Concatena los DataFrames con 10 columnas y los DataFrames con 14 columnas
final_df_10 = pd.concat(dfs_10, ignore_index=True) if dfs_10 else None
final_df_14 = pd.concat(dfs_14, ignore_index=True) if dfs_14 else None

# Crea la columna 'doc_folio_rut' en ambos DataFrames finales
if final_df_10 is not None:
    final_df_10['doc_folio_rut'] = final_df_10['Documento'].astype(str) + '_' + final_df_10['Folio'].astype(str) + '_' + final_df_10['Rut'].astype(str)

if final_df_14 is not None:
    final_df_14['doc_folio_rut'] = final_df_14['Documento'].astype(str) + '_' + final_df_14['Folio'].astype(str) + '_' + final_df_14['Rut'].astype(str)

# Une los dos DataFrames en otro nuevo de acuerdo a la columna 'doc_folio_rut'
if final_df_10 is not None and final_df_14 is not None:
    merged_df = pd.merge(final_df_10, final_df_14, how='outer', on='doc_folio_rut')
elif final_df_10 is not None:
    merged_df = final_df_10
elif final_df_14 is not None:
    merged_df = final_df_14
else:
    merged_df = None

merged_df.to_csv('/home/cogalde/dev/architecture/ImageMaker/ControlDeGestion/output/salida_raw.csv', index=False)
print(merged_df.size)

merged_df = merged_df.drop_duplicates()
print(merged_df.size)

# Si existe una versión 'x' y una versión 'y', toma 'x', de lo contrario toma la versión que esté presente
for col in ['Documento', 'Folio', 'Fecha Emision', 'Rut', 'RazonSocial', 'Codigo Vendedor']:
    merged_df[col] = merged_df[col + '_x'].combine_first(merged_df[col + '_y'])
    merged_df.drop([col + '_x', col + '_y'], axis=1, inplace=True)

merged_df.fillna('', inplace=True)

merged_df.to_csv('/home/cogalde/dev/architecture/ImageMaker/ControlDeGestion/output/salida_raw_merge.csv', index=False)

unique_cols = ['Documento', 'Folio', 'Rut', 'RazonSocial', 'Codigo Vendedor'] #, 'Glosa'
merged_df = merged_df.drop_duplicates(subset=unique_cols, keep='first')

merged_df.to_csv('/home/cogalde/dev/architecture/ImageMaker/ControlDeGestion/output/salida_raw_merge_drop.csv', index=False)

# Define el orden de las columnas
column_order = [
    'Documento', 'Folio', 'Fecha Emision', 'Fecha de Vencimiento', 'Rut',
    'RazonSocial', 'Producto', 'Glosa', 'Cantidad', 'Precio', 'Afecto',
    'DescuentoFactor', 'Nota Credito', 'Codigo Vendedor', 'MontoNeto', 'IVA',
    'MontoExento', 'MontoTotal'
]

# Reorganiza las columnas en el DataFrame según el orden especificado
merged_df = merged_df[column_order]
merged_df['MontoExento'] = pd.to_numeric(merged_df['MontoExento'], errors='coerce')
merged_df['MontoNeto'] = pd.to_numeric(merged_df['MontoNeto'], errors='coerce')
merged_df['MontoExento'] = merged_df['MontoExento'].fillna(0)
merged_df['MontoNeto'] = merged_df['MontoNeto'].fillna(0)
merged_df['Exento + Neto'] = merged_df['MontoExento'] + merged_df['MontoNeto']

merged_df.to_csv('/home/cogalde/dev/architecture/ImageMaker/ControlDeGestion/output/salida.csv', index=False)

