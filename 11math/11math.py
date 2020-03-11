import requests
import os
import json
import pdfkit
from data import ChapterList, StepList

proxies = {
 "http": "http://168.219.61.252:8080",
 "https": "http://168.219.61.252:8080"
}

os.environ['REQUESTS_CA_BUNDLE'] = os.path.join(
    '/etc/ssl/certs/',
    'ca-certificates.crt')

config = pdfkit.configuration(wkhtmltopdf='/usr/bin/wkhtmltopdf')
options = {
    'proxy': '168.219.61.252:8080'
}

home = '/home/siwon.sung/sandbox/11math'
stepList = json.loads(StepList)
for step in stepList:
    step_idx = step['idx']
    chapter = step['chapter']
    no = step['no']
    title = step['title'].replace('/', '&').replace('@', '')
    grade = step['grade'][0]
    if not grade == '2':
        continue

    semester = step['grade'][1]
    dir = '{}/{}학년/{}학기/{}-{}.{}'.format(home, grade, semester, chapter, no, title)
    if not os.path.exists(dir):
        os.makedirs(dir)

    print("{} {} {} {} {}".format(step_idx, chapter, title, grade, semester))
    for i in range(0, 2):
        url = 'https://www.11math.com/calc.json?level=&grade={}{}&chapter={}&step={}&new=t'.format(grade, semester, chapter, step_idx);
        # print(url)
        response = requests.get(url, proxies=proxies)
        pcode = response.json()['pcode']
        # print(pcode)
        purl = 'https://www.11math.com/calc#{}'.format(pcode)
        print(purl)
        pdfkit.from_url(purl, '{}/{}{}_{}-{}_{}.pdf'.format(dir, grade, semester, chapter, no, pcode), configuration=config, options=options)