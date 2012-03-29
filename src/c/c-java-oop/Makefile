
CFLAGS  = -Wall -pedantic -g
CC      = gcc
LFLAGS  = -g
LINKER  = gcc

# specific app name
APPNAME = bin/c-java-oop



all: $(APPNAME)

$(APPNAME): bin obj/main.o obj/jenv.o obj/jobj.o obj/jobjbase.o obj/jutil.o
	$(LINKER) $(LFLAGS) obj/*.o -o $(APPNAME)

bin:
	mkdir bin

obj:
	mkdir obj


obj/main.o: obj src/main.c
	$(CC) $(CFLAGS) -c src/main.c -o obj/main.o

obj/jenv.o: obj src/jenv.c
	$(CC) $(CFLAGS) -c src/jenv.c -o obj/jenv.o

obj/jobj.o: obj src/jobj.c
	$(CC) $(CFLAGS) -c src/jobj.c -o obj/jobj.o

obj/jobjbase.o: obj src/jobjbase.c
	$(CC) $(CFLAGS) -c src/jobjbase.c -o obj/jobjbase.o

obj/jutil.o: obj src/jutil.c
	$(CC) $(CFLAGS) -c src/jutil.c -o obj/jutil.o


clean:
	rm -rvf obj bin