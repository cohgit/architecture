import matplotlib.pyplot as plt

# Datos
alta_influencia_alto_interes = [
    "Juan Cisne", "Marco Herrera", "Mariana Villalobos", "Jorge Silva", "Víctor Corrales"
]
baja_influencia_alto_interes = [
    "Richard Arce y Pablo Herrera", "Johan Umaña", "Luis Sánchez", "Melissa Carvajal"
]
alta_influencia_bajo_interes = [
    "Luis Navarro", "Jonathan Méndez Solano"
]
baja_influencia_bajo_interes = [
    "Federico Castro Bonilla", "José Miguel Brenes Quesada", "ruben.caballero@cr.asesorextemoca.com"
]

# Crear el cuadrante
plt.figure(figsize=(10, 10))

# Alta Influencia / Alto Interés
for i, name in enumerate(alta_influencia_alto_interes):
    plt.text(0.75, 0.9 - i * 0.1, name, bbox=dict(facecolor='lightblue', alpha=0.5), ha='center')

# Baja Influencia / Alto Interés
for i, name in enumerate(baja_influencia_alto_interes):
    plt.text(0.25, 0.9 - i * 0.1, name, bbox=dict(facecolor='lightblue', alpha=0.5), ha='center')

# Alta Influencia / Bajo Interés
for i, name in enumerate(alta_influencia_bajo_interes):
    plt.text(0.75, 0.3 - i * 0.1, name, bbox=dict(facecolor='lightblue', alpha=0.5), ha='center')

# Baja Influencia / Bajo Interés
for i, name in enumerate(baja_influencia_bajo_interes):
    plt.text(0.25, 0.3 - i * 0.1, name, bbox=dict(facecolor='lightblue', alpha=0.5), ha='center')

# Configuración de la gráfica
plt.title('Cuadrante de Influencia vs Interés')
plt.xlabel('Interés')
plt.ylabel('Influencia')
plt.xlim(0, 1)
plt.ylim(0, 1)
plt.xticks([0.25, 0.75], ['Bajo', 'Alto'])
plt.yticks([0.25, 0.75], ['Bajo', 'Alto'])
#plt.grid(True, linewidth=0.5, linestyle='--')
plt.axvline(x=0.5, color='black', linewidth=1)
plt.axhline(y=0.5, color='black', linewidth=1)
#plt.show()
plt.savefig('cuadrante-devops.png')