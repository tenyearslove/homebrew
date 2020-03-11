import json
import os

ChapterList = """[{ "grade": "11", "chapter": "1", "title": "9까지의 수", "visible": "f", "visible_calc": "f" },
	{ "grade": "11", "chapter": "2", "title": "여러 가지 모양", "visible": "f", "visible_calc": "f" }, {
		"grade": "11",
		"chapter": "3",
		"title": "덧셈과 뺄셈",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "11", "chapter": "4", "title": "비교하기", "visible": "f", "visible_calc": "f" }, { "grade": "11", "chapter": "5", "title": "50까지의 수", "visible": "f", "visible_calc": "f" }, {
		"grade": "12",
		"chapter": "1",
		"title": "100까지의 수",
		"visible": "f",
		"visible_calc": "f"
	}, { "grade": "12", "chapter": "2", "title": "덧셈과 뺄셈(1)", "visible": "f", "visible_calc": "t" }, { "grade": "12", "chapter": "3", "title": "여러 가지 모양", "visible": "f", "visible_calc": "f" }, {
		"grade": "12",
		"chapter": "4",
		"title": "덧셈과 뺄셈(2)",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "12", "chapter": "5", "title": "시계 보기와 규칙 찾기", "visible": "f", "visible_calc": "f" }, { "grade": "12", "chapter": "6", "title": "덧셈과 뺄셈(3)", "visible": "f", "visible_calc": "t" }, {
		"grade": "21",
		"chapter": "1",
		"title": "세 자리 수",
		"visible": "f",
		"visible_calc": "f"
	}, { "grade": "21", "chapter": "2", "title": "여러 가지 도형", "visible": "f", "visible_calc": "f" }, { "grade": "21", "chapter": "3", "title": "덧셈과 뺄셈", "visible": "f", "visible_calc": "t" }, {
		"grade": "21",
		"chapter": "4",
		"title": "길이 재기",
		"visible": "f",
		"visible_calc": "f"
	}, { "grade": "21", "chapter": "5", "title": "분류하기", "visible": "f", "visible_calc": "f" }, { "grade": "21", "chapter": "6", "title": "곱셈", "visible": "f", "visible_calc": "f" }, {
		"grade": "22",
		"chapter": "1",
		"title": "네 자리 수",
		"visible": "f",
		"visible_calc": "f"
	}, { "grade": "22", "chapter": "2", "title": "곱셈구구", "visible": "f", "visible_calc": "t" }, { "grade": "22", "chapter": "3", "title": "길이 재기", "visible": "f", "visible_calc": "f" }, {
		"grade": "22",
		"chapter": "4",
		"title": "시각과 시간",
		"visible": "f",
		"visible_calc": "f"
	}, { "grade": "22", "chapter": "5", "title": ">표와 그래프", "visible": "f", "visible_calc": "f" }, { "grade": "22", "chapter": "6", "title": "규칙찾기", "visible": "f", "visible_calc": "f" }, {
		"grade": "31",
		"chapter": "1",
		"title": "덧셈과 뺄셈",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "31", "chapter": "2", "title": "평면 도형", "visible": "f", "visible_calc": "f" }, { "grade": "31", "chapter": "3", "title": "나눗셈", "visible": "f", "visible_calc": "t" }, {
		"grade": "31",
		"chapter": "4",
		"title": "곱셈",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "31", "chapter": "5", "title": "길이와 시간", "visible": "f", "visible_calc": "f" }, { "grade": "31", "chapter": "6", "title": "분수와 소수", "visible": "f", "visible_calc": "f" }, {
		"grade": "32",
		"chapter": "1",
		"title": "곱셈",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "32", "chapter": "2", "title": "나눗셈", "visible": "f", "visible_calc": "t" }, { "grade": "32", "chapter": "3", "title": ">원", "visible": "f", "visible_calc": "f" }, {
		"grade": "32",
		"chapter": "4",
		"title": "분수",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "32", "chapter": "5", "title": "들이와 무게", "visible": "f", "visible_calc": "f" }, { "grade": "32", "chapter": "6", "title": "자료의 정리", "visible": "f", "visible_calc": "f" }, {
		"grade": "41",
		"chapter": "1",
		"title": "큰 수",
		"visible": "f",
		"visible_calc": "f"
	}, { "grade": "41", "chapter": "2", "title": "각도", "visible": "f", "visible_calc": "f" }, { "grade": "41", "chapter": "3", "title": "곱셈과 나눗셈", "visible": "f", "visible_calc": "t" }, {
		"grade": "41",
		"chapter": "4",
		"title": "평면도형의 이동",
		"visible": "f",
		"visible_calc": "f"
	}, { "grade": "41", "chapter": "5", "title": "막대그래프", "visible": "f", "visible_calc": "f" }, { "grade": "41", "chapter": "6", "title": "규칙찾기", "visible": "f", "visible_calc": "f" }, {
		"grade": "42",
		"chapter": "1",
		"title": "분수의 덧셈과 뺄셈",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "42", "chapter": "2", "title": "삼각형", "visible": "f", "visible_calc": "f" }, { "grade": "42", "chapter": "3", "title": "소수의 덧셈과 뺄셈", "visible": "f", "visible_calc": "t" }, {
		"grade": "42",
		"chapter": "4",
		"title": "사각형",
		"visible": "f",
		"visible_calc": "f"
	}, { "grade": "42", "chapter": "5", "title": "꺾은선그래프", "visible": "f", "visible_calc": "f" }, { "grade": "42", "chapter": "6", "title": "다각형", "visible": "f", "visible_calc": "f" }, {
		"grade": "51",
		"chapter": "1",
		"title": "자연수의 혼합계산",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "51", "chapter": "2", "title": "약수와 배수", "visible": "f", "visible_calc": "t" }, { "grade": "51", "chapter": "3", "title": "규칙과 대응", "visible": "f", "visible_calc": "f" }, {
		"grade": "51",
		"chapter": "4",
		"title": "약분과 통분",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "51", "chapter": "5", "title": "분수의 덧셈과 뺄셈", "visible": "f", "visible_calc": "t" }, { "grade": "51", "chapter": "6", "title": "다각형의 둘레와 넓이", "visible": "f", "visible_calc": "f" }, {
		"grade": "52",
		"chapter": "1",
		"title": "수의 범위와 어림하기",
		"visible": "f",
		"visible_calc": "f"
	}, { "grade": "52", "chapter": "2", "title": "분수의 곱셈", "visible": "f", "visible_calc": "f" }, { "grade": "52", "chapter": "3", "title": "합동과 대>칭", "visible": "f", "visible_calc": "f" }, {
		"grade": "52",
		"chapter": "4",
		"title": "소수의 곱셈",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "52", "chapter": "5", "title": "직육면체", "visible": "f", "visible_calc": "f" }, { "grade": "52", "chapter": "6", "title": "평균과 가능성", "visible": "f", "visible_calc": "f" }, {
		"grade": "61",
		"chapter": "1",
		"title": "분수의 나눗셈",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "61", "chapter": "2", "title": "각기둥과 각뿔", "visible": "f", "visible_calc": "f" }, { "grade": "61", "chapter": "3", "title": "소수의 나눗셈", "visible": "f", "visible_calc": "t" }, {
		"grade": "61",
		"chapter": "4",
		"title": "비와 비율",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "61", "chapter": "5", "title": "여러 가지 그래프", "visible": "f", "visible_calc": "f" }, { "grade": "61", "chapter": "6", "title": "직육면체의 부피와 겉넓이", "visible": "f", "visible_calc": "f" }, {
		"grade": "62",
		"chapter": "1",
		"title": "분수의 나눗셈",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "62", "chapter": "2", "title": "소수의 나눗셈", "visible": "f", "visible_calc": "t" }, { "grade": "62", "chapter": "3", "title": "공간과 입체", "visible": "f", "visible_calc": "f" }, {
		"grade": "62",
		"chapter": "4",
		"title": "비례식과 비례배분",
		"visible": "f",
		"visible_calc": "t"
	}, { "grade": "62", "chapter": "5", "title": "원의 넓이", "visible": "f", "visible_calc": "f" }, { "grade": "62", "chapter": "6", "title": "원기둥, 원뿔, 구", "visible": "f", "visible_calc": "f" }]"""

