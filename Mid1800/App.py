import requests
import os
import json

proxies = {
 "http": "http://168.219.61.252:8080",
 "https": "http://168.219.61.252:8080"
}

headers = {
    'X-Mashape-key': '32fb35442amsh8194cc80cb9f946p1a47f2jsn7bf1710e8a4d',
}

os.environ['REQUESTS_CA_BUNDLE'] = os.path.join(
    '/etc/ssl/certs/',
    'ca-certificates.crt')

filepath = 'MID30.txt'
with open(filepath) as fp:
   line = fp.readline()
   i = 1;
   while line:
       word = line.split(" ")[1]
       response = requests.get('https://wordsapiv1.p.mashape.com/words/' + word, headers=headers, proxies=proxies)
       jsondumps = json.dumps(response.json())
       f = open("dictionary/{:0>4d}_{}.json".format(i, word), "w")
       f.write(jsondumps)
       f.close()
       print("{:d} {}".format(i, word))
       i += 1
       line = fp.readline()

