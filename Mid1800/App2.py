import json

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
               for result in results:
                   print("{}. {} : {}".format(splited[0], word, result['definition']))

       line = fp.readline()

