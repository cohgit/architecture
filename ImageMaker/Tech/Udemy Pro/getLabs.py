import http.client
import json
import csv

conn = http.client.HTTPSConnection("imagemakertrial.udemy.com")
payload = ''
headers = {
  'accept': 'application/json, text/plain, */*',
  'accept-language': 'es-ES',
  'cookie': '__zlcmid=1Nvn2zzH4eEfXgk; _ga_E5H32MGF72=GS1.1.1727295055.1.1.1727295655.0.0.0; __udmy_2_v57r=01925faf907d46988182217960bb7bb6; __stripe_mid=05e8560c-c326-4db5-b5e9-f7e0d93bf6da0be746; ud_cache_brand=342825CLes_ES; ud_cache_marketplace_country=CL; ud_cache_price_country=CL; ud_cache_version=1; ud_cache_language=es; ud_cache_device=None; ud_user_jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjgzMzAyNjczLCJlbWFpbCI6ImNlc2FyLm9nYWxkZUBpbWFnZW1ha2VyLmNvbSIsImlzX3N1cGVydXNlciI6ZmFsc2UsImdyb3VwX2lkcyI6W119.hJE7az7x7aWyP49EeSx9ohZD--ffB29bJaZ_yAO8AX4; ud_credit_last_seen=None; ud_locale=es_ES; client_id=bd2565cb7b0c313f5e9bae44961e8db2; access_token=9vsSbM0AtvqeFNAJAvX60DKI48lkBnxkxT9EJIQR; dj_session_id=dznjzeyq58jb91vz11jk3tynbwnl0z77; ud_cache_user=283302673; ud_cache_logged_in=1; csrftoken=vQ17NB4XV6n7LqaZSIZweCsdXyCQZ1X1; ud_cache_campaign_code=MTST7102224A2; __cfruid=ef5e17ab799d6bdc45a66f83cbcfb4248c1f7a77-1729778631; __cf_bm=da6xc1vC3o196TZvzQKkRCkOFaIxdwB5bEmd1WrYNi4-1729778631-1.0.1.1-.MC2..LIKNWOLIJYLrvBz.igDCxHFOAcc8MgQFpToCX4v9XACbZOeld8e1ik6zkm_EU1iePQqjwEMhZQfCi9jQ; ud_credit_unseen=0; cf_clearance=3lhGzV12DCbumb7qhXDdrjJ8yH6gesmShJTln_U1Z2c-1729778649-1.2.1.1-SKYE4CLTASJwsd_dNW9PNM8MRhHEpwiGSl0rW3rmMeYqt6XrUogYDFMrL7ul40hFtupse9oHgzJ4g1Px1.lHUP2JJUwLbsIFpgwiS73jdDBS390bu60v68o252D9AwSaOHKYitPos9cMsR6UAkuRy6xRDt4jKu_ayIae8QzL63FoxMQdoKblZzSOAAViFYRghqb2NwnPvqyAhvu74griDkQZ6ZuID2AWJNTiRgVNP_JS90PoPVxfsOo6jZcQM76Ae_pPbc8sGst1zlZLS26dJatYnJGhhy_93HgidYonU5luL50BAMjw9WBw1tsbk_3ViYl9oP3jsiE3ZxmAOccTryuT1uQjlQSUxRbJ9caeD3yb47XoHW5EoXV0n8VE4Zf4; __stripe_sid=e72fca17-074a-4e06-97ca-a91e661e4c9103eb14; ud_cache_release=5426dea193dc045b49fd; fs_uid=#ZD1RP#9c420cb5-8739-4a55-9457-af7d05844bd4:503b208f-1311-4087-8949-cafbd46b47e0:1729778630789::6#6dc2b2aa#/1761232263; _gcl_au=1.1.1658125055.1729778873; __ssid=e8d1f06e9f3ea2f04d10d2b6fa6668c; _fbp=fb.1.1729778873454.519720062330174817; _yjsu_yjad=1729778874.66ee3559-106b-4815-afa1-b3d166210cb2; _ga_7YMFEFLR6Q=GS1.1.1729778873.1.0.1729778880.0.0.0; _rdt_uuid=1729778873354.3adb13c9-02d9-4e60-93d3-9ff787432dd5; _uetsid=5c89cee0921111efb5e43b2c53397b71|1k8sbh5|2|fqa|0|1758; _uetvid=5c89f330921111ef8a21952916c9e6de|1toj44j|1729778884653|2|1|bat.bing.com/p/insights/c/z; _ga=GA1.2.514267479.1727295055; _gid=GA1.2.2069966799.1729778899; _gat=1; _dc_gtm_UA-12366301-1=1; fs_lua=1.1729778640794; ufb_acc="3@L7TD_2LVR76cfkyXQYe3XLd1KjxoRUpR597TC0JFZ_RAdZ-t99M7uDwRzElCPyGerCnOXzpP1g=="; evi="3@UhqLTYC2C3NqtdCb7Pim-kYf2iAuv8814JV2HXt7r4YGKExExvUCJoy-"; ud_rule_vars=eJxtjU0KwyAYBa8S3LYpn8b4dxZB1GgrTZGqySbk7g20hS66fbyZ2VCz5RpamMyaamq5KMCSjNFGCXyiTAqBBSGYSwbOceeY8jnfU0CqQ5tGMZXa3qyZbAv62DUiQGiPoSe4w4OigxrlhQsiuTgBKACNzsdrtgfa8uJvphUbY_Km5qX4YFZbknXzxxYeNs0_SAnPJdS_PdphqkAqYBcxcsnpt7ej_QWk70aU:1t3yWQ:e0yPVNHkn18k7vEn6Myd_Cm_SLoIS7SXGrcAaE9uORI; eventing_session_id=NmRiMmRhMjctZGVjOC00Nz-1729780747264; _dd_s=rum=0&expire=1729779848365; OptanonConsent=isGpcEnabled=0&datestamp=Thu+Oct+24+2024+11%3A09%3A09+GMT-0300+(hora+de+verano+de+Chile)&version=202407.1.0&browserGpcFlag=0&isIABGlobal=false&hosts=&consentId=71dcbf6e-7aad-4aa5-b010-ecf597da0b34&interactionCount=1&isAnonUser=1&landingPath=NotLandingPage&groups=C0001%3A1%2CC0003%3A0%2CC0002%3A0&AwaitingReconsent=false; __cf_bm=X_4wNiOQiGqgc5ZjGI5ITlEV16jbgHpmjgEYxT4hnfI-1729780050-1.0.1.1-7D44bxRNHB3eQJDRK_joxQrz3Shc6pHfaBAync32zXP0U2G85SjuVUqlCPEsHSqjHsjJDOscyCH.xHhAujAeTQ; __udmy_2_v57r=01925faf907d46988182217960bb7bb6; evi="3@qQVx7dPnrY47ytequNrSo6OkzkOSmJL51KYhYscZc4zwzxTZyvnV60Pi"; ud_cache_brand=342825CLes_ES; ud_cache_device=None; ud_cache_language=es; ud_cache_logged_in=1; ud_cache_marketplace_country=CL; ud_cache_price_country=CL; ud_cache_release=5426dea193dc045b49fd; ud_cache_user=283302673; ud_cache_version=1; ud_rule_vars=eJxtjU0KwyAYBa8S3LYpn0bjz1kEUaOtNEWqJpuQuzfQFrro9vFmZkPNlmtoYTJrqqnlogBLwqKNEvhERykEFoRgLkdwjjs3Kp_zPQWkOrRpFFOp7c2aybagj10jAoT2GHqCOzwoOigmL1wQycUJQAFodD5esz3Qlhd_M63YGJM3NS_FB7PakqybP7bwsGn-QUp4LqH-7dEOU0W4GuCCKTAmv70d7S-jtUaB:1t3yoE:pr1TVRMGa31qVreK5Hr5qQQWcRxe1r4ft2sqMnbdhmI',
  'priority': 'u=1, i',
  #'referer': 'https://imagemakertrial.udemy.com/labs/listing/?vertical=all&p=20',
  'sec-ch-ua': '"Chromium";v="130", "Google Chrome";v="130", "Not?A_Brand";v="99"',
  'sec-ch-ua-mobile': '?0',
  'sec-ch-ua-platform': '"Windows"',
  'sec-fetch-dest': 'empty',
  'sec-fetch-mode': 'cors',
  'sec-fetch-site': 'same-origin',
  'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36',
  'x-requested-with': 'XMLHttpRequest',
  'x-udemy-cache-brand': '342825CLes_ES',
  'x-udemy-cache-campaign-code': 'MTST7102224A2',
  'x-udemy-cache-device': 'None',
  'x-udemy-cache-language': 'es',
  'x-udemy-cache-logged-in': '1',
  'x-udemy-cache-marketplace-country': 'CL',
  'x-udemy-cache-price-country': 'CL',
  'x-udemy-cache-release': '5426dea193dc045b49fd',
  'x-udemy-cache-user': '283302673',
  'x-udemy-cache-version': '1'
}
page=1
conn.request("GET", f"/api-2.0/labs/search/?ordering=-created&page={page}&query=", payload, headers)
res = conn.getresponse()
data = res.read()

