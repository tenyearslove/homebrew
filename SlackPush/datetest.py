import requests, time, os
from datetime import datetime, timedelta


lastNotify = datetime.now()
while True:
    try:
        now = datetime.now()
        delta = datetime.now() - lastNotify
        print(now, lastNotify, delta.total_seconds())
        time.sleep(10)
    except:
        print('error')
