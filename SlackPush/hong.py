import requests, time, os

proxies = {
 "http": "http://168.219.61.252:8080",
 "https": "http://168.219.61.252:8080"
}

os.environ['REQUESTS_CA_BUNDLE'] = os.path.join(
    '/etc/ssl/certs/',
    'ca-certificates.crt')

def toSlack(roomname):
    headers = {
        'Content-type': 'application/json',
    }

    data = '{"text":"' + roomname + '"}'

    response = requests.post('https://hooks.slack.com/services/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',
                             headers=headers, data=data.encode('utf-8'), proxies=proxies)

while True:
    # try:
        headers = {
            'Sec-Fetch-Mode': 'cors',
            'Origin': 'https://pension.onda.me',
            'service': 'pension_bp',
            'locale': 'ko-KR',
            'User-Agent': 'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36',
            'Content-Type': 'application/json',
            'Accept': 'application/json, text/plain, */*',
            'Referer': 'https://pension.onda.me/bp/3226/calendar',
        }

        data = '{"operationName":"QueryCalendarList","variables":{"property_id":"3226","from":"2019-09-28","to":"2019-09-28"},"query":"query QueryCalendarList($property_id: ID!, $from: String!, $to: String!) {\\n  bpCalendarList(property_id: $property_id, from: $from, to: $to) {\\n    date\\n    daily_pricing {\\n      pricing_name\\n      is_before_holiday\\n    }\\n    roomtype_list {\\n      roomtype_id: id\\n      seq\\n      property_id\\n      bp_status\\n      name\\n      avail\\n      phone_inquiry\\n      basic_price\\n      sale_price\\n      min_los\\n      channel_rates {\\n        channel_id\\n        sale_price\\n      }\\n      reservation {\\n        name\\n        phone\\n        reservation_item_status\\n      }\\n    }\\n  }\\n  holidayList(from: $from, to: $to) {\\n    date\\n    name\\n  }\\n  property(property_id: $property_id) {\\n    id\\n    leadtime\\n    leadtime_status\\n  }\\n}\\n"}'
        response = requests.post('http://gql.tport.io/gql', headers=headers, data=data)
        print(response)
        response_json = response.json()

        for room in response_json['data']['bpCalendarList'][0]['roomtype_list']:
            # print(room)
            if room['avail'] == 1:
                print(room['name'] + "은 예약 가능합니다")
                toSlack(room['name'])
            else:
                print(room['name'] + "은 이미 예약되었습니다")

        time.sleep(5)
    # except:
    #     print('error')


