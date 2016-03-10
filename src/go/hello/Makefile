
# Run `make` to compile
# Run `make clean` to clean target folder
# Run `make run` to run compiled program

export GOPATH := $(PWD)
export PATH := $(PATH):$(GOPATH)/bin

.PHONY: clean compile run

#
# Targets
#

all: compile

bin/hello: src/main.go
	@echo "GOPATH =" $$GOPATH
	@cd src
	@echo "PWD =" $(PWD)
	@go install

compile: bin/hello

run:
	hello

clean:
	rm -rf bin

