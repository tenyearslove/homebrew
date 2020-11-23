import requests


def login():
    response = requests.post(
        url = 'http://www.suwonlib.go.kr:8080/api/user/login',
        data='{"userId":"coolove","userPw":"U2FsdGVkX19uW5BGbE1kugS6wYfvlJjmUX69f688egg=","encYn":"Y"}',
        headers={'Content-Type': 'application/json;charset=utf-8'})
    response_json = response.json()
    return response_json['contents']['userToken']


def search_by_keyword(keyword):
    print(f"keyword: {keyword}")
    response = requests.post(
        url = 'http://www.suwonlib.go.kr:8080/api/search',
        data=f'{{"searchKeyword":"{keyword}","pubFormCode":"MO","page":"1","display":"30","article":"SCORE","order":"DESC","manageCode":"ALL"}}'.encode('utf-8'),
        headers={'Content-Type': 'application/json;charset=utf-8'}

    )
    response_json = response.json()
    print(response_json)
    print(response_json["contents"]["bookList"])

    if response_json["contents"]["allTotalCount"] > 0:
        return response_json["contents"]["bookList"]
    else:
        return []


def unmanned_reservation(token, bookKey, libCode):
    print(f"{bookKey} {libCode} {token}")
    response = requests.post(
        url='http://www.suwonlib.go.kr:8080/api/unmannedLibrary/uLibraryBook/apply',
        data=f'{{"libCode":"{libCode}","bookKey":"{bookKey}","deviceCode":"GECOBOOKSLIB01","phoneNum":"010-4013-9992"}}'.encode(
            'utf-8'),
        headers={'Content-Type': 'application/json;charset=utf-8', 'x-access-token': token}
    )
    print(response.json())


def check_unmanned(token):
    response = requests.get(
        url = 'http://www.suwonlib.go.kr:8080/api/unmannedLibrary/uLibraryBook/apply/info?libCode=141557&bookKey=1762351661',
        headers = {'x-access-token': token}
    )
    response_json = response.json()
    print(response_json)
    for place in response_json['contents']['takePlaceList']:
        if place['devicePlace'] == '광교중앙역예약대출기':
            return place['resvPossibleYn'] == 'Y'


if __name__ == "__main__":
    token = login()
    result = search_by_keyword("Goddess Girls")
    unmanned_reservation(token, result[1]["bookKey"], result[1]["libCode"])
    print(check_unmanned(token))