# Parsear el JSON recibido
parsed_data = json.loads(data.decode("utf-8"))
limit = 20 # por defecto en udemy es 20
total = parsed_data['count'];
pages = (total // limit) + (1 if total % limit != 0 else 0)

print(f"Total: {total} Page: {page} Pages: {pages}");
all_results = parsed_data['results']

# Iterar sobre todas las páginas
for pageNav in range(2, pages + 1):  # Ya obtuvimos la página 1, así que empezamos en 2
    conn.request("GET", f"/api-2.0/labs/search/?ordering=-created&page={pageNav}&query=", payload, headers)
    res = conn.getresponse()
    data = res.read()
    parsed_data = json.loads(data.decode("utf-8"))
    all_results.extend(parsed_data['results'])
    print(f"Total: {total} Page: {pageNav} Pages: {pages}");

print(f"Total de resultados obtenidos: {len(all_results)}")

# Crear y escribir en un archivo CSV
csv_filename = 'resultados.csv'

# Obtener los nombres de las columnas del primer elemento de los resultados
columnas = all_results[0].keys()

with open(csv_filename, mode='w', newline='', encoding='utf-8') as file:
    writer = csv.DictWriter(file, fieldnames=columnas)
    
    # Escribir encabezados
    writer.writeheader()
    
    # Escribir filas
    for result in all_results:
        writer.writerow(result)

print(f"Los resultados han sido guardados en '{csv_filename}'")
