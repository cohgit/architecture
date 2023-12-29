import json
f = open('/Users/imagemaker/Downloads/producto_empresas.json')
data = json.load(f)
for i in data['body']:

    insert = f"INSERT INTO EXEPCOME.PRODUCTO (ID, NOMBRE_PRODUCTO, TIPO_CLIENTE) VALUES ( {+i['idProducto']} ,'{i['nombre']}' ,'E');"
    print(insert)
f.close()

f = open('/Users/imagemaker/Downloads/PRODUCTOS_CLIENTE_PN.js')
data = json.load(f)
for i in data['body']:
    insert = f"INSERT INTO EXEPCOME.PRODUCTO (ID, NOMBRE_PRODUCTO, TIPO_CLIENTE) VALUES ( {+i['idProducto']} ,'{i['nombre']}' ,'G');"
    print(insert)
f.close()

f = open('/Users/imagemaker/Downloads/productos_PNG.JSON')
data = json.load(f)
for i in data['body']:
    insert = f"INSERT INTO EXEPCOME.PRODUCTO (ID, NOMBRE_PRODUCTO, TIPO_CLIENTE) VALUES ( {+i['idProducto']} ,'{i['nombre']}' ,'N');"
    print(insert)
f.close()
