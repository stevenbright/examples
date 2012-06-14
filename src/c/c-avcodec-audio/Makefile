
#
# Makefile for make-noise (mew)
#

FFPATH  = $(HOME)/opt/local

IPATHS	= -I$(FFPATH)/include
CFLAGS	= $(IPATHS) -Wall -O2
CC		= gcc
LPATHS	= -L$(FFPATH)/lib
LDFLAGS	= -Wl,--no-undefined $(LPATHS) -lswscale -lavdevice -lavformat -lavcodec -lavutil -lavcore -lpostproc -lavfilter -lz -lm
LINKER	= gcc
APPNAME = mew

all: $(APPNAME)

$(APPNAME): main-noise.o
	$(LINKER) $(LDFLAGS) main-noise.o -o $(APPNAME)

main-noise.o: main-noise.c
	$(CC) $(CFLAGS) -c main-noise.c -o main-noise.o

clean:
	rm -rf *.o $(APPNAME)

