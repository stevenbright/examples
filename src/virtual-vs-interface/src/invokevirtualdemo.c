#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

#define ITERATIONS      (100000000)

struct MyObj;

typedef int (* foo_fptr)(struct MyObj *);

struct MyObj_vtbl {
    foo_fptr foo;
};

struct MyObj {
    struct MyObj_vtbl * vtbl;
};

static int MyObj_foo(struct MyObj * o) {
    return 0;
}

/* ignored, introduced to cheat the compiler's static code analyzer */
static int MyObj_foo_2(struct MyObj * o) {
    fprintf(stderr, "Should never called, o = %p\n", (void *)o);
    return -1;
}

struct MyObj_vtbl vtbl = { &MyObj_foo };

/* fake, introduced to cheat compiler to prohibit optimization */
struct MyObj_vtbl vtbl_2 = { &MyObj_foo_2 };

static struct MyObj * createMyObj(int argc) {
    struct MyObj * o = malloc(sizeof(struct MyObj));
    o->vtbl = argc != 7 ? &vtbl : &vtbl_2;
    return o;
}

#define NANO_UNIT       (1000000000LL)
#define MICRO_UNIT      (1000LL)

int main(int argc, const char * argv[]) {
    struct MyObj * obj = createMyObj(argc);
    int i;

    for (i = 0; i < 4; ++i) {
        struct timeval start;
        struct timeval stop;
        int j;
        long long nanoTime;

        gettimeofday(&start, NULL);
        for (j = 0; j < ITERATIONS; ++j) {
            /* equivalent of java's obj.foo() */
            obj->vtbl->foo(obj);
        }
        gettimeofday(&stop, NULL);

        nanoTime = (stop.tv_sec - start.tv_sec) * NANO_UNIT + (stop.tv_usec - start.tv_usec) * MICRO_UNIT;
        fprintf(stdout, "Non-volatile C virtual call delta = %lld\n", nanoTime);
    }

    free(obj);
    return 0;
}

