import trio
from selenium import webdriver
from selenium.webdriver.common.log import Log

async def printConsoleLogs():
  chrome_options = webdriver.ChromeOptions()
  driver = webdriver.Remote(
        command_executor='http://20.230.106.158:4444/wd/hub',
        options=chrome_options
    )
  #driver = webdriver.Chrome()
  driver.get("http://www.google.com")

  async with driver.bidi_connection() as session:
      log = Log(driver, session)
      from selenium.webdriver.common.bidi.console import Console
      async with log.add_listener(Console.ALL) as messages:
          driver.execute_script("console.log('I love cheese')")
      print(messages["message"])

  driver.quit()

trio.run(printConsoleLogs)