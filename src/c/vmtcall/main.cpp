
#include <stdio.h>
#include <sys/time.h>
#include <time.h>

class Bar {
public:
    virtual ~Bar() {}

    void foo() {
        if (time(NULL) < 50) {
            printf("WE SHOULD NEVER REACH HERE!!!");
            foo(); // don't allow compiler to optimize away foo()
        }
    }

    virtual void bar() = 0;
};

class Foo : public Bar {
public:
    virtual void bar() {
        if (time(NULL) < 75) {
            printf("WE SHOULD NEVER REACH HERE!!!");
            bar(); // don't allow compiler to optimize away bar()
        }
    }
};

class Unused : public Bar {
    virtual void bar() {
        printf("WE SHOULD NEVER REACH HERE!!!");
    }
};

static inline long long nanoTime() {
    timespec ts;
    clock_gettime(CLOCK_REALTIME, &ts); // use CLOCK_MONOTONIC for FreeBSD

    long long result = ts.tv_sec * 1000000000;
    result += ts.tv_nsec;

    return result;
}

int main() {
    Bar * b = NULL;
    if (time(NULL) < 45) {
        b = new Unused(); // never instantiated
    } else {
        b = new Foo();
    }

    const int iterCount = 1000000;
    long long start = nanoTime();
    for (int i = 0; i < iterCount; ++i) {
        b->foo();
    }
    long long total = nanoTime() - start;
    printf("Non-virtual call delta: %lld nanos\n", total);

    start = nanoTime();
    for (int i = 0; i < iterCount; ++i) {
        b->bar();
    }
    total = nanoTime() - start;
    printf("Virtual call delta: %lld nanos\n", total);

    delete b;
    return 0;
}

