
RUSTC       = rustc

.PHONY: all clean

all: target/hello

clean:
	rm -rf ./target

target:
	mkdir ./target

# Make targets

target/hello: target hello.rs
	rustc hello.rs -o target/hello

