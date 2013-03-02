#include <stdio.h>
#include <stdlib.h>

void * xmalloc(size_t size) {
  void * p = malloc(size);
  if (p == NULL) {
    abort();
  }
  return p;
}

struct Expr {
  int tag;
};

struct Lambda {
  struct Expr parent;
  int body;
  int param;
};

struct Var {
  struct Expr parent;
  const char * name;
};

struct Pair {
  struct Expr parent;
  struct Expr * left;
  struct Expr * right;
};

#define LAMBDA_TYPE (0x03)

#define EXPR_IS_LAMBDA(expr) ((expr)->tag == LAMBDA_TYPE)

static struct Expr * Pair_reduce(struct Expr * p) {
  return NULL; // TODO
}


