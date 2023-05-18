#Performing Geolocation Testing With Selenium Using Python For Selenium Test Automation
import pytest
import pytest_html
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
import time
from time import sleep
import sys
import urllib3
import warnings
import selenium.webdriver.common.devtools.v107 as devtools
 
class Test_Scenario_1:
    @pytest.mark.asyncio
    async def test_1(self):
        #driver = webdriver.Chrome()
        chrome_options = webdriver.ChromeOptions()
        chrome_options.set_capability("se:recordVideo","True")
        driver = webdriver.Remote(
            command_executor='http://20.230.106.158:4444/wd/hub',
            options=chrome_options
        )
        self.latitude = 42.1408845
        self.longitude = -72.5033907
        self.accuracy = 100
 
        async with driver.bidi_connection() as session:
            cdpSession = session.session
            await cdpSession.execute(devtools.emulation.set_geolocation_override(latitude=-33.4231316,longitude=-70.6635925,accuracy=100))
    
        driver.maximize_window()
        
        driver.get("https://locations.dennys.com/search.html/")

        time.sleep(10)

        location_icon = driver.find_element_by_css_selector(".icon-geolocate")
        time.sleep(10)
        location_icon.click()

        time.sleep(10)
        print("Geolocation testing with Selenium is complete")