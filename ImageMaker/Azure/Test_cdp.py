import asyncio
import pytest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
from selenium import webdriver
#Replace the version to match the Chrome version
import selenium.webdriver.common.devtools.v107 as devtools

@staticmethod
@pytest.mark.asyncio
async def test_geoLocationTest():
    chrome_options = webdriver.ChromeOptions()
    chrome_options.set_capability("se:recordVideo","True")
    driver = webdriver.Remote(
        command_executor='http://20.1.130.61:4444/wd/hub',
        options=chrome_options
    )
    async with driver.bidi_connection() as session:
        cdpSession = session.session
        await cdpSession.execute(devtools.emulation.set_geolocation_override(latitude=41.8781,longitude=-87.6298,accuracy=100))
    driver.get("https://www.google.cl/")
    driver.quit()