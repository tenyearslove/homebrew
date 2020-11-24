import json

Be_Verb = {"am", "are", "is", "was", "were", "being", "be"}
Pronoun = {"i", "you", "he", "she", "it", "we", "you", "they", "me", "him", "her", "us", "them", "my", "your", "his", "its", "our", "your", "their", "mine", "yours", "hers", "ours", "yours", "theirs"}
Relatives = {"who", "when", "where", "why", "which", "how", "that", "whose", "whom"}
Auxiliary = {"will", "would", "can", "could", "shall", "should"}
PrePosition = {"in", "at", "on", "for", "to", "up", "with", "within", "without", "of", "as", "from", "by", "off", "onto", "into", "toward", "about", "out"}
Interjaction = {"oh", "ah", "wow", "oops", "uh", "hey", "yes", "no", "not"}
Article = {"a", "an", "the"}
Conjuntion = {"and", "or"}
Adverb = {"yet", "back","always", "sometimes", "usually", "never", "rarely"}
Adjective = {"some", "any", "all", "every", "proper", "own", "other", "others", "another", "short", "tall", "small", "particular", "specific"}
Noun = {"times","something", "anything", "everyone", "description"}
Verb = {"have", "take", "having", "taken", "taking", "get", "getting", "make", "making", "made"}
FrequentlyUsed = {}

Exceptions = {
    "Be_Verb": Be_Verb,
    "Pronoun": Pronoun,
    "Relatives": Relatives,
    "Auxiliary": Auxiliary,
    "PrePosition": PrePosition,
    "Interjaction": Interjaction,
    "Article": Article,
    "Conjuntion": Conjuntion,
    "Adverb": Adverb,
    "Adjective": Adjective,
    "Noun": Noun,
    "Verb": Verb,
    "FrequentlyUsed": FrequentlyUsed
}


def convert(sentence):
    converted = '';
    keys = Exceptions.keys()
    splited = sentence.split(" ")
    in_parenthsis = False
    for token in splited:
        if not in_parenthsis and token[0] == '(':
            in_parenthsis = True

        if in_parenthsis:
            converted += token + ' '
            if token[-1] == ')':
                in_parenthsis = False
            continue
        symbol = ''
        if token[-1] == ';' or token[-1] == ',':
            symbol = token[-1]
            token = token[0:-1]
        isExceptionWord = False
        for key in keys:
            for item in Exceptions[key]:
                if token.lower() == item or "'" in token:
                    isExceptionWord = True
                    break
            else:
                continue
            break
        if not isExceptionWord:
            converted += printUnderscore(len(token)) + symbol + ' '
        else:
            converted += token + symbol + ' '
    return converted


def printUnderscore(len):
    underscores = ''
    length = len * 1
    i = 0
    while i < length:
        underscores += '_'
        i += 1
    return underscores


def isEndsWithSymbol(str):
    return str[-1] == ';' or str[-1] == ','


def main():
#    convert("the experience of being alive; the course of human events and activities")
#    return

    filepath = 'MID1800.txt'
    with open(filepath) as fp:
       line = fp.readline()
       while line:
           if not line.startswith('#'):
               splited = line.split(" ")
               num = int(splited[0])
               word = splited[1]
               f = open("dictionary/{:04d}_{}.json".format(num, word), "r")
               line = f.readline()
               results = json.loads(line)['results']
               if len(results) > 0:
                   # for result in results:
                   #     print("{}. {} : {}".format(splited[0], word, result['definition']))
                   #print("{:04d}:{}:{}:{}".format(num, word, results[0]['definition'], convert(results[0]['definition'])))
                   print("[{}]\n{}\n{}".format(word, results[0]['definition'], convert(results[0]['definition'])))
                # if len(results) > 1:
                #    print("{:04d}:{}:{}:{}".format(num, word, results[1]['definition'], convert(results[1]['definition'])))
                # if len(results) > 2:
                #    print("{:04d}:{}:{}:{}".format(num, word, results[2]['definition'], convert(results[2]['definition'])))

           line = fp.readline()


if __name__ == "__main__":
    main()