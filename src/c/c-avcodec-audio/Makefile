
#
# Makefile for make-noise (mew)
#

IPATHS	= -I$(HOME)/bin/opt/include
CFLAGS	= $(IPATHS) -Wall -O2
CC		= gcc
LPATHS	= -L$(HOME)/bin/opt/lib
LFLAGS	= $(LPATHS) -lavcodec -lavformat -lavutil -lavcore -lavfilter -lavdevice -lmp3lame -lxvidcore -lx264 -lz -lm
LINKER	= gcc
APPNAME = mew

all: $(APPNAME)

$(APPNAME): main-noise.o
	$(LINKER) $(LFLAGS) main-noise.o -o $(APPNAME)

main-noise.o: main-noise.c
	$(CC) $(CFLAGS) -c main-noise.c -o main-noise.o

clean:
	rm -rf *.o $(APPNAME)

