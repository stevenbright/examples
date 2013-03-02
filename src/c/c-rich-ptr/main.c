/* fptr demo (simple function pointer within struct) */

#include <stdio.h>

typedef void (* foo)(int a, int b);

struct Rfp {
    int     a;
    foo     fp;
};

static void foo1(int a, int b) {
    fprintf(stdout, "foo1: %d - %d = %d\n", a, b, a - b);
}

static void foo2(int a, int b) {
    fprintf(stdout, "foo2: %d + %d = %d\n", a, b, a + b);
}

static void dispatch(struct Rfp rfp, int b) {
    rfp.fp(rfp.a, b);
}

int main() {
    struct Rfp rfp;

    /* foo1 */
    rfp.a = 1000;
    rfp.fp = foo1;
    dispatch(rfp, 200);

    rfp.a = 1;
    rfp.fp = foo2;
    dispatch(rfp, 2);

    return 0;
}

