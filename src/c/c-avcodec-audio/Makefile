
#
# Makefile for make-noise (mew)
#

DEPS    = libavdevice libavformat libavfilter libavcodec libswresample libswscale libavutil

CFLAGS	= $(shell pkg-config --cflags $(DEPS)) -Wall -O2
CC		= gcc
LDFLAGS = $(shell pkg-config --libs $(DEPS))
LINKER	= gcc
APPNAME = mew

all: $(APPNAME)

$(APPNAME): main-noise.o
	$(LINKER) main-noise.o $(LDFLAGS) -o $(APPNAME)

main-noise.o: main-noise.c
	$(CC) $(CFLAGS) -c main-noise.c -o main-noise.o

clean:
	rm -rf *.o $(APPNAME)

