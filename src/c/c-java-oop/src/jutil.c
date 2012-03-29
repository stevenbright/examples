
#include "jutil.h"

#include <stdio.h>
#include <stdlib.h>



/* alloc */
void * xmalloc(size_t s) {
    void * result = malloc(s);
    if (result == NULL) {
	fputs("Not enough memory", stderr);
	abort();
    }
    return result;
}

void * xrealloc(void * p, size_t s) {
    void * result = realloc(p, s);
    if (s != 0 && result == NULL) {
	fputs("Not enough memory", stderr);
	abort();
    }
    return result;
}

void xfree(void * p) {
    free(p);
}
