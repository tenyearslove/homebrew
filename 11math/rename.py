import os
import json
from data import ChapterList, StepList

home = '/home/siwon.sung/sandbox/11math'
stepList = json.loads(StepList)
for step in stepList:
    step_idx = step['idx']
    chapter = step['chapter']
    no = step['no']
    title = step['title'].replace('/', '&').replace('@', '')
    grade = step['grade'][0]
    semester = step['grade'][1]
    dir = '{}/{}학년/{}학기/{}-{}.{}'.format(home, grade, semester, chapter, no, title)

    if grade == '3' and semester == '1':
        list = os.listdir(dir)
        for item in list:
            src = '{}/{}'.format(dir, item)
            dst = '{}/{}{}_{}-{}_{}'.format(dir, grade, semester, chapter, no, item)
            os.rename(src, dst)