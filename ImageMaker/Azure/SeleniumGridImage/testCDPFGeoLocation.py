import trio
from selenium import webdriver
#Replace the version to match the Chrome version
import selenium.webdriver.common.devtools.v107 as devtools

async def printConsoleLogs():
  chrome_options = webdriver.ChromeOptions()
  chrome_options.set_capability("se:recordVideo","True")

  driver = webdriver.Remote(
        command_executor='http://20.230.106.158:4444/wd/hub',
        options=chrome_options
    )
  #driver = webdriver.Chrome()
  async with driver.bidi_connection() as session:
        cdpSession = session.session
        await cdpSession.execute(devtools.emulation.set_geolocation_override(latitude=-33.4231316,longitude=-70.6635925,accuracy=100))
  driver.get("https://my-location.org/")
  print("http://20.230.106.158/videos/"+driver.session_id)
  driver.quit()

trio.run(printConsoleLogs)