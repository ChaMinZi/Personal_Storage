#using python3.5 & request & selenium
import sys
import urllib.request
from selenium import webdriver
from bs4 import BeautifulSoup
from bs4 import Comment
import json
import time
import urllib3

#url = "https://www.instagram.com/p/BbUAWWrDhmJ/"
#url = "https://www.instagram.com/p/Ba8wuebAeAA/"
#url = "https://www.instagram.com/p/BbdCiOsnG9Z/"

url = sys.argv[1]

app_id = "..."
app_secret ="..."
access_token = app_id + "|" + app_secret
page_id = "..."

def unescape(s):
	s = s.replace("&lt;", "<")
	s = s.replace("&gt;", ">")
	s = s.replace("&amp;", "&")
	return s

def request_until_suceed(url):
	req = urllib.request.Request(url)
	success = False
	while success is False:
		try:
			response = urllib.request.urlopen(req)
			if response.getcode() == 200:
				success = True
		except Exception as error:
			print(error) #what error
			time.sleep(5)
			print ("Error for url %s : %s" %(url, datetimenow()))
	return response.read()

def count_image(soup):
	soup = soup.split('<table class="_4ag49">')
	count_image = 1
	try:
		soup = soup[1].split("</tr>")
		count_image = soup[0].count('<td>')
		return count_image
	except IndexError:
		return count_image

#-------------------------------------------------------------------------
data = request_until_suceed(url)
soup = BeautifulSoup(data,'html.parser')
str_soup = str(soup)
count = count_image(str_soup)

html = str_soup.split('<script type="text/javascript">window._sharedData')
html = html[1].split('"display_url": "')
#html = html[1].split('",')

#print (count)
for i in range(2,len(html)):
	image = html[i].split('"')
	print (image[0])
