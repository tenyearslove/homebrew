import requests, time, os
from datetime import datetime, timedelta

proxies = {
 "http": "http://168.219.61.252:8080",
 "https": "http://168.219.61.252:8080"
}

os.environ['REQUESTS_CA_BUNDLE'] = os.path.join(
    '/etc/ssl/certs/',
    'ca-certificates.crt')

lastNotify = datetime.now() - timedelta(days=1)
# print(lastNotify)
# delta = datetime.now() - lastNotify
# print(delta)
# print(delta.total_seconds())

def toSlack(roomname):
    global lastNotify
    headers = {
        'Content-type': 'application/json',
    }

    data = '{"text":"' + roomname + '"}'

    #8th: 'https://hooks.slack.com/services/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'
    # 8th mo: 'https://hooks.slack.com/services/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',
    response = requests.post('https://hooks.slack.com/services/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',
                             headers=headers, data=data.encode('utf-8'), proxies=proxies)
    lastNotify = datetime.now()


def checkLibrary():
    global lastNotify
    headers = {
        'x-access-token': 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJjb29sb3ZlIiwidXNlck5hbWUiOiLshLHsi5zsm5AiLCJ1c2VyTm8iOiIwODcwMjg1MjgiLCJsaWJDb2RlIjoiMzQxMTQ3IiwibGliTmFtZSI6IuyCrOuekeyDmCIsImxvYW5IaXN0b3J5WW4iOiJZIiwiY2giOiJENEI2RDYyQjdFOTA0QkY4ODlFNTZCNjg1NUQ5RTQ5N0NDREUxOTZENzNDMTNGQkNBOUQ0MzY0NDBEREJFODMwIiwidXNlclN0YXR1cyI6IuygleyDgSIsImxvYW5TdG9wRW5kRGF0ZSI6IiIsImxvYW5Db3VudCI6IjAiLCJyZXNlcnZhdGlvbkNvdW50IjoiMCIsIm92ZXJkdWVDb3VudCI6IjAiLCJjb21tZW50Ijoi64yA7Lac6rCA64qlIO2ajOybkCDsnoXri4jri6QuIiwiY2kiOiJCem1qbzhzOTRCbCt2dFgzRXpYd2IvNWhyNFB4S3I4eW9BWWMyQmFDekQ2SW96SXY0eE9DVlNtbjVjdFVFd1NkSzVzQzA2alh2SGZ5Tk4xTnJtcmtwZz09IiwiYmlydGhkYXkiOiIxOTc5LjA4LjE5Iiwic2V4IjoiMSIsImlhdCI6MTU5OTYwNjkyMywiZXhwIjoxNTk5NjQyOTIzLCJpc3MiOiJhbHBhc3EiLCJzdWIiOiJ1c2VySW5mbyJ9.HR4RXfvED6S8nN2zVRbQc7jAPxAI-aR3YXD-rhxGuWk'
    }

    #data = '{"operationName":"QueryCalendarList","variables":{"property_id":"3226","from":"2020-09-28","to":"2020-09-28"},"query":"query QueryCalendarList($property_id: ID!, $from: String!, $to: String!) {\\n  bpCalendarList(property_id: $property_id, from: $from, to: $to) {\\n    date\\n    daily_pricing {\\n      pricing_name\\n      is_before_holiday\\n    }\\n    roomtype_list {\\n      roomtype_id: id\\n      seq\\n      property_id\\n      bp_status\\n      name\\n      avail\\n      phone_inquiry\\n      basic_price\\n      sale_price\\n      min_los\\n      channel_rates {\\n        channel_id\\n        sale_price\\n      }\\n      reservation {\\n        name\\n        phone\\n        reservation_item_status\\n      }\\n    }\\n  }\\n  holidayList(from: $from, to: $to) {\\n    date\\n    name\\n  }\\n  property(property_id: $property_id) {\\n    id\\n    leadtime\\n    leadtime_status\\n  }\\n}\\n"}'

    response = requests.get('http://www.suwonlib.go.kr:8080/api/unmannedLibrary/uLibraryBook/apply/info?libCode=141557&bookKey=1762351661', headers=headers)
    # print(response.json())
    response_json = response.json()
    for place in response_json['contents']['takePlaceList']:
        if place['devicePlace'] == '광교중앙역예약대출기':
            if place['resvPossibleYn'] == 'Y':
                print(place)
                # print(place['resvPossibleYn'] + "은 예약 가능합니다")
                delta = datetime.now() - lastNotify
                # print(delta.days)
                print(delta.total_seconds())
                if delta.total_seconds() > 3600:
                    toSlack(place['devicePlace'] + " 예약가능 상태입니다")
            else:
                lastNotify = datetime.now() - timedelta(days=1)
    #     else:
    #         print(room['name'] + "은 이미 예약되었습니다")


# checkLibrary()

#toSlack('HAHA')

while True:
    try:
        now = datetime.now()
        print(now)
        if 8 <= now.hour < 24:
            checkLibrary()
        time.sleep(10)
    except:
        print('error')


