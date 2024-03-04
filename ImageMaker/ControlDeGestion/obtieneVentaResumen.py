from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains
import time
#import gspread
#from gspread_dataframe import set_with_dataframe
#from google_auth_oauthlib.flow import InstalledAppFlow
#from google.auth.transport.requests import Request
import os
import pickle
import pandas as pd
from glob import glob
from datetime import datetime

## Agrega librerías
import selenium.webdriver.common.devtools.v107 as devtools
import trio
from selenium.webdriver.common.bidi.console import Console
from selenium.webdriver.common.by import By
from selenium.webdriver.common.log import Log

user = "18572064-0"
password = "Imit2023"

def login(driver):
  ## Login
  driver.get('https://web.nubox.com/Login/Account/Login?ReturnUrl=%2FSistemaLogin')
  loginuser_elements = WebDriverWait(driver, 10).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, "input.nbx-input-cirrus")))
  loginuser_elements[0].send_keys(user)
  time.sleep(3)
  loginuser_elements[1].send_keys(password)
  time.sleep(5)
  loginuser_elements[1].send_keys(Keys.ENTER)
  time.sleep(3)

def descargaResumenFacturas(n,driver):

  driver.get('https://web.nubox.com/SistemaLogin/')
  # Encuentra todos los elementos que coinciden con la clase "jqx-tree-grid-title" y que contienen el texto "Factura Electrónica"
  elements = WebDriverWait(driver, 15).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, ".jqx-tree-grid-title.jqx-tree-grid-title-nubox")))

  # Filtra los elementos para encontrar aquellos que contienen el texto específico
  factura_electronica_elements = [element for element in elements if "Factura Electrónica" in element.text]

  
  
  # Haz clic en el primer elemento que coincide
  if factura_electronica_elements and len(factura_electronica_elements)>n:
    factura_electronica_elements[n].click()
    
    time.sleep(10)
    
    # Espera hasta que el menú esté presente en la página
    menu = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, ".nuboxHeaderMenu")))
    
    time.sleep(3)
    # Haz clic en el menú
    menu.click()
    
    time.sleep(5)
    
    # Espera hasta que el elemento esté presente en la página
    reportes_menu = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, "m1300")))
    
    # Haz clic en el elemento
    time.sleep(3)
    reportes_menu.click()
    time.sleep(3)
    
    # Espera hasta que el elemento esté presente en la página
    ventas_menu = WebDriverWait(driver, 3).until(EC.presence_of_element_located((By.ID, "m1365")))
    
    # Haz clic en el elemento
    ventas_menu.click()
    time.sleep(3)
    
    # Espera hasta que el elemento esté presente en la página
    resumen_ventas_menu = WebDriverWait(driver, 3).until(EC.presence_of_element_located((By.ID, "m1367")))
    
    # Haz clic en el elemento
    resumen_ventas_menu.click()
    time.sleep(3)
    
    iframe_element = driver.find_element(By.ID, "Contenido")
    driver.switch_to.frame(iframe_element)
    time.sleep(3)
    
    # Espera hasta que el elemento con el atributo onclick correspondiente esté presente en la página
    formato_excel_element = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.CSS_SELECTOR, ".dropdown")))
    
    # Haz clic en el elemento
    formato_excel_element.click()
    time.sleep(3)
    
    # Espera hasta que el elemento con el atributo onclick correspondiente esté presente en la página
    formato_excel_element = WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.CSS_SELECTOR, "p[onclick=\"imprimirConModal('0')\"]")))
    
    # Haz clic en el elemento
    formato_excel_element.click()
    time.sleep(3)

def descargaDetalleFacturas(n,driver):

  driver.get('https://web.nubox.com/SistemaLogin/')
  # Encuentra todos los elementos que coinciden con la clase "jqx-tree-grid-title" y que contienen el texto "Factura Electrónica"
  elements = WebDriverWait(driver, 15).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, ".jqx-tree-grid-title.jqx-tree-grid-title-nubox")))

  # Filtra los elementos para encontrar aquellos que contienen el texto específico
  factura_electronica_elements = [element for element in elements if "Factura Electrónica" in element.text]
  time.sleep(3)

  # Haz clic en el primer elemento que coincide
  if factura_electronica_elements and len(factura_electronica_elements)>n:
    factura_electronica_elements[n].click()

    time.sleep(10)
    
    # Espera hasta que el menú esté presente en la página
    menu = WebDriverWait(driver, 5).until(EC.presence_of_element_located((By.CSS_SELECTOR, ".nuboxHeaderMenu")))
    
    time.sleep(7)
    # Haz clic en el menú
    menu.click()
    
    time.sleep(10)
    
    # Espera hasta que el elemento esté presente en la página
    reportes_menu = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, "m1300")))
    
    # Haz clic en el elemento
    time.sleep(10)
    reportes_menu.click()
    time.sleep(3)
    
    # Espera hasta que el elemento esté presente en la página
    ventas_menu = WebDriverWait(driver, 3).until(EC.presence_of_element_located((By.ID, "m1365")))
    
    # Haz clic en el elemento
    ventas_menu.click()
    time.sleep(3)
    
    # Espera hasta que el elemento esté presente en la página
    otros_menu = WebDriverWait(driver, 3).until(EC.presence_of_element_located((By.ID, "m1341")))
    
    # Haz clic en el elemento
    otros_menu.click()
    time.sleep(3)
    
    iframe_element = driver.find_element(By.ID, "Contenido")
    driver.switch_to.frame(iframe_element)
    time.sleep(3)
    
    campo_fecha = driver.find_element(By.ID, 'Otros_fechaDesde')
    
    # Coloca el cursor en el campo
    campo_fecha.click()
    time.sleep(2)
    
    # Presiona la tecla "HOME" para mover el cursor al inicio del campo
    campo_fecha.send_keys(Keys.HOME)
    
    # Presiona la tecla "DELETE" dos veces para eliminar los dos primeros caracteres
    campo_fecha.send_keys(Keys.DELETE)
    time.sleep(1)
    campo_fecha.send_keys(Keys.DELETE)
    time.sleep(1)
    
    # Espera explícita para dar tiempo al campo de entrada para procesar el cambio
    time.sleep(1)  # puedes ajustar la cantidad de tiempo según sea necesario
    
    # Ingresa los dígitos "01" en el campo de entrada
    campo_fecha.send_keys('01')
    
    # Encuentra el botón del menú desplegable (podrías necesitar ajustar este selector)
    dropdown_button = driver.find_element(By.CSS_SELECTOR, 'div.dijitReset.dijitInputField.dijitButtonText')
    
    # Haz clic en el botón para abrir el menú desplegable
    dropdown_button.click()
    
    # Espera un poco para que se carguen las opciones
    time.sleep(2)
    
    # Crea un objeto de acciones encadenadas
    actions = ActionChains(driver)
    
    # Mueve el cursor hacia abajo tres veces (ajusta este número según tus necesidades)
    for _ in range(2):
        actions.send_keys(Keys.ARROW_DOWN)
    
    # Presiona Enter para seleccionar la opción
    actions.send_keys(Keys.ENTER)
    
    # Ejecuta la secuencia de acciones
    actions.perform()
    time.sleep(4)
    
    # Encuentra el botón por su ID
    button = driver.find_element(By.ID, 'nubox_widget_Button_0')
    
    # Haz clic en el botón
    button.click()
    time.sleep(3)

    try:
        # Encuentra el botón con la clase "dijitDialogCloseIcon"
        button = driver.find_element(By.XPATH, "//span[contains(@class, 'dijitDialogCloseIcon')]")
        
        # Haz clic en el botón
        button.click()
        time.sleep(1)
    except:
        pass
        
    # Encuentra el elemento por su ID
    radio_button = driver.find_element(By.ID, 'rdbDocConTotales')
    
    # Haz clic en el elemento
    radio_button.click()
    
    # Encuentra el elemento por su ID
    radio_button_si = driver.find_element(By.ID, 'rdbSi')
    
    # Haz clic en el elemento
    radio_button_si.click()
    time.sleep(4)
    
    # Encuentra el botón por su ID
    button = driver.find_element(By.ID, 'nubox_widget_Button_0')
    
    # Haz clic en el botón
    button.click()
    time.sleep(3)
    
    try:
        # Encuentra el botón con la clase "dijitDialogCloseIcon"
        button = driver.find_element(By.XPATH, "//span[contains(@class, 'dijitDialogCloseIcon')]")
        
        # Haz clic en el botón
        button.click()
        time.sleep(1)
    except:
        pass

async def executebatch():
  chrome_options = webdriver.ChromeOptions()
  chrome_options.set_capability("se:recordVideo","True")
  chrome_options.set_capability("se:downloadsEnabled","True")
  prefs = {
    "download.default_directory": r'/home/seluser/Downloads', # Cambia esta ruta a la ubicación de descarga deseada
    "browser.download.dir": r'/home/seluser/Downloads',
    "download.prompt_for_download": False,
    "download.directory_upgrade": True,
    "browser.download.manager.showWhenStarting": True,
    "safebrowsing_for_trusted_sources_enabled": False,
    "safebrowsing.enabled": False
  }
  chrome_options.add_experimental_option("prefs", prefs)

  driver = webdriver.Remote(
        command_executor='http://localhost:4444/wd/hub',
        options=chrome_options
  )
  
  try:
   ## Login
   login(driver)

   ## Descarga 
   #descargaResumenFacturas(0,driver)
   descargaDetalleFacturas(0,driver)
   descargaDetalleFacturas(1,driver)


   driver.get("chrome://downloads/")
   time.sleep(3)

   download = driver.execute_script("""return document.querySelector('downloads-manager').shadowRoot.querySelector('downloads-item').shadowRoot.querySelector('div#content #safe span a').title""")
   print(download)

  finally:   
   driver.quit()

trio.run(executebatch)
