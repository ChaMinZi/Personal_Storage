#using python2.7 & selenium
import sys
reload(sys)
sys.setdefaultencoding('utf-8')

from selenium import webdriver
from bs4 import BeautifulSoup
from time import sleep

url = "https://www.facebook.com/wannaoneS2/posts/966366690184140"

driver = webdriver.Chrome('./chromedriver')
driver.implicitly_wait(3)

driver.get(url)
html = driver.page_source
soup = BeautifulSoup(html,'html.parser')
imgs = soup.find_all("img",{"class" : "scaledImageFitWidth img"})

for src in imgs:
        print(src)

driver.quit()
sys.exit();
