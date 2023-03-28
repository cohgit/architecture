from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
import selenium.webdriver.common.devtools.v107 as devtools

optionsChrome = webdriver.ChromeOptions()
optionsChrome.set_capability("se:recordVideo","True")
optionsChrome.set_capability("se:bidiEnabled","True")

driver = webdriver.Remote(
   command_executor='http://20.1.130.61:4444/wd/hub',
   options=optionsChrome
)
driver.implicitly_wait(1);

#driver.execute(devtools.emulation.set_geolocation_override(latitude=41.8781,longitude=-87.6298,accuracy=100))

try:
    driver.get("https://www.google.cl/maps")
    print("http://20.1.130.61/videos/"+driver.session_id)
finally:
    driver.quit()