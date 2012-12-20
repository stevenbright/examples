import random
import codecs
from datetime import datetime

RECORDS_COUNT = 10000000

USER_ID_BOUND = 10
USERS = ['alex', 'bob', 'cavin', 'alice', 'jenny', 'dave', 'mark', 
         'cindy', 'larry', 'joe', 'maria', 'helen']
USER_COUNT = len(USERS)
TEXTS = ["hello", "good evening", "hi there", "welcome aboard!", 
         "hey, how are you?", "hi", "good morning", "how's it goin'?",
         "wotcha", "hey!", "all right?", "ow ya goin?",
         "hiya!", "yo", "whazzup?", "g'day!"];

# Initial post time (1970-01-01 00:00:10)
INITIAL_TIME = 10
# 1 minute, in seconds
TIME_RAND_DELTA = 60

FILENAME = "/tmp/posts.txt"

with codecs.open(FILENAME, "w", "UTF-8") as fh:
    ctime = INITIAL_TIME
    for i in range(0, RECORDS_COUNT):
        user_idx = random.randrange(0, USER_COUNT)
        user_name = USERS[user_idx]
        text = TEXTS[random.randrange(0, len(TEXTS))]

        ctime = ctime + random.randrange(1, TIME_RAND_DELTA)
        created = datetime.fromtimestamp(ctime).strftime('%Y-%m-%d %H:%M:%S')
        fh.write('{0}, {1}, "{2}", "{3}: {4}"\n'.format(i, 
                          USER_ID_BOUND + user_idx, 
                          created, 
                          user_name, 
                          text))