StepList = """[{ "idx": "5", "grade": "11", "chapter": "3", "no": "1", "title": "두 수로 가르기(한 자리 수)", "page": "p50, 52, 54, 56", "description": "한 자리 수를 둘로 나누어 뺄셈의 기본 개념을 익힙니다." }, {
	"idx": "4",
	"grade": "11",
	"chapter": "3",
	"no": "2",
	"title": "두 수를 모으기(한 자리 수)",
	"page": "p51, 53, 55, 57",
	"description": "한 자리 수 두 개를 모은 수를 살펴 보아 덧셈의 기본 개념을 익힙니다."
}, { "idx": "6", "grade": "11", "chapter": "3", "no": "3", "title": "몇 + 몇 (합이 9이하)", "page": "p60~62", "description": "두 수를 더한 값이 9이하인 덧셈을 연습합니다." }, {
	"idx": "7",
	"grade": "11",
	"chapter": "3",
	"no": "4",
	"title": "몇 - 몇 (차가 9이하)",
	"page": "p63~67",
	"description": "두 수를 뺀 값이 9이하인 뺄셈을 연습합니다."
}, { "idx": "8", "grade": "11", "chapter": "3", "no": "5", "title": "한 자리 수 덧셈과 뺄셈의 관계", "page": "p68", "description": "한 자리 수끼리의 덧셈식을 보고 뺄셈식을 만드는 연습을 합니다." }, {
	"idx": "9",
	"grade": "11",
	"chapter": "3",
	"no": "6",
	"title": "한 자리 수 뺄셈과 덧셈의 관계",
	"page": "p69",
	"description": "한 자리 수끼리의 뺄셈식을 보고 덧셈식을 만드는 연습을 합니다."
}, { "idx": "10", "grade": "11", "chapter": "3", "no": "7", "title": "한 자리 수 덧셈/뺄셈에서 @box안의 수 찾기", "page": "p72~73", "description": "한 자리 수끼리의 덧셈과 뺄셈에서 ☐안의 숫자를 알아내는 연습을 합니다." }, {
	"idx": "19",
	"grade": "12",
	"chapter": "2",
	"no": "1",
	"title": "몇십 + 몇 (받아올림 없음) A",
	"page": "p56~57",
	"description": "받아올림이 없는 몇십과 몇의 합을 세로식으로 연습합니다."
}, { "idx": "18", "grade": "12", "chapter": "2", "no": "2", "title": "몇십 + 몇 (받아올림 없음) B", "page": "p56~57", "description": "받아올림이 없는 몇십과 몇의 합을 가로식으로 연습합니다." }, {
	"idx": "21",
	"grade": "12",
	"chapter": "2",
	"no": "3",
	"title": "몇십 몇 + 몇 (받아올림 없음) A",
	"page": "p58~59",
	"description": "받아올림이 없는 몇십 몇과 몇의 합을 세로식으로 연습합니다."
}, { "idx": "20", "grade": "12", "chapter": "2", "no": "4", "title": "몇십 몇 + 몇 (받아올림 없음) B", "page": "p58~59", "description": "받아올림이 없는 몇십 몇과 몇의 합을 가로식으로 연습합니다." }, {
	"idx": "23",
	"grade": "12",
	"chapter": "2",
	"no": "5",
	"title": "몇십 + 몇십 (받아올림 없음) A",
	"page": "p60",
	"description": "받아올림이 없는 몇십과 몇십의 합을 세로식으로 연습합니다."
}, { "idx": "22", "grade": "12", "chapter": "2", "no": "6", "title": "몇십 + 몇십 (받아올림 없음) B", "page": "p60", "description": "받아올림이 없는 몇십과 몇십의 합을 가로식으로 연습합니다." }, {
	"idx": "25",
	"grade": "12",
	"chapter": "2",
	"no": "7",
	"title": "몇십 몇 + 몇십 몇 (받아올림 없음) A",
	"page": "p61",
	"description": "받아올림이 없는 몇십 몇과 몇십 몇의 합을 세로식으로 연습합니다."
}, { "idx": "24", "grade": "12", "chapter": "2", "no": "8", "title": "몇십 몇 + 몇십 몇 (받아올림 없음) B", "page": "p61", "description": "받아올림이 없는 몇십 몇과 몇십 몇의 합을 가로식으로 연습합니다." }, {
	"idx": "29",
	"grade": "12",
	"chapter": "2",
	"no": "9",
	"title": "몇십 - 몇십 (받아내림 없음) A",
	"page": "p64",
	"description": "받아내림이 없는 몇십과 몇십의 차를 세로식으로 연습합니다."
}, { "idx": "28", "grade": "12", "chapter": "2", "no": "10", "title": "몇십 - 몇십 (받아내림 없음) B", "page": "p64", "description": "받아내림이 없는 몇십과 몇십의 차를 가로식으로 연습합니다." }, {
	"idx": "27",
	"grade": "12",
	"chapter": "2",
	"no": "11",
	"title": "몇십 몇 - 몇 (받아내림 없음) A",
	"page": "p62~63",
	"description": "받아내림이 없는 몇십 몇과 몇의 차를 세로식으로 연습합니다."
}, { "idx": "26", "grade": "12", "chapter": "2", "no": "12", "title": "몇십 몇 - 몇 (받아내림 없음) B", "page": "p62~63", "description": "받아내림이 없는 몇십 몇과 몇의 차를 가로식으로 연습합니다." }, {
	"idx": "31",
	"grade": "12",
	"chapter": "2",
	"no": "13",
	"title": "몇십 몇 - 몇십 몇 (받아내림 없음) A",
	"page": "p65",
	"description": "받아내림이 없는 몇십 몇과 몇십 몇의 차를 세로식으로 연습합니다."
}, { "idx": "30", "grade": "12", "chapter": "2", "no": "14", "title": "몇십 몇 - 몇십 몇 (받아내림 없음) B", "page": "p65", "description": "받아내림이 없는 몇십 몇과 몇십 몇의 차를 가로식으로 연습합니다." }, {
	"idx": "32",
	"grade": "12",
	"chapter": "2",
	"no": "15",
	"title": "두 자리 수 덧셈과 뺄셈의 관계",
	"page": "p66",
	"description": "두 자리 수들의 덧셈식을 보고 뺄셈식을 만드는 연습을 합니다."
}, { "idx": "33", "grade": "12", "chapter": "2", "no": "16", "title": " 두 자리 수 뺄셈과 덧셈의 관계", "page": "p67", "description": "두 자리 수들의 뺄셈식을 보고 덧셈식을 만드는 연습을 합니다." }, {
	"idx": "15",
	"grade": "12",
	"chapter": "4",
	"no": "1",
	"title": "몇 + 몇 + 몇 (합이 9이하)",
	"page": "p52~55",
	"description": "합이 10을 넘지 않는 세 수의 덧셈을 연습합니다."
}, { "idx": "16", "grade": "12", "chapter": "4", "no": "2", "title": "몇 - 몇 - 몇 (차가 9이하)", "page": "p52~55", "description": "한 자리 수 세 개의 뺄셈을 연습합니다." }, {
	"idx": "17",
	"grade": "12",
	"chapter": "4",
	"no": "3",
	"title": "몇 + 몇 - 몇 / 몇 - 몇 + 몇 (한 자리 수)",
	"page": "p52~55",
	"description": "세 수의 계산 결과가 한 자리 수인 덧셈과 뺄셈이 혼합된 계산을 연습합니다."
}, { "idx": "11", "grade": "12", "chapter": "4", "no": "4", "title": "10을 두 수로 가르기", "page": "p36~37", "description": "10을 두수로 가르는 문제를 통해 이후 받아내림이 있는 뺄셈을 잘 할 수 있도록  연습합니다." }, {
	"idx": "12",
	"grade": "12",
	"chapter": "4",
	"no": "5",
	"title": "10이 되도록 두 수를 모으기",
	"page": "p38~39",
	"description": "두 수를 모아 10을 만드는 문제를 통해 이후 받아올림이 있는 덧셈을 잘 할 수 있도록 연습합니다."
}, { "idx": "13", "grade": "12", "chapter": "4", "no": "6", "title": "10이 되는 더하기", "page": "p40~43", "description": "두 수를 모아 10을 만드는 것을 식을 통해 연습합니다." }, {
	"idx": "14",
	"grade": "12",
	"chapter": "4",
	"no": "7",
	"title": "10에서 빼기",
	"page": "p44~47",
	"description": "10을 두 수로 가르는 것을 식을 통해 연습합니다."
}, { "idx": "34", "grade": "12", "chapter": "4", "no": "8", "title": "두 수의 합이 10이 되는 세 수의 덧셈 A", "page": "p82~83", "description": "받아올림이 있는 덧셈을 연습하기 위해 합이 10이 되는 두 수가 표시되어 있을 때 그 수들을 먼저 더하고 남은 수를 더하는 연습을 합니다." }, {
	"idx": "35",
	"grade": "12",
	"chapter": "4",
	"no": "9",
	"title": "두 수의 합이 10이 되는 세 수의 덧셈 B",
	"page": "p82~83",
	"description": "받아올림이 있는 덧셈을 연습하기 위해 합이 10이 되는 두 수를 찾아서 그 수들을 먼저 더하고 남은 수를 더하는 연습을 합니다."
}, { "idx": "277", "grade": "12", "chapter": "5", "no": "1", "title": "시계에서 몇 시를 읽고 그리기", "page": "p72~73", "description": "몇 시 정각을 시계를 보고 읽고, 몇 시 정각에 해당하는 시간을 시계에 표현하는 연습을 합니다." }, {
	"idx": "278",
	"grade": "12",
	"chapter": "5",
	"no": "2",
	"title": "시계에서 몇 시 30분을 읽고 그리기",
	"page": "p74~75",
	"description": "몇 시 30분을 시계를 보고 읽고, 몇 시 30분에 해당하는 시간을 시계에 표현하는 연습을 합니다."
}, { "idx": "37", "grade": "12", "chapter": "6", "no": "1", "title": "몇 + 몇 (받아올림 있음) A", "page": "p84~87", "description": "받아올림이 있는 한 자리 수 덧셈을 세로식으로 연습합니다." }, {
	"idx": "36",
	"grade": "12",
	"chapter": "6",
	"no": "2",
	"title": "몇 + 몇 (받아올림 있음) B",
	"page": "p84~87",
	"description": "받아올림이 있는 한 자리 수 덧셈을 가로식으로 연습합니다."
}, { "idx": "43", "grade": "12", "chapter": "6", "no": "3", "title": "몇 + 몇 + 몇 (받아올림 있음)", "page": "p92", "description": "받아올림이 있는 한 자리 수 세 개의 덧셈을 연습합니다." }, {
	"idx": "39",
	"grade": "12",
	"chapter": "6",
	"no": "4",
	"title": "십몇 - 몇 (받아내림 있음) A",
	"page": "p88~91",
	"description": "받아내림이 있는 십몇 - 몇을 세로식으로 연습합니다."
}, { "idx": "38", "grade": "12", "chapter": "6", "no": "5", "title": "십몇 - 몇 (받아내림 있음) B", "page": "p88~91", "description": "받아내림이 있는 십몇 - 몇을 가로식으로 연습합니다." }, {
	"idx": "44",
	"grade": "12",
	"chapter": "6",
	"no": "6",
	"title": "십몇 - 몇 - 몇 (받아내림 있음)",
	"page": "p93",
	"description": "받아내림이 있는 세 수의(십몇 - 몇 - 몇) 뺄셈을 연습합니다."
}, { "idx": "42", "grade": "12", "chapter": "6", "no": "7", "title": "몇 + 몇 / 십몇 - 몇  A", "page": "p94~95", "description": "받아올림이 있는 한 자리 수 덧셈, 받아내림이 있는 십몇 - 몇을 세로식으로 연습합니다." }, {
	"idx": "41",
	"grade": "12",
	"chapter": "6",
	"no": "8",
	"title": "몇 + 몇 / 십몇 - 몇 B",
	"page": "p94~95",
	"description": "받아올림이 있는 한 자리 수 덧셈, 받아내림이 있는 십몇 - 몇을 가로식으로 연습합니다."
}, { "idx": "45", "grade": "21", "chapter": "3", "no": "1", "title": "몇십 몇 + 몇 (받아올림 있음) A", "page": "p22~23", "description": "받아올림이 있는 몇십 + 몇을 세로식으로 연습합니다." }, {
	"idx": "46",
	"grade": "21",
	"chapter": "3",
	"no": "2",
	"title": "몇십 몇 + 몇 (받아올림 있음) B",
	"page": "p22~23",
	"description": "받아올림이 있는 몇십 + 몇을 가로식으로 연습합니다."
}, { "idx": "55", "grade": "21", "chapter": "3", "no": "3", "title": "몇십 몇 + 몇십 몇 (받아올림 1번) A", "page": "p52~53", "description": "받아올림이 한 번 있는 몇십 몇 + 몇십 몇을 세로식으로 연습합니다." }, {
	"idx": "56",
	"grade": "21",
	"chapter": "3",
	"no": "4",
	"title": "몇십 몇 + 몇십 몇 (받아올림 1번) B",
	"page": "p52~53",
	"description": "받아올림이 한 번 있는 몇십 몇 + 몇십 몇을 가로식으로 연습합니다."
}, { "idx": "57", "grade": "21", "chapter": "3", "no": "5", "title": "몇십 몇 + 몇십 몇 (받아올림 2번) A", "page": "p54", "description": "받아올림이 두 번 있는 몇십 몇 + 몇십 몇을 세로식으로 연습합니다." }, {
	"idx": "58",
	"grade": "21",
	"chapter": "3",
	"no": "6",
	"title": "몇십 몇 + 몇십 몇 (받아올림 2번) B",
	"page": "p54",
	"description": "받아올림이 두 번 있는 몇십 몇 + 몇십 몇을 가로식으로 연습합니다."
}, { "idx": "47", "grade": "21", "chapter": "3", "no": "7", "title": "몇십 몇 - 몇 (받아내림 있음) A", "page": "p24~25", "description": "받아내림이 있는 몇십 몇 - 몇을 세로식으로 연습합니다." }, {
	"idx": "48",
	"grade": "21",
	"chapter": "3",
	"no": "8",
	"title": "몇십 몇 - 몇 (받아내림 있음) B",
	"page": "p24~25",
	"description": "받아내림이 있는 몇십 몇 - 몇을 가로식으로 연습합니다."
}, { "idx": "59", "grade": "21", "chapter": "3", "no": "9", "title": "몇십 몇 - 몇십 몇 (받아내림 1번) A", "page": "p55~56", "description": "받아내림이 한 번 있는 몇십 몇 - 몇십 몇을 세로식으로 연습합니다." }, {
	"idx": "60",
	"grade": "21",
	"chapter": "3",
	"no": "10",
	"title": "몇십 몇 - 몇십 몇 (받아내림 1번) B",
	"page": "p55~56",
	"description": "받아내림이 한 번 있는 몇십 몇 - 몇십 몇을 가로식으로 연습합니다."
}, { "idx": "49", "grade": "21", "chapter": "3", "no": "11", "title": "몇십 몇 ± 몇 (받아올림/내림 있음) A", "page": "p28~29", "description": "받아올림이 있는 몇십 몇 + 몇 또는 받아내림이 있는 몇십 몇 - 몇을 세로식으로 연습합니다." }, {
	"idx": "50",
	"grade": "21",
	"chapter": "3",
	"no": "12",
	"title": "몇십 몇 ± 몇 (받아올림/내림 있음) B",
	"page": "p28~29",
	"description": "받아올림이 있는 몇십 몇 + 몇 또는 받아내림이 있는 몇십 몇 - 몇을 가로식으로 연습합니다."
}, { "idx": "61", "grade": "21", "chapter": "3", "no": "13", "title": "몇십 몇 ± 몇십 몇  A", "page": "p52~56", "description": "받아올림/받아내림이 있는 두 자리 수의 덧셈/뺄셈을 세로식으로 연습합니다." }, {
	"idx": "62",
	"grade": "21",
	"chapter": "3",
	"no": "14",
	"title": "몇십 몇 ± 몇십 몇  B",
	"page": "p52~56",
	"description": "받아올림/받아내림이 있는 두 자리 수의 덧셈/뺄셈을 가로식으로 연습합니다."
}, { "idx": "51", "grade": "21", "chapter": "3", "no": "15", "title": "몇십 몇 + 몇 + 몇 (받아올림 있음)", "page": "p26", "description": "몇십 몇 + 몇 + 몇 형태의 세 수의 덧셈을 연습합니다." }, {
	"idx": "52",
	"grade": "21",
	"chapter": "3",
	"no": "16",
	"title": "몇십 몇 - 몇 - 몇 (받아내림 있음)",
	"page": "p27",
	"description": "몇십 몇 - 몇 - 몇 형태의 세 수의 뺄셈을 연습합니다."
}, { "idx": "53", "grade": "21", "chapter": "3", "no": "17", "title": "몇십 몇 + 몇 - 몇 / 몇십 몇 - 몇 + 몇 ", "page": "p28~29", "description": "몇십 몇 + 몇 - 몇 또는 몇십 몇 - 몇 + 몇과 같이 더하기와 빼기가 한 번씩 나오는 세 수의 계산을 연습합니다." }, {
	"idx": "54",
	"grade": "21",
	"chapter": "3",
	"no": "18",
	"title": "몇십 몇 ± 몇 ± 몇 ",
	"page": "p28~29",
	"description": "세 수를 더하거나 빼는 것이 혼합되어 있는 계산을 연습합니다."
}, { "idx": "63", "grade": "21", "chapter": "3", "no": "19", "title": "몇십 몇 ± 몇십 몇 ± 몇십 몇", "page": "p60~61", "description": "받아올림/받아내림이 있는 세 개의 두 자리 수들의 덧셈/뺄셈 혼합 계산을 연습합니다." }, {
	"idx": "64",
	"grade": "22",
	"chapter": "2",
	"no": "1",
	"title": "2, 5, 3, 4의 단 곱셈구구",
	"page": "p4~9",
	"description": "2단, 5단, 3단, 4단의 곱셈구구를 연습합니다."
}, { "idx": "65", "grade": "22", "chapter": "2", "no": "2", "title": "6, 7, 8, 9의 단 곱셈구구", "page": "p10~15", "description": "6단, 7단, 8단, 9단의 곱셈구구를 연습합니다." }, {
	"idx": "66",
	"grade": "22",
	"chapter": "2",
	"no": "3",
	"title": "1의 단, 0의 단을 포함한 곱셈구구",
	"page": "p4~17",
	"description": "0단, 1단을 포함한 곱셈구구를 연습합니다."
}, { "idx": "67", "grade": "22", "chapter": "2", "no": "4", "title": "곱셈구구에서 @box안의 수 찾기 A", "page": "p4~17", "description": "곱셈구구 식에서 ☐가 오른쪽에 있을 때, ☐안의 숫자를 알아내는 연습을 합니다." }, {
	"idx": "68",
	"grade": "22",
	"chapter": "2",
	"no": "5",
	"title": "곱셈구구에서 @box안의 수 찾기 B",
	"page": "p4~17",
	"description": "곱셈구구 식에서 ☐가 왼쪽에 있을 때, ☐안의 숫자를 알아내는 연습을 합니다."
}, { "idx": "69", "grade": "22", "chapter": "2", "no": "6", "title": "곱셈구구에서 @box안의 수 찾기 C", "page": "p4~17", "description": "곱셈구구 식에서 ☐안의 숫자를 알아내는 연습을 합니다." }, {
	"idx": "70",
	"grade": "31",
	"chapter": "1",
	"no": "1",
	"title": "몇백 / 몇백 몇십의 덧셈 (받아올림 없음) A",
	"page": "p24~26",
	"description": "받아올림이 없는 몇백 + 몇백, 또는 몇백 몇십 + 몇백 몇십을 세로식으로 연습합니다."
}, { "idx": "71", "grade": "31", "chapter": "1", "no": "2", "title": "몇백 / 몇백 몇십의 덧셈 (받아올림 없음) B", "page": "p24~26", "description": "받아올림이 없는 몇백 + 몇백, 또는 몇백 몇십 + 몇백 몇십을 가로식으로 연습합니다." }, {
	"idx": "72",
	"grade": "31",
	"chapter": "1",
	"no": "3",
	"title": "몇백 몇십 몇의 덧셈 (받아올림 없음) A",
	"page": "p27~28",
	"description": "받아올림이 없는 몇백 몇십 몇 + 몇백 몇십 몇을 세로식으로 연습합니다."
}, { "idx": "73", "grade": "31", "chapter": "1", "no": "4", "title": "몇백 몇십 몇의 덧셈 (받아올림 없음) B", "page": "p27~28", "description": "받아올림이 없는 몇백 몇십 몇 + 몇백 몇십 몇을 가로식으로 연습합니다." }, {
	"idx": "79",
	"grade": "31",
	"chapter": "1",
	"no": "5",
	"title": "몇백 몇십 몇의 덧셈 (받아올림 1번) A",
	"page": "p54~57",
	"description": "받아올림이 1번 있는 몇백 몇십 몇 + 몇백 몇십 몇을 세로식으로 연습합니다."
}, { "idx": "80", "grade": "31", "chapter": "1", "no": "6", "title": "몇백 몇십 몇의 덧셈 (받아올림 1번) B", "page": "p54~57", "description": "받아올림이 1번 있는 몇백 몇십 몇 + 몇백 몇십 몇을 가로식으로 연습합니다." }, {
	"idx": "81",
	"grade": "31",
	"chapter": "1",
	"no": "7",
	"title": "몇백 몇십 몇의 덧셈 (받아올림 2번) A",
	"page": "p58~60",
	"description": "받아올림이 2번 있는 몇백 몇십 몇 + 몇백 몇십 몇을 세로식으로 연습합니다."
}, { "idx": "82", "grade": "31", "chapter": "1", "no": "8", "title": "몇백 몇십 몇의 덧셈 (받아올림 2번) B", "page": "p58~60", "description": "받아올림이 2번 있는 몇백 몇십 몇 + 몇백 몇십 몇을 가로식으로 연습합니다." }, {
	"idx": "91",
	"grade": "31",
	"chapter": "1",
	"no": "9",
	"title": "몇백 몇십 몇의 덧셈 (받아올림 3번) A",
	"page": "p20~21",
	"description": "받아올림이 3번 있는 몇백 몇십 몇 + 몇백 몇십 몇을 세로식으로 연습합니다."
}, { "idx": "92", "grade": "31", "chapter": "1", "no": "10", "title": "몇백 몇십 몇의 덧셈 (받아올림 3번) B", "page": "p20~21", "description": "받아올림이 3번 있는 몇백 몇십 몇 + 몇백 몇십 몇을 가로식으로 연습합니다." }, {
	"idx": "111",
	"grade": "31",
	"chapter": "1",
	"no": "11",
	"title": "네 자리 수 + 세 자리 수 A",
	"page": "p4~5",
	"description": "네 자리 수 + 세 자리 수를 세로식으로 연습합니다."
}, { "idx": "112", "grade": "31", "chapter": "1", "no": "12", "title": "네 자리 수 + 세 자리 수 B", "page": "p4~5", "description": "네 자리 수 + 세 자리 수를 가로식으로 연습합니다." }, {
	"idx": "113",
	"grade": "31",
	"chapter": "1",
	"no": "13",
	"title": "네 자리 수 + 네 자리 수 A",
	"page": "p6~7",
	"description": "네 자리 수 + 네 자리 수를 세로식으로 연습합니다."
}, { "idx": "114", "grade": "31", "chapter": "1", "no": "14", "title": "네 자리 수 + 네 자리 수 B", "page": "p6~7", "description": "네 자리 수 + 네 자리 수를 가로식으로 연습합니다." }, {
	"idx": "74",
	"grade": "31",
	"chapter": "1",
	"no": "15",
	"title": "몇백 / 몇백 몇십의 뺄셈 (받아내림 없음) A",
	"page": "p29~31",
	"description": "받아내림이 없는 몇백 - 몇백, 또는 몇백 몇십 - 몇백 몇십을 세로식으로 연습합니다."
}, { "idx": "75", "grade": "31", "chapter": "1", "no": "16", "title": "몇백 / 몇백 몇십의 뺄셈 (받아내림 없음) B", "page": "p29~31", "description": "받아내림이 업는 몇백 - 몇백, 또는 몇백 몇십 - 몇백 몇십을 가로식으로 연습합니다." }, {
	"idx": "76",
	"grade": "31",
	"chapter": "1",
	"no": "17",
	"title": "몇백 몇십 몇의 뺄셈 (받아내림 없음) A",
	"page": "p32~33",
	"description": "받아내림이 없는 몇백 몇십 몇 - 몇백 몇십 몇을 세로식으로 연습합니다."
}, { "idx": "77", "grade": "31", "chapter": "1", "no": "18", "title": "몇백 몇십 몇의 뺄셈 (받아내림 없음) B", "page": "p32~33", "description": "받아내림이 없는 몇백 몇십 몇 - 몇백 몇십 몇을 가로식으로 연습합니다." }, {
	"idx": "84",
	"grade": "31",
	"chapter": "1",
	"no": "19",
	"title": "몇백 몇십 몇의 뺄셈 (받아내림 1번) A",
	"page": "p61~64",
	"description": "받아내림이 1번 있는 몇백 몇십 몇 - 몇백 몇십 몇을 세로식으로 연습합니다."
}, { "idx": "85", "grade": "31", "chapter": "1", "no": "20", "title": "몇백 몇십 몇의 뺄셈 (받아내림 1번) B", "page": "p61~64", "description": "받아내림이 1번 있는 몇백 몇십 몇 - 몇백 몇십 몇을 가로식으로 연습합니다." }, {
	"idx": "86",
	"grade": "31",
	"chapter": "1",
	"no": "21",
	"title": "몇백 몇십 몇의 뺄셈 (받아내림 2번) A",
	"page": "p65~67",
	"description": "받아내림이 2번 있는 몇백 몇십 몇 - 몇백 몇십 몇을 세로식으로 연습합니다."
}, { "idx": "87", "grade": "31", "chapter": "1", "no": "22", "title": "몇백 몇십 몇의 뺄셈 (받아내림 2번) B", "page": "p65~67", "description": "받아내림이 2번 있는 몇백 몇십 몇 - 몇백 몇십 몇을 가로식으로 연습합니다." }, {
	"idx": "115",
	"grade": "31",
	"chapter": "1",
	"no": "23",
	"title": "네 자리 수 - 세 자리 수 A",
	"page": "p8~9",
	"description": "네 자리 수 - 세 자리 수를 세로식으로 연습합니다."
}, { "idx": "116", "grade": "31", "chapter": "1", "no": "24", "title": "네 자리 수 - 세 자리 수 B", "page": "p8~9", "description": "네 자리 수 - 세 자리 수를 세로식으로 연습합니다." }, {
	"idx": "117",
	"grade": "31",
	"chapter": "1",
	"no": "25",
	"title": "네 자리 수 - 네 자리 수 A",
	"page": "p10~11",
	"description": "네 자리 수 - 네 자리 수를 세로식으로 연습합니다."
}, { "idx": "118", "grade": "31", "chapter": "1", "no": "26", "title": "네 자리 수 - 네 자리 수 B", "page": "p10~11", "description": "네 자리 수 - 네 자리 수를 가로식으로 연습합니다." }, {
	"idx": "78",
	"grade": "31",
	"chapter": "1",
	"no": "27",
	"title": "몇백 몇십 몇 ± 몇백 몇십 몇  A",
	"page": "p36~37",
	"description": "받아내림이 없는 몇백 몇십 몇 + 몇백 몇십 몇, 또는 몇백 몇십 몇 - 몇백 몇십 몇을 세로식으로 연습합니다."
}, { "idx": "83", "grade": "31", "chapter": "1", "no": "28", "title": "몇백 몇십 몇 ± 몇백 몇십 몇  B", "page": "p36~37", "description": "받아내림이 없는 몇백 몇십 몇 + 몇백 몇십 몇, 또는 몇백 몇십 몇 - 몇백 몇십 몇을 세로식으로 연습합니다." }, {
	"idx": "119",
	"grade": "31",
	"chapter": "1",
	"no": "29",
	"title": "네 자리 수 ± 네 자리 수 A",
	"page": "p6~7, 10~11",
	"description": "네 자리 수 ± 네 자리 수를 세로식으로 연습합니다."
}, { "idx": "120", "grade": "31", "chapter": "1", "no": "30", "title": "네 자리 수 ± 네 자리 수 B", "page": "p6~7, 10~11", "description": "네 자리 수 ± 네 자리 수를 가로식으로 연습합니다." }, {
	"idx": "90",
	"grade": "31",
	"chapter": "1",
	"no": "31",
	"title": "세 수의 덧셈/뺄셈 (세 자리 수)",
	"page": "p68~69",
	"description": "받아올림/받아내림이 있는 세 개의 세 자리 수들의 덧셈/뺄셈 혼합 계산을 연습합니다."
}, { "idx": "121", "grade": "31", "chapter": "1", "no": "32", "title": "세 수의 덧셈/뺄셈 (네 자리 수)", "page": "p12~13", "description": "세 개의 네 자리 수들의 덧셈/뺄셈 혼합 계산을 연습합니다." }, {
	"idx": "93",
	"grade": "31",
	"chapter": "3",
	"no": "1",
	"title": "나눗셈의 기초",
	"page": "p48~51",
	"description": "나누는 수를 몇 번 빼서 0이 될 때, 그 회수가 나눗셈의 몫이 되는 나눗셈의 개념을 익힙니다."
}, { "idx": "94", "grade": "31", "chapter": "3", "no": "2", "title": "곱셈과 나눗셈의 관계", "page": "p58~59", "description": "두 수의 곱셈에서, 곱셈한 결과를 한 수로 나눈 결과는 다른 한 수가 된다는 개념을 익힙니다." }, {
	"idx": "95",
	"grade": "31",
	"chapter": "3",
	"no": "3",
	"title": "나눗셈과 곱셈의 관계",
	"page": "p58~59",
	"description": "두 수의 나눗셈에서, 나누는 수와 몫을 곱하면 나누어지는 수가 되는 개념을 익힙니다. "
}, { "idx": "96", "grade": "31", "chapter": "3", "no": "4", "title": "곱셈구구 내에서의 나눗셈 A", "page": "p60~65", "description": "곱셈을 활용하여 나눗셈의 몫을 구하는 것을 세로식으로 연습합니다." }, {
	"idx": "97",
	"grade": "31",
	"chapter": "3",
	"no": "5",
	"title": "곱셈구구 내에서의 나눗셈 B",
	"page": "p60~65",
	"description": "곱셈을 활용하여 나눗셈의 몫을 구하는 것을 가로식으로 연습합니다."
}, { "idx": "98", "grade": "31", "chapter": "4", "no": "1", "title": "몇십 X 몇 A", "page": "p82~83", "description": "몇십 X 몇을 세로식으로 연습합니다." }, {
	"idx": "99",
	"grade": "31",
	"chapter": "4",
	"no": "2",
	"title": "몇십 X 몇 B",
	"page": "p82~83",
	"description": "몇십 X 몇을 가로식으로 연습합니다."
}, { "idx": "100", "grade": "31", "chapter": "4", "no": "3", "title": "몇십 몇 X 몇 (올림 없음) A", "page": "p84~85", "description": "올림이 없는 몇십 몇 X 몇을 세로식으로 연습합니다." }, {
	"idx": "101",
	"grade": "31",
	"chapter": "4",
	"no": "4",
	"title": "몇십 몇 X 몇 (올림 없음) B",
	"page": "p84~85",
	"description": "올림이 없는 몇십 몇 X 몇을 가로식으로 연습합니다."
}, { "idx": "102", "grade": "31", "chapter": "4", "no": "5", "title": "몇십 몇 X 몇 (십의 자리 올림 있음) A", "page": "p86~87", "description": "십의 자리에서 올림이 있는 몇십 몇 X 몇을 세로식으로 연습합니다. " }, {
	"idx": "103",
	"grade": "31",
	"chapter": "4",
	"no": "6",
	"title": "몇십 몇 X 몇 (십의 자리 올림 없음) B",
	"page": "p86~87",
	"description": "십의 자리에서 올림이 있는 몇십 몇 X 몇을 가로식으로 연습합니다. "
}, { "idx": "104", "grade": "31", "chapter": "4", "no": "7", "title": "몇십 몇 X 몇 (일의 자리에서 올림) A", "page": "p88~89", "description": "일의 자리에서 올림이 있는 몇십 몇 X 몇을 세로식으로 연습합니다." }, {
	"idx": "105",
	"grade": "31",
	"chapter": "4",
	"no": "8",
	"title": "몇십 몇 X 몇 (일의 자리에서 올림) B",
	"page": "p88~89",
	"description": "일의 자리에서 올림이 있는 몇십 몇 X 몇을 가로식으로 연습합니다."
}, { "idx": "107", "grade": "31", "chapter": "4", "no": "9", "title": "몇십 몇 X 몇 (십, 일의 자리 올림 있음) A", "page": "p92~93", "description": "십의 자리와 일의 자리에서 모두 올림이 있는 몇십 몇 X 몇을 세로식으로 연습합니다." }, {
	"idx": "108",
	"grade": "31",
	"chapter": "4",
	"no": "10",
	"title": "몇십 몇 X 몇 (십, 일의 자리 올림 있음) B",
	"page": "p92~93",
	"description": "십의 자리와 일의 자리에서 모두 올림이 있는 몇십 몇 X 몇을 가로식으로 연습합니다."
}, { "idx": "109", "grade": "31", "chapter": "4", "no": "11", "title": "몇십 몇 X 몇 (종합) A", "page": "p92~93", "description": "여러 형태의 올림이 있는 몇십 몇 X 몇을 세로식으로 연습합니다." }, {
	"idx": "110",
	"grade": "31",
	"chapter": "4",
	"no": "12",
	"title": "몇십 몇 X 몇 (종합) B",
	"page": "p92~93",
	"description": "여러 형태의 올림이 있는 몇십 몇 X 몇을 가로식으로 연습합니다."
}, { "idx": "122", "grade": "32", "chapter": "1", "no": "1", "title": "세 자리 수 X 한 자리 수 (올림 없음) A", "page": "p18~19", "description": "올림이 없는 세 자리 수 X 한 자리 수를 곱셉 과정을 모두 나타낸 세로식으로 연습합니다. " }, {
	"idx": "123",
	"grade": "32",
	"chapter": "1",
	"no": "2",
	"title": "세 자리 수 X 한 자리 수 (올림 없음) B",
	"page": "p18~19",
	"description": "올림이 없는 세 자리 수 X 한 자리 수를 세로식으로 연습합니다. "
}, { "idx": "124", "grade": "32", "chapter": "1", "no": "3", "title": "세 자리 수 X 한 자리 수 (올림 없음) C", "page": "p18~19", "description": "올림이 없는 세 자리 수 X 한 자리 수를 가로식으로 연습합니다. " }, {
	"idx": "125",
	"grade": "32",
	"chapter": "1",
	"no": "4",
	"title": "세 자리 수 X 한 자리 수 (올림 있음) A",
	"page": "p20~21",
	"description": "올림이 있는 세 자리 수 X 한 자리 수를 곱셈 과정을 모두 나타낸 세로식으로 연습합니다."
}, { "idx": "126", "grade": "32", "chapter": "1", "no": "5", "title": "세 자리 수 X 한 자리 수 (올림 있음) B", "page": "p20~21", "description": "올림이 있는 세 자리 수 X 한 자리 수를 세로식으로 연습합니다." }, {
	"idx": "127",
	"grade": "32",
	"chapter": "1",
	"no": "6",
	"title": "세 자리 수 X 한 자리 수 (올림 있음) C",
	"page": "p20~21",
	"description": "올림이 있는 세 자리 수 X 한 자리 수를 가로식으로 연습합니다."
}, { "idx": "128", "grade": "32", "chapter": "1", "no": "7", "title": "몇십 X 몇십 A", "page": "p22~23", "description": "몇십 X 몇십을 세로식으로 연습합니다." }, {
	"idx": "129",
	"grade": "32",
	"chapter": "1",
	"no": "8",
	"title": "몇십 X 몇십 B",
	"page": "p22~23",
	"description": "몇십 X 몇십을 가로식으로 연습합니다."
}, { "idx": "130", "grade": "32", "chapter": "1", "no": "9", "title": "두 자리 수 X 몇십 A", "page": "p24~25", "description": "두 자리 수 X 몇십을 세로식으로 연습합니다." }, {
	"idx": "131",
	"grade": "32",
	"chapter": "1",
	"no": "10",
	"title": "두 자리 수 X 몇십 B",
	"page": "p24~25",
	"description": "두 자리 수 X 몇십을 가로식으로 연습합니다."
}, { "idx": "132", "grade": "32", "chapter": "1", "no": "11", "title": "두 자리 수 X 두 자리 수 A", "page": "p26~27", "description": "두 자리 수 X 두 자리 수를 세로식으로 연습합니다." }, {
	"idx": "133",
	"grade": "32",
	"chapter": "1",
	"no": "12",
	"title": "두 자리 수 X 두 자리 수 B",
	"page": "p26~27",
	"description": "가로식으로 된 두 자리 수 X 두 자리 수를 세로식으로 바꾸어 곱셈하는 것을 연습합니다."
}, { "idx": "134", "grade": "32", "chapter": "2", "no": "1", "title": "몇십 ÷ 몇 (내림이 없고 나머지도 없음) A", "page": "p50~51", "description": "내림이 없고 나머지도 없는 몇십 ÷ 몇을 세로식으로 연습합니다." }, {
	"idx": "135",
	"grade": "32",
	"chapter": "2",
	"no": "2",
	"title": "몇십 ÷ 몇 (내림이 없고 나머지도 없음) B",
	"page": "p50~51",
	"description": "내림이 없고 나머지도 없는 몇십 ÷ 몇이 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈을 하는 연습을 합니다."
}, { "idx": "136", "grade": "32", "chapter": "2", "no": "3", "title": "몇십 몇 ÷ 몇 (내림이 없고 나머지도 없음) A", "page": "p52~53", "description": "내림이 없고 나머지도 없는 몇십 몇 ÷ 몇을 세로식으로 연습합니다." }, {
	"idx": "137",
	"grade": "32",
	"chapter": "2",
	"no": "4",
	"title": "몇십 몇 ÷ 몇 (내림이 없고 나머지도 없음) B",
	"page": "p52~53",
	"description": "내림이 없고 나머지도 없는 몇십 몇 ÷ 몇이 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다. "
}, { "idx": "142", "grade": "32", "chapter": "2", "no": "5", "title": "몇십 몇 ÷ 몇 (내림이 있으나 나머지가 없음) A", "page": "p58", "description": "내림이 있으나 나머지가 없는 몇십 몇 ÷ 몇을 세로식으로 연습합니다." }, {
	"idx": "143",
	"grade": "32",
	"chapter": "2",
	"no": "6",
	"title": "몇십 몇 ÷ 몇 (내림이 있으나 나머지가 없음) B",
	"page": "p58",
	"description": "내림이 있으나 나머지가 없는 몇십 몇 ÷ 몇이 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다."
}, { "idx": "144", "grade": "32", "chapter": "2", "no": "7", "title": "몇십 몇 ÷ 몇 (내림이 있고 나머지도 있음) A", "page": "p59", "description": "내림이 있고 나머지도 있는 몇십 몇 ÷ 몇을 세로식으로 연습합니다." }, {
	"idx": "145",
	"grade": "32",
	"chapter": "2",
	"no": "8",
	"title": "몇십 몇 ÷ 몇 (내림이 있고 나머지도 있음) B",
	"page": "p59",
	"description": "내림이 있고 나머지도 있는 몇십 몇 ÷ 몇이 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다."
}, { "idx": "152", "grade": "32", "chapter": "2", "no": "9", "title": "몇백 몇십 몇 ÷ 몇 (내림/나머지 있음) A", "page": "p60~61", "description": "내림이 있고 나머지도 있는 몇백 몇십 몇 ÷ 몇을 세로식으로 연습합니다." }, {
	"idx": "153",
	"grade": "32",
	"chapter": "2",
	"no": "10",
	"title": "몇백 몇십 몇 ÷ 몇 (내림/나머지 있음) B",
	"page": "p60~61",
	"description": "내림이 있고 나머지도 있는 몇백 몇십 몇 ÷ 몇이 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다."
}, { "idx": "138", "grade": "32", "chapter": "2", "no": "11", "title": "나눗셈의 몫과 나머지 A", "page": "p54~55", "description": "나누는 수를 몇 번 빼서 남은 수가 나누는 수보다 작을 때, 그 회수가 나눗셈의 몫이 되고 남은 수가 나눗셈의 나머지가 되는 개념을 익힙니다." }, {
	"idx": "139",
	"grade": "32",
	"chapter": "2",
	"no": "12",
	"title": "나눗셈의 몫과 나머지 B",
	"page": "p54~55",
	"description": "나누어 떨어지지 않는 나눗셈의 몫과 나머지를 구하는 것을 세로식으로 연습합니다."
}, { "idx": "140", "grade": "32", "chapter": "2", "no": "13", "title": "나눗셈의 몫과 나머지 C", "page": "p54~55", "description": "나누어 떨어지지 않는 나눗셈의 몫과 나머지를 구하는 것을 가로식으로 연습합니다." }, {
	"idx": "141",
	"grade": "32",
	"chapter": "2",
	"no": "14",
	"title": "나눗셈의 검산 ",
	"page": "p56~57",
	"description": "나눗셈의 몫과 나머지를 곱셈과 덧셈을 이용하여 검산하는 것을 연습합니다."
}, { "idx": "181", "grade": "32", "chapter": "4", "no": "1", "title": "대분수를 가분수로 나타내기", "page": "p98~99", "description": "대분수를 가분수로 나타내는 연습을 합니다." }, {
	"idx": "182",
	"grade": "32",
	"chapter": "4",
	"no": "2",
	"title": "가분수를 대분수로 나타내기",
	"page": "p98~99",
	"description": "가분수를 대분수로 나타내는 연습을 합니다."
}, { "idx": "183", "grade": "32", "chapter": "4", "no": "3", "title": "분모가 같은 가분수/대분수끼리의 크기 비교", "page": "p100~p101", "description": "분모가 같은 가분수들의 크기를 비교하고, 분모가 같은 대분수들의 크기를 비교하는 연습을 합니다." }, {
	"idx": "184",
	"grade": "32",
	"chapter": "4",
	"no": "4",
	"title": "분모가 같은 가분수와 대분수의 크기 비교",
	"page": "p100~p101",
	"description": "분모가 같은 가분수와 대분수의 크기를 비교하는 연습을 합니다."
}, { "idx": "146", "grade": "41", "chapter": "3", "no": "1", "title": "두 자리 수 X 몇백/몇천", "page": "p24~25", "description": "몇백과 몇천을 곱할 때의 원리를 이해하고, 두 자리수 X 몇백/몇천을 구하는 것을 연습합니다." }, {
	"idx": "147",
	"grade": "41",
	"chapter": "3",
	"no": "2",
	"title": "몇백, 몇천의 곱",
	"page": "p24~25",
	"description": "몇백과 몇천을 곱할 때의 원리를 이해하고, 몇백/몇천 X 몇백/몇천을 연습합니다."
}, { "idx": "148", "grade": "41", "chapter": "3", "no": "3", "title": "세 자리 수 X 두 자리 수 A", "page": "p26~27", "description": "세 자리 수 X 두 자리 수를 세로식으로 연습합니다." }, {
	"idx": "149",
	"grade": "41",
	"chapter": "3",
	"no": "4",
	"title": "세 자리 수 X 두 자리 수 B",
	"page": "p26~27",
	"description": "가로식으로 주어진 세 자리 수 X 두 자리 수를 세로식으로 풀어 구하는 것을 연습합니다."
}, { "idx": "150", "grade": "41", "chapter": "3", "no": "5", "title": "네 자리 수 X 두 자리 수 A", "page": "p28~29", "description": "네 자리 수 X 두 자리 수를 세로식으로 연습합니다." }, {
	"idx": "151",
	"grade": "41",
	"chapter": "3",
	"no": "6",
	"title": "네 자리 수 X 두 자리 수 B",
	"page": "p28~29",
	"description": "가로식으로 주어진 네 자리 수 X 두 자리 수를 세로식으로 풀어 구하는 것을 연습합니다."
}, { "idx": "154", "grade": "41", "chapter": "3", "no": "7", "title": "세 수의 곱셈", "page": "p30~31", "description": "세 수의 연이은 곱셈값을 구하는 것을 연습합니다." }, {
	"idx": "155",
	"grade": "41",
	"chapter": "3",
	"no": "8",
	"title": "곱셈 종합",
	"page": "p24~31",
	"description": "두 자리/세 자리/네 자리 X 두 자리 곱셈을 연습합니다."
}, { "idx": "156", "grade": "41", "chapter": "3", "no": "9", "title": "몇십으로 나누기 (나머지 없음) A", "page": "p32", "description": "나머지가 없는 세 자리수 ÷ 몇십을 세로식으로 연습합니다." }, {
	"idx": "157",
	"grade": "41",
	"chapter": "3",
	"no": "10",
	"title": "몇십으로 나누기 (나머지 없음) B",
	"page": "p32",
	"description": "나머지가 없는 세 자리수 ÷ 몇십이 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다."
}, { "idx": "158", "grade": "41", "chapter": "3", "no": "11", "title": "몇십으로 나누기 (나머지 있음) A", "page": "p33", "description": "나머지가 있는 세 자리수 ÷ 몇십을 세로식으로 연습합니다." }, {
	"idx": "159",
	"grade": "41",
	"chapter": "3",
	"no": "12",
	"title": "몇십으로 나누기 (나머지 있음)",
	"page": "p33",
	"description": "나머지가 있는 세 자리수 ÷ 몇십이 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다."
}, { "idx": "160", "grade": "41", "chapter": "3", "no": "13", "title": "두 자리 수 ÷ 두 자리 수 (나머지 없음) A", "page": "p34", "description": "나머지가 없는 두 자리수 ÷ 두 자리 수를 세로식으로 연습합니다." }, {
	"idx": "161",
	"grade": "41",
	"chapter": "3",
	"no": "14",
	"title": "두 자리 수 ÷ 두 자리 수 (나머지 없음) B",
	"page": "p34",
	"description": "나머지가 없는 두 자리수 ÷ 두 자리 수가 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다."
}, { "idx": "162", "grade": "41", "chapter": "3", "no": "15", "title": "두 자리 수 ÷ 두 자리 수 (나머지 있음) A", "page": "p35", "description": "나머지가 있는 두 자리수 ÷ 두 자리 수를 세로식으로 연습합니다." }, {
	"idx": "163",
	"grade": "41",
	"chapter": "3",
	"no": "16",
	"title": "두 자리 수 ÷ 두 자리 수 (나머지 있음) B",
	"page": "p35",
	"description": "나머지가 있는 두 자리수 ÷ 두 자리 수가 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다."
}, { "idx": "164", "grade": "41", "chapter": "3", "no": "17", "title": "세 자리 수 ÷ 두 자리 수 (나머지 없음) A", "page": "p36", "description": "몫이 한 자리이고 나머지가 없는 세 자리수 ÷ 두 자리 수를 세로식으로 연습합니다." }, {
	"idx": "165",
	"grade": "41",
	"chapter": "3",
	"no": "18",
	"title": "세 자리 수 ÷ 두 자리 수 (나머지 없음) B",
	"page": "p36",
	"description": "몫이 한 자리이고 나머지가 없는 세 자리수 ÷ 두 자리 수가 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다."
}, { "idx": "166", "grade": "41", "chapter": "3", "no": "19", "title": "세 자리 수 ÷ 두 자리 수 (나머지 있음) A", "page": "p37", "description": "몫이 한 자리이고 나머지가 있는 세 자리수 ÷ 두 자리 수를 세로식으로 연습합니다." }, {
	"idx": "167",
	"grade": "41",
	"chapter": "3",
	"no": "20",
	"title": "세 자리 수 ÷ 두 자리 수 (나머지 있음) B",
	"page": "p37",
	"description": "몫이 한 자리이고 나머지가 있는 세 자리수 ÷ 두 자리 수가 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다."
}, { "idx": "168", "grade": "41", "chapter": "3", "no": "21", "title": "세 자리 수 ÷ 두 자리 수 (몫이 두 자리) A", "page": "p38~39", "description": "몫이 두 자리이고 나머지가 있는 세 자리수 ÷ 두 자리 수를 세로식으로 연습합니다." }, {
	"idx": "169",
	"grade": "41",
	"chapter": "3",
	"no": "22",
	"title": "세 자리 수 ÷ 두 자리 수 (몫이 두 자리) B",
	"page": "p38~39",
	"description": "몫이 두 자리이고 나머지가 있는 세 자리수 ÷ 두 자리 수가 가로식으로 주어졌을때, 세로식으로 풀어서 나눗셈하는 연습을 합니다."
}, { "idx": "185", "grade": "42", "chapter": "1", "no": "1", "title": "분모가 같은 진분수의 덧셈 (합이 진분수)", "page": "p4~5", "description": "합이 진분수인, 분모가 같은 두 개의 진분수의 합을 구하는 연습을 합니다. " }, {
	"idx": "186",
	"grade": "42",
	"chapter": "1",
	"no": "2",
	"title": "분모가 같은 진분수의 덧셈 (합이 가분수)",
	"page": "p6~7",
	"description": "합이 가분수인, 분모가 같은 두 개의 진분수의 합을 구하는 연습을 합니다. "
}, { "idx": "187", "grade": "42", "chapter": "1", "no": "3", "title": "분모가 같은 대분수의 덧셈 (분자의 합 < 분모)", "page": "p8", "description": "분자의 합이 분모보다 크지 않은, 분모가 같은 두 개의 대분수의 합을 구하는 연습을 합니다. " }, {
	"idx": "188",
	"grade": "42",
	"chapter": "1",
	"no": "4",
	"title": "분모가 같은 대분수의 덧셈 (분자의 합 > 분모)",
	"page": "p9",
	"description": "분자의 합이 분모보다 큰, 분모가 같은 두 개의 대분수의 합을 구하는 연습을 합니다. "
}, { "idx": "189", "grade": "42", "chapter": "1", "no": "5", "title": "분모가 같은 진분수의 뺄셈 (진분수 - 진분수)", "page": "p10", "description": "분모가 같은 두 개의 진분수의 뺄셈을 연습합니다." }, {
	"idx": "190",
	"grade": "42",
	"chapter": "1",
	"no": "6",
	"title": "분모가 같은 진분수의 뺄셈 (자연수 - 진분수)",
	"page": "p11",
	"description": "자연수에서 진분수를 빼는 계산을 연습합니다."
}, { "idx": "191", "grade": "42", "chapter": "1", "no": "7", "title": "분모가 같은 대분수의 뺄셈 <1>", "page": "p12", "description": "분모가 같고 앞의 분자가 뒤의 분자보다 큰 값을 가진, 두 개의 대분수의 뺄셈을 연습합니다." }, {
	"idx": "192",
	"grade": "42",
	"chapter": "1",
	"no": "8",
	"title": "분모가 같은 대분수의 뺄셈 <2>",
	"page": "p13",
	"description": "분모가 같고 앞의 분자가 뒤의 분자보다 작은 값을 가진, 두 개의 대분수의 뺄셈을 연습합니다."
}, { "idx": "193", "grade": "42", "chapter": "1", "no": "9", "title": "분모가 같은 대분수와 진분수의 덧셈", "page": "p14", "description": "분모가 같은 대분수와 진분수의 덧셈을 연습합니다." }, {
	"idx": "194",
	"grade": "42",
	"chapter": "1",
	"no": "10",
	"title": "분모가 같은 대분수와 진분수의 뺄셈",
	"page": "p15",
	"description": "분모가 같은 대분수와 진분수의 뺄셈을 연습합니다."
}, { "idx": "195", "grade": "42", "chapter": "3", "no": "1", "title": "자연수가 없는 소수 한 자리 수끼리의 덧셈 A", "page": "p20~21", "description": "소수 한 자리 수끼리의 덧셈을 세로식으로 연습합니다." }, {
	"idx": "203",
	"grade": "42",
	"chapter": "3",
	"no": "2",
	"title": "자연수가 없는 소수 한 자리 수끼리의 덧셈 B",
	"page": "p20~21",
	"description": "소수 한 자리 수끼리의 덧셈을 가로식으로 연습합니다."
}, { "idx": "196", "grade": "42", "chapter": "3", "no": "3", "title": "자연수가 없는 소수 두 자리 수 범위의 덧셈 A", "page": "p22~23", "description": "소수 두 자리 수끼리의 덧셈 또는 소수 한 자리 수와 소수 두 자리 수의 덧셈을 세로식으로 연습합니다." }, {
	"idx": "197",
	"grade": "42",
	"chapter": "3",
	"no": "4",
	"title": "자연수가 없는 소수 두 자리 수 범위의 덧셈 B",
	"page": "p22~23",
	"description": "소수 두 자리 수끼리의 덧셈 또는 소수 한 자리 수와 소수 두 자리 수의 덧셈을 가로식으로 연습합니다."
}, { "idx": "198", "grade": "42", "chapter": "3", "no": "5", "title": "자연수가 있는 소수 두 자리 수끼리의 덧셈 A", "page": "p24", "description": "자연수가 있는 소수 두 자리 수끼리의 덧셈을 세로식으로 연습합니다." }, {
	"idx": "199",
	"grade": "42",
	"chapter": "3",
	"no": "6",
	"title": "자연수가 있는 소수 두 자리 수끼리의 덧셈 B",
	"page": "p24",
	"description": "자연수가 있는 소수 두 자리 수끼리의 덧셈을 가로식으로 연습합니다."
}, { "idx": "200", "grade": "42", "chapter": "3", "no": "7", "title": "자연수가 있는 소수 세 자리 수 범위의 덧셈 A", "page": "p25", "description": "자연수가 있는 소수 세 자리 수 범위의 덧셈을 세로식으로 연습합니다." }, {
	"idx": "201",
	"grade": "42",
	"chapter": "3",
	"no": "8",
	"title": "자연수가 있는 소수 세 자리 수 범위의 덧셈 B",
	"page": "p25",
	"description": "자연수가 있는 소수 세 자리 수 범위의 덧셈을 가로식으로 연습합니다."
}, { "idx": "202", "grade": "42", "chapter": "3", "no": "9", "title": "소수 한 자리 수끼리의 뺄셈 A", "page": "p26~27", "description": "소수 한 자리 수끼리의 뺄셈을 세로식으로 연습합니다." }, {
	"idx": "204",
	"grade": "42",
	"chapter": "3",
	"no": "10",
	"title": "소수 한 자리 수끼리의 뺄셈 B",
	"page": "p26~27",
	"description": "소수 한 자리 수끼리의 뺄셈을 가로식으로 연습합니다."
}, { "idx": "205", "grade": "42", "chapter": "3", "no": "11", "title": "자연수가 없는 소수 두 자리 범위의 뺄셈 A", "page": "p28~29", "description": "자연수가 없는 소수 두 자리 수 범위의 뺄셈을 세로식으로 연습합니다." }, {
	"idx": "206",
	"grade": "42",
	"chapter": "3",
	"no": "12",
	"title": "자연수가 없는 소수 두 자리 범위의 뺄셈 B",
	"page": "p28~29",
	"description": "자연수가 없는 소수 두 자리 수 범위의 뺄셈을 가로식으로 연습합니다."
}, { "idx": "207", "grade": "42", "chapter": "3", "no": "13", "title": "자연수가 있는 소수 두 자리 수끼리의 뺄셈 A", "page": "p30", "description": "자연수가 있는 소수 두 자리 수끼리의 뺄셈을 세로식으로 연습합니다." }, {
	"idx": "208",
	"grade": "42",
	"chapter": "3",
	"no": "14",
	"title": "자연수가 있는 소수 두 자리 수끼리의 뺄셈 B",
	"page": "p30",
	"description": "자연수가 있는 소수 두 자리 수끼리의 뺄셈을 가로식으로 연습합니다."
}, { "idx": "209", "grade": "42", "chapter": "3", "no": "15", "title": "자연수가 있는 소수 세 자리 수 범위의 뺄셈 A", "page": "p31", "description": "자연수가 있는 소수 세 자리 범위의 뺄셈을 세로식으로 연습합니다." }, {
	"idx": "210",
	"grade": "42",
	"chapter": "3",
	"no": "16",
	"title": "자연수가 있는 소수 세 자리 수 범위의 뺄셈 B",
	"page": "p31",
	"description": "자연수가 있는 소수 세 자리 범위의 뺄셈을 가로식으로 연습합니다."
}, { "idx": "170", "grade": "51", "chapter": "1", "no": "1", "title": "덧셈과 뺄셈의 혼합 계산", "page": "p76~77", "description": "덧셈과 뺄셈이 섞여 있는 식의 계산을 연습합니다." }, {
	"idx": "171",
	"grade": "51",
	"chapter": "1",
	"no": "2",
	"title": "곱셈과 나눗셈의 혼합 계산",
	"page": "p78~79",
	"description": "곱셈과 나눗셈이 섞여 있는 식의 계산을 연습합니다."
}, { "idx": "172", "grade": "51", "chapter": "1", "no": "3", "title": "덧셈, 뺄셈, 곱셈의 혼합 계산", "page": "p80~81", "description": "덧셈, 뺄셈, 곱셈이 섞여 있는 식의 계산을 연습합니다." }, {
	"idx": "173",
	"grade": "51",
	"chapter": "1",
	"no": "4",
	"title": "덧셈, 뺄셈, 나눗셈의 혼합 계산",
	"page": "p82~83",
	"description": "덧셈, 뺄셈, 나눗셈이 섞여 있는 식의 계산을 연습합니다."
}, { "idx": "174", "grade": "51", "chapter": "1", "no": "5", "title": "덧셈, 뺄셈, 곱셈, 나눗셈의 혼합 계산", "page": "p80~83", "description": "덧셈, 뺄셈, 곱셈, 나눗셈이 섞여 있는 식의 계산을 연습합니다." }, {
	"idx": "175",
	"grade": "51",
	"chapter": "1",
	"no": "6",
	"title": "( )가 있는 덧셈/뺄셈의 혼합 계산",
	"page": "p84~85",
	"description": "( )가 있는 덧셈/뺄셈의 혼합 계산을 연습합니다."
}, { "idx": "176", "grade": "51", "chapter": "1", "no": "7", "title": "( )가 있는 곱셈/나눗셈의 혼합 계산", "page": "p84~85", "description": "( )가 있는 곱셈/나눗셈의 혼합 계산을 연습합니다." }, {
	"idx": "177",
	"grade": "51",
	"chapter": "1",
	"no": "8",
	"title": "( )가 있는 덧셈/뺄셈/곱셈의 혼합 계산",
	"page": "p84~85",
	"description": "( )가 있는 덧셈/뺄셈/곱셈의 혼합 계산을 연습합니다."
}, { "idx": "178", "grade": "51", "chapter": "1", "no": "9", "title": "( )가 있는 덧셈/뺄셈/나눗셈의 혼합 계산", "page": "p84~85", "description": "( )가 있는 덧셈/뺄셈/나눗셈의 혼합 계산을 연습합니다." }, {
	"idx": "179",
	"grade": "51",
	"chapter": "1",
	"no": "10",
	"title": "( )가 있는 덧셈/뺄셈/곱셈/나눗셈의 혼합 계산",
	"page": "p84~85",
	"description": "( )가 있는 덧셈/뺄셈/곱셈/나눗셈의 혼합 계산을 연습합니다."
}, { "idx": "180", "grade": "51", "chapter": "1", "no": "11", "title": "{ }가 있는 덧셈/뺄셈/곱셈/나눗셈의 혼합 계산", "page": "p86~89", "description": "{ }가 있는 덧셈/뺄셈/곱셈/나눗셈의 혼합 계산을 연습합니다." }, {
	"idx": "211",
	"grade": "51",
	"chapter": "2",
	"no": "1",
	"title": "약수 구하기",
	"page": "p4~5",
	"description": "주어진 수의 약수를 구하는 연습을 합니다."
}, { "idx": "212", "grade": "51", "chapter": "2", "no": "2", "title": "배수 구하기", "page": "p6~7", "description": "주어진 수의 배수를 구하는 연습을 합니다." }, {
	"idx": "213",
	"grade": "51",
	"chapter": "2",
	"no": "3",
	"title": "공약수 구하기",
	"page": "p10",
	"description": "두 개의 수에서 공통으로 존재하는 약수들을 구하는 연습을 합니다."
}, { "idx": "214", "grade": "51", "chapter": "2", "no": "4", "title": "최대 공약수 구하기  - 두 수를 곱으로 나타냄", "page": "p11", "description": "두 수를 곱으로 나타내어 공통인 수를 찾아 최대공약수를 구하는 것을 연습합니다." }, {
	"idx": "215",
	"grade": "51",
	"chapter": "2",
	"no": "5",
	"title": "최대 공약수 구하기 - 공통 약수 이용",
	"page": "p11",
	"description": "공통인 약수를 찾아 최대공약수를 구하는 것을 연습합니다."
}, { "idx": "216", "grade": "51", "chapter": "2", "no": "6", "title": "공배수 구하기", "page": "p12", "description": "두 수의 배수 중에서 공통으로 존재하는 배수들을 구하는 연습을 합니다." }, {
	"idx": "217",
	"grade": "51",
	"chapter": "2",
	"no": "7",
	"title": "최소 공배수 구하기 - 두 수를 곱으로 나타냄",
	"page": "p13",
	"description": "두 수를 곱으로 나타낸 후 최소공배수를 찾는 연습을 합니다."
}, { "idx": "218", "grade": "51", "chapter": "2", "no": "8", "title": "최소 공배수 구하기 - 최대공약수 이용", "page": "p13", "description": "두 수의 최대공약수와 각 수를 최대공약수로 나눈 몫을 곱하여 최소공배수를 구하는 것을 연습합니다." }, {
	"idx": "219",
	"grade": "51",
	"chapter": "4",
	"no": "1",
	"title": "크기가 같은 분수 만들기",
	"page": "p20",
	"description": "분수의 분모와 분자에 0이 아닌 같은 수를 곱하여 크기가 같은 분수를 만드는 연습을 합니다."
}, { "idx": "222", "grade": "51", "chapter": "4", "no": "4", "title": "분수의 통분 (곱을 공통분모로)", "page": "p24~27", "description": "분모의 곱을 공통분모로 하여 두 분수를 통분하는 것을 연습합니다." }, {
	"idx": "223",
	"grade": "51",
	"chapter": "4",
	"no": "5",
	"title": "분수의 통분 (최소공배수를 공통분모로)",
	"page": "p24~27",
	"description": "분모의 곱을 최소공배수로 하여 두 분수를 통분하는 것을 연습합니다."
}, { "idx": "224", "grade": "51", "chapter": "4", "no": "6", "title": "분수의 크기 비교", "page": "p28", "description": "분모가 다른 두 분수의 크기를 비교하여 부등호를 써서 나타내는 연습을 합니다. " }, {
	"idx": "238",
	"grade": "51",
	"chapter": "4",
	"no": "7",
	"title": "분수를 소수로 나타내기",
	"page": "p6~7",
	"description": "분수를 소수로 나타내는 연습을 합니다."
}, { "idx": "239", "grade": "51", "chapter": "4", "no": "8", "title": "소수를 분수로 나타내기", "page": "p8~9", "description": "소수를 분수로 나타내는 연습을 합니다." }, {
	"idx": "240",
	"grade": "51",
	"chapter": "4",
	"no": "9",
	"title": "분수와 소수의 비교",
	"page": "p10~11",
	"description": "소수를 분수로 나타내는 연습을 합니다."
}, { "idx": "225", "grade": "51", "chapter": "5", "no": "1", "title": "진분수의 덧셈", "page": "p34~35", "description": "분모가 다른 진분수의 덧셈을 연습합니다." }, {
	"idx": "226",
	"grade": "51",
	"chapter": "5",
	"no": "2",
	"title": "대분수의 덧셈 (받아올림 없음)",
	"page": "p36",
	"description": "받아올림이 없는 대분수의 덧셈을 연습합니다. "
}, { "idx": "227", "grade": "51", "chapter": "5", "no": "3", "title": "대분수의 덧셈 (받아올림 있음)", "page": "p37", "description": "받아올림이 있는 대분수의 덧셈을 연습합니다. " }, {
	"idx": "228",
	"grade": "51",
	"chapter": "5",
	"no": "4",
	"title": "진분수의 뺄셈",
	"page": "p38~39",
	"description": "분모가 다른 진분수의 뺄셈을 연습합니다."
}, { "idx": "230", "grade": "51", "chapter": "5", "no": "5", "title": "대분수의 뺄셈 (받아내림 없음)", "page": "p40", "description": "받아내림이 없는 대분수의 뺄셈을 연습합니다." }, {
	"idx": "231",
	"grade": "51",
	"chapter": "5",
	"no": "6",
	"title": "대분수의 뺄셈 (받아내림 있음)",
	"page": "p41",
	"description": "받아내림이 있는 대분수의 뺄셈을 연습합니다."
}, { "idx": "232", "grade": "52", "chapter": "2", "no": "1", "title": "분수 X 자연수", "page": "p48~51", "description": "(진분수) X (자연수) 또는 (대분수) X (자연수)의 계산을 연습합니다." }, {
	"idx": "233",
	"grade": "52",
	"chapter": "2",
	"no": "2",
	"title": "자연수 X 분수",
	"page": "p52~55",
	"description": "(자연수) X (진분수) 또는 (자연수) X (대분수)의 계산을 연습합니다."
}, { "idx": "234", "grade": "52", "chapter": "2", "no": "3", "title": "진분수 X 진분수", "page": "p56~59", "description": "(진분수) X (진분수)의 계산을 연습합니다." }, {
	"idx": "235",
	"grade": "52",
	"chapter": "2",
	"no": "4",
	"title": "대분수 X 대분수",
	"page": "p60~61",
	"description": "(대분수) X (대분수)의 계산을 연습합니다."
}, { "idx": "236", "grade": "52", "chapter": "2", "no": "5", "title": "세 분수의 곱셈 (진분수)", "page": "p62~63", "description": "세 분수 모두 진분수로만 이루어진 세 분수의 곱셈을 연습합니다." }, {
	"idx": "237",
	"grade": "52",
	"chapter": "2",
	"no": "6",
	"title": "세 분수의 곱셈 (대분수, 자연수 포함)",
	"page": "p62~63",
	"description": "대분수 또는 자연수를 포함한 세 분수의 곱셈을 연습합니다."
}, { "idx": "243", "grade": "52", "chapter": "4", "no": "1", "title": "소수와 자연수의 곱", "page": "p56~61", "description": "소수 X 자연수, 자연수 X 소수를 연습합니다." }, {
	"idx": "244",
	"grade": "52",
	"chapter": "4",
	"no": "2",
	"title": "곱의 소수점의 위치",
	"page": "p62~65",
	"description": "자연수와 소수의 곱셈 및 소수에 10, 100, 1000을 곱하는 경우와 자연수에 0.1, 0.01, 0.001을 곱하는 것을 통해 소수를 곱한 후 소수점의 위치를 알아내는 연습을 합니다."
}, { "idx": "246", "grade": "52", "chapter": "4", "no": "3", "title": "소수 X 소수", "page": "p66~69", "description": "소수와 소수의 곱셈을 연습합니다." }, {
	"idx": "247",
	"grade": "52",
	"chapter": "4",
	"no": "4",
	"title": "세 소수의 곱셈",
	"page": "p71",
	"description": "세 소수의 곱셈을 연습합니다."
}, { "idx": "241", "grade": "61", "chapter": "1", "no": "1", "title": "분수 ÷ 자연수", "page": "p20~25", "description": "분수를 자연수로 나눈 값을 구하는 연습을 합니다." }, {
	"idx": "242",
	"grade": "61",
	"chapter": "1",
	"no": "2",
	"title": "분수와 자연수의 곱셈/나눗셈 혼합",
	"page": "p26~27",
	"description": "분수와 자연수의 곱셈, 나눗셈이 혼합된 계산을 연습합니다."
}, { "idx": "248", "grade": "61", "chapter": "3", "no": "1", "title": "소수 ÷ 자연수 ", "page": "p76~81", "description": "자연수로 나누어 떨어지는 소수 ÷ 자연수를 연습합니다." }, {
	"idx": "249",
	"grade": "61",
	"chapter": "3",
	"no": "2",
	"title": "소수 ÷ 자연수 (소수 끝자리 아래 0을 내려 계산)",
	"page": "p82~85",
	"description": "주어진 소수를 그대로 나눌 때 나누어 떨어지지 않는 경우 소수 끝자리 아래에 0을 내려 계산하는 연습을 합니다."
}, { "idx": "250", "grade": "61", "chapter": "3", "no": "3", "title": "자연수 ÷ 자연수", "page": "p86", "description": "자연수 ÷ 자연수의 몫이 소수인 나눗셈을 연습합니다." }, {
	"idx": "251",
	"grade": "61",
	"chapter": "3",
	"no": "4",
	"title": "자연수 ÷ 자연수 (반올림하여 나타냄)",
	"page": "p87",
	"description": "자연수 ÷ 자연수의 몫이 나누어떨어지지 않을 경우 반올림하여 나타내는 연습을 합니다."
}, { "idx": "259", "grade": "61", "chapter": "4", "no": "1", "title": "비와 비율", "page": "p106~109", "description": "문장을 보고, 비를 나타내고 그 비를 분수와 소수로 나타내는 연습을 합니다." }, {
	"idx": "260",
	"grade": "61",
	"chapter": "4",
	"no": "2",
	"title": "백분율과 할푼리",
	"page": "p110~113",
	"description": "비율을 백분율(%) 또는 할푼리로 나타내는 연습을 합니다. "
}, { "idx": "252", "grade": "62", "chapter": "1", "no": "1", "title": "분수의 나눗셈 <1>", "page": "p4~7", "description": "(자연수) ÷ (단위분수) 및 분모가 같은 진분수끼리의 나눗셈을 연습합니다." }, {
	"idx": "253",
	"grade": "62",
	"chapter": "1",
	"no": "2",
	"title": "분수의 나눗셈 <2>",
	"page": "p8~11",
	"description": "분모가 다른 진분수끼리의 나눗셈 및 (자연수) ÷ (진분수)를 연습합니다."
}, { "idx": "254", "grade": "62", "chapter": "1", "no": "3", "title": "분수의 나눗셈 <3>", "page": "p12~13", "description": "대분수가 포함된 분수의 나눗셈을 연습합니다." }, {
	"idx": "255",
	"grade": "62",
	"chapter": "2",
	"no": "1",
	"title": "소수 한 자리 수 ÷ 소수 한 자리 수",
	"page": "p20~21",
	"description": "(소수 한 자리 수) ÷ (소수 한 자리 수)의 몫을 구하는 연습을 합니다."
}, { "idx": "256", "grade": "62", "chapter": "2", "no": "2", "title": "소수 두 자리 수 ÷ 소수 두 자리 수", "page": "p22~23", "description": "(소수 두 자리 수) ÷ (소수 두 자리 수)의 몫을 구하는 연습을 합니다." }, {
	"idx": "257",
	"grade": "62",
	"chapter": "2",
	"no": "3",
	"title": "자릿수가 다른 두 소수의 나눗셈",
	"page": "p24~25",
	"description": "자릿수가 다른 두 소수의 나눗셈을 연습합니다."
}, { "idx": "258", "grade": "62", "chapter": "2", "no": "4", "title": "자연수 ÷ 소수", "page": "p26~27", "description": "(자연수) ÷ (소수)을 연습합니다." }, {
	"idx": "261",
	"grade": "62",
	"chapter": "2",
	"no": "5",
	"title": "소수의 나눗셈에서 나머지 구하기",
	"page": "p28~29",
	"description": "소수의 나눗셈에서 나머지를 구하는 연습을 합니다."
}, { "idx": "271", "grade": "62", "chapter": "2", "no": "6", "title": "소수 ÷ 분수 (분수로 고쳐 계산)", "page": "p4~6", "description": "(소수) ÷ (분수)에서 소수를 분수로 고쳐서 계산하는 연습을 합니다." }, {
	"idx": "272",
	"grade": "62",
	"chapter": "2",
	"no": "7",
	"title": "소수 ÷ 분수 (소수로 고쳐 계산)",
	"page": "p4~6",
	"description": "(소수) ÷ (분수)에서 분수를 소수로 고쳐서 계산하는 연습을 합니다."
}, { "idx": "273", "grade": "62", "chapter": "2", "no": "8", "title": "분수 ÷ 소수 (분수로 고쳐 계산)", "page": "p7~8", "description": "(분수) ÷ (소수)에서 소수를 분수로 고쳐서 계산하는 연습을 합니다." }, {
	"idx": "274",
	"grade": "62",
	"chapter": "2",
	"no": "9",
	"title": "분수 ÷ 소수 (소수로 고쳐 계산)",
	"page": "p7~8",
	"description": "(분수) ÷ (소수)에서 분수를 소수로 고쳐서 계산하는 연습을 합니다."
}, { "idx": "275", "grade": "62", "chapter": "2", "no": "10", "title": "분수와 소수의 혼합 계산 (연산자가 2, 3개)", "page": "p9~10", "description": "연산자가 2개 또는 3개인, 분수와 소수가 혼합되어 있는 수식을 계산하는 연습을 합니다." }, {
	"idx": "276",
	"grade": "62",
	"chapter": "2",
	"no": "11",
	"title": "분수와 소수의 혼합 계산 (연산자가 4개 이상)",
	"page": "p11~13",
	"description": "연산자가 4개 이상인, 분수와 소수가 혼합되어 있는 수식을 계산하는 연습을 합니다."
}, { "idx": "262", "grade": "62", "chapter": "4", "no": "1", "title": "가장 작은 자연수의 비로 나타내기", "page": "p104~105", "description": "주어진 비를 가장 작은 자연수의 비로 나타내는 연습을 합니다." }, {
	"idx": "263",
	"grade": "62",
	"chapter": "4",
	"no": "2",
	"title": "자연수로 된 비례식에서 @box 안의 수 구하기",
	"page": "p106~107",
	"description": "비례식의 성질을 이용하여 자연수로만 이루어진 비례식에서 모르는 항의 값을 구하는 연습을 합니다."
}, { "idx": "264", "grade": "62", "chapter": "4", "no": "3", "title": "소수가 있는 비례식에서 @box 안의 수 구하기", "page": "p106~107", "description": "비례식의 성질을 이용하여 소수가 포함되어 있는 비례식에서 모르는 항의 값을 구하는 연습을 합니다." }, {
	"idx": "265",
	"grade": "62",
	"chapter": "4",
	"no": "4",
	"title": "분수가 있는 비례식에서 @box 안의 수 구하기",
	"page": "p106~107",
	"description": "비례식의 성질을 이용하여 분수가 포함되어 있는 비례식에서 모르는 항의 값을 구하는 연습을 합니다."
}, { "idx": "266", "grade": "62", "chapter": "4", "no": "5", "title": "두 비의 관계를 연비로 나타내기", "page": "p116~119", "description": "두 비의 관계를 연비로 나타내는 연습을 합니다." }, {
	"idx": "267",
	"grade": "62",
	"chapter": "4",
	"no": "6",
	"title": "두 비를 연비로 나타낸 것에서 @box 안의 수 구하기",
	"page": "p116~119",
	"description": "두 비를 하나로 표현한 연비와의 관계에서 모르는 수를 찾는 연습을 합니다."
}, { "idx": "268", "grade": "62", "chapter": "4", "no": "7", "title": "가장 작은 수의 연비로 나타내기", "page": "p120~121", "description": "연비의 각 항에 0이 아닌 같은 수를 곱하거나 나누어 가장 작은 자연수의 연비로 나타내는 연습을 합니다." }, {
	"idx": "269",
	"grade": "62",
	"chapter": "4",
	"no": "8",
	"title": "비례배분",
	"page": "p122~123",
	"description": "문제에 주어진 수를 비에 맞게 비례배분하는 연습을 합니다."
}, { "idx": "270", "grade": "62", "chapter": "4", "no": "9", "title": "연비로 비례배분", "page": "p124~125", "description": "문제에 주어진 수를 연비에 맞게 비례배분하는 연습을 합니다." }]"""

# chapterList = json.loads(ChapterList)
# for chapter in chapterList:
#     print(chapter)

# home = '/home/siwon.sung/sandbox/11math'
# stepList = json.loads(StepList)
# for step in stepList:
#     step_idx = step['idx']
#     chapter = step['chapter']
#     title = step['title'].replace('/', '&').replace('@', '')
#     grade = step['grade'][0]
#     semester = step['grade'][1]
#     dir = '{}/{}학년/{}학기/{}'.format(home, grade, semester, title)
#     if not os.path.exists(dir):
#         os.makedirs(dir)
#
#     print("{} {} {} {} {}".format(step_idx, chapter, title, grade, semester))

