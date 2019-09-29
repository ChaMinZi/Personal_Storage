# -*- coding: utf-8 -*-
#using python3.5 & request & selenium
#using python2.7 & selenium

import sys
import urllib.request
from selenium import webdriver
from bs4 import BeautifulSoup
from bs4 import Comment
import json
import time
import urllib3
#url = "https://www.facebook.com/wannaoneS2/posts/966366690184140"
#url = "https://www.facebook.com/ongseongwoopage/posts/853885714782880"
#url = "https://www.facebook.com/ongseongwoopage/posts/816510298520422"
#url = "https://www.facebook.com/wannaoneS2/posts/996444753843000"
#url = "https://www.facebook.com/groups/codingeverybody/permalink/1858395980867596/"
#url = "https://www.facebook.com/ggsing/posts/1258041760967268"

#url = "https://m.facebook.com/story.php?story_fbid=1560629660670688&id=270935242973476"
#url = "https://m.facebook.com/YooAhInpage/photos/a.953902188001290.1073741828.953899924668183/1625089527549216/?type=3&source=48"
app_id = "126860411384620"
app_secret ="ed868a202489e08e2ad0d010b31dfd20"
access_token = app_id + "|" + app_secret
page_id = "899513713536105"

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
	#return response.read()
	return response.read().decode(response.headers.get_content_charset())

def find_counter_tag(url): #find back side of the url
	findurl = url.split('.com')
	return findurl[1]

def count_blank(string):
	count = 0
	for word in string:
		if word == ' ':
			count = count + 1
	return count
#-----------------------------------------------------------------------
def login(url):
	driver = webdriver.Chrome('./chromedriver')
	driver.implicitly_wait(3)
	driver.get('https://www.facebook.com/login')
	time.sleep(1)
	driver.find_element_by_name('email').send_keys('misha11@hanmail.net')
	time.sleep(1)
	driver.find_element_by_name('pass').send_keys('whatthefuck1')
	time.sleep(1)
	driver.find_element_by_xpath('//*[@id="loginbutton"]').click()
	driver.get(url)
	return driver

#-------------------------------------------------------------------------
data = request_until_suceed(url) #print (data)
soup = BeautifulSoup(data,'html.parser') #print (soup)
comments = soup.find_all(text=lambda text:isinstance(text, Comment))
tmp_comments = ''.join(comments[1:])
soup_comment = BeautifulSoup(tmp_comments,'html.parser')

#------------Find to main image--------------------------------
for src in soup_comment.find_all("img",{"class" : "scaledImageFitWidth img"}):
	try:
		width = unescape(str(src))
		print (width)
	except NavigableString:
		pass

for src in soup_comment.find_all("img",{"class" : "scaledImageFitHeight img"}):
	try:
		height = unescape(str(src))
		print (height)
	except NavigableString:
		pass

#-------------how many images?----------------------------------
hreftag = find_counter_tag(url)
str_soup = str(soup)
Find_href = str_soup.split('<span class="fwb">')
Find_href = Find_href[1].split('</a>을')
Find_href = Find_href[0].split(hreftag)
count = count_blank(Find_href[1])
Find_href = Find_href[1].split()
Find_href = Find_href[count].split('&')
count_image = int(Find_href[0])

if count_image > 4:
	first_image = str_soup.split('<div class="uiScaledImageContainer"')
	first_image = first_image[0].split('<div class="mtm">')
	first_image = first_image[1].split('href="')
	first_image = first_image[1].split('"')
	first_image_url = "https://www.facebook.com"+first_image[0]
	current_page_url = []
	browser = login(first_image_url)
	button = browser.find_element_by_xpath('//*[@id="photos_snowlift"]/div[2]/div/div[1]/div[1]/div[1]/a[2]')

	for i in range(0,count_image):
		webdriver.ActionChains(browser).click(on_element=button).perform()
		current_page_url.append(browser.current_url)
		time.sleep(1)
	for i in range(4,count_image):
		browser.get(current_page_url[i])
		rhtml = browser.page_source.split('class="fbPhotosPhotoTagboxes tagContainer"')
		rhtml = rhtml[1].split('<div class="videoStage"')
		rsoup = BeautifulSoup(rhtml[0],'html.parser')
		rimgs = str(rsoup.find("img",{"class" : "spotlight"}))
		print (unescape(rimgs))


browser.quit()
driver.quit()
