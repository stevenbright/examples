import gzip
import random
import codecs

RECORDS_COUNT = 10000000
SEPARATOR_THRESHOLD = 10000

TABLE_NAME = "posts1"

USER_ID_BOUND = 10
USERS = ['alex', 'bob', 'cavin', 'alice', 'eva', 'jenny', 'dave', 'mark', 'cindy', 'larry', 'bill']
USER_COUNT = len(USERS)
TEXTS = ["hello", "preved", "salut", "hola, ombre", "hi there", "wut iz it?", "oops!", "simple text",
         "proze", "python", "rulezz", "postgres", "ultimate"];

FILENAME = "/tmp/posts1_init.sql"
#FILENAME = "/tmp/posts1_init.sql.gz"

INSERT_STMT_LHS = "INSERT INTO {0} (id, user_id, created, text) VALUES\n".format(TABLE_NAME)

#fh = gzip.open(FILENAME, "wb")
fh = codecs.open(FILENAME, "w", "UTF-8")
try:
    next = False
    j = 0
    for i in range(0, RECORDS_COUNT):
        if (j % SEPARATOR_THRESHOLD == 0):
            if (j > 0) : fh.write(";\n\n")
            fh.write(INSERT_STMT_LHS)
            next = False

        j = j + 1
        user_idx = random.randrange(0, USER_COUNT)
        user_name = USERS[user_idx]
        text = TEXTS[random.randrange(0, len(TEXTS))]
        r_date = "{0}-{1:02d}-{2:02d}".format(random.randrange(2001, 2012),
                                              random.randrange(1, 12), 
                                              random.randrange(1, 28))
        created = "DATE '{0}'".format(r_date)
        fh.write("{0}({1}, {2}, {3}, '{4}')\n"
                 .format(", " if next else "  ",
                         i, 
                          USER_ID_BOUND + user_idx, 
                          created, 
                          "{0} - {1}: {2}".format(r_date, user_name, text)))
        next = True
    fh.write(";\n\n");
finally:
    fh.close()

