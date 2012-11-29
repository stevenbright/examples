#include <stdio.h>
#include <stdlib.h>

#include <sys/time.h>

typedef long long   jlong;

struct Node;
struct Visitor;

/* Node.apply(Visitor v) */
typedef jlong (* apply_fptr)(struct Node * self, struct Visitor * v);

/* Visitor.visitX(Node n) */
typedef jlong (* visitNode_fptr)(struct Visitor * self, struct Node * n);


struct NodeVtbl {
    apply_fptr apply;
};

struct Node {
    struct NodeVtbl * vtbl;
};

/* Visitor's methods */
struct VisitorVtbl {
    visitNode_fptr visitExpr;
    visitNode_fptr visitStmt;
    visitNode_fptr visitUnit;
    visitNode_fptr visitKlas;
};

struct Visitor {
    struct VisitorVtbl * vtbl;
};

/* Node descendant's methods */

static jlong Expr_apply(struct Node * self, struct Visitor * v) {
    return v->vtbl->visitExpr(v, self);
}

static jlong Expr_getFoo(struct Node * self) {
    return 1LL;
}

static jlong Stmt_apply(struct Node * self, struct Visitor * v) {
    return v->vtbl->visitStmt(v, self);
}

static jlong Stmt_getBar(struct Node * self) {
    return 100000LL;
}

static jlong Unit_apply(struct Node * self, struct Visitor * v) {
    return v->vtbl->visitUnit(v, self);
}

static jlong Unit_getBaz(struct Node * self) {
    return 10000000000LL;
}

static jlong Klas_apply(struct Node * self, struct Visitor * v) {
    return v->vtbl->visitKlas(v, self);
}

static jlong Klas_getRal(struct Node * self) {
    return 0;
}

/* PosVisitor's methods */

static jlong PosVisitor_visitExpr(struct Visitor * self, struct Node * n) {
    return Expr_getFoo(n);
}

static jlong PosVisitor_visitStmt(struct Visitor * self, struct Node * n) {
    return Stmt_getBar(n);
}

static jlong PosVisitor_visitUnit(struct Visitor * self, struct Node * n) {
    return Unit_getBaz(n);
}

static jlong PosVisitor_visitKlas(struct Visitor * self, struct Node * n) {
    return Klas_getRal(n);
}

/* NegVisitor's methods */

static jlong NegVisitor_visitExpr(struct Visitor * self, struct Node * n) {
    return -Expr_getFoo(n);
}

static jlong NegVisitor_visitStmt(struct Visitor * self, struct Node * n) {
    return -Stmt_getBar(n);
}

static jlong NegVisitor_visitUnit(struct Visitor * self, struct Node * n) {
    return -Unit_getBaz(n);
}

static jlong NegVisitor_visitKlas(struct Visitor * self, struct Node * n) {
    return -Klas_getRal(n);
}

/* vtables */

struct NodeVtbl g_ExprVtbl = { &Expr_apply };
struct NodeVtbl g_StmtVtbl = { &Stmt_apply };
struct NodeVtbl g_UnitVtbl = { &Unit_apply };
struct NodeVtbl g_KlasVtbl = { &Klas_apply };

struct VisitorVtbl g_PosVisitorVtbl = {
    &PosVisitor_visitExpr,
    &PosVisitor_visitStmt,
    &PosVisitor_visitUnit,
    &PosVisitor_visitKlas
};

struct VisitorVtbl g_NegVisitorVtbl = {
    &NegVisitor_visitExpr,
    &NegVisitor_visitStmt,
    &NegVisitor_visitUnit,
    &NegVisitor_visitKlas
};


#define ITERATIONS  (1000000)

struct Node * createNodes() {
    struct Node * nodes = malloc(sizeof(struct Node) * ITERATIONS);
    for (int i = 0; i < ITERATIONS; ++i) {
        struct Node * n = nodes + i;
        switch (i % 4) {
            case 0:
                n->vtbl = &g_ExprVtbl;
                break;
            case 1:
                n->vtbl = &g_StmtVtbl;
            case 2:
                n->vtbl = &g_UnitVtbl;
            default:
                n->vtbl = &g_KlasVtbl;
        }
    }
    return nodes;
}

struct Visitor * createVisitors() {
    struct Visitor * visitors = malloc(sizeof(struct Visitor) * ITERATIONS);
    for (int i = 0; i < ITERATIONS; ++i) {
        struct Visitor * v = visitors + i;
        v->vtbl = (i % 2 == 0) ? &g_PosVisitorVtbl : &g_NegVisitorVtbl;
    }
    return visitors;
}

#define NANO_UNIT       (1000000000LL)
#define MICRO_UNIT      (1000LL)

static void testIfc() {
    struct Node * nodes = createNodes();
    struct Visitor * visitors = createVisitors();
    struct timeval start;
    struct timeval stop;
    jlong accum = 0;
    jlong nanoTime;
    int i;

    gettimeofday(&start, NULL);
    for (i = 0; i < ITERATIONS; ++i) {
        struct Node * n = nodes + i;
        struct Visitor * v = visitors + i;
        accum += n->vtbl->apply(n, v);
    }
    gettimeofday(&stop, NULL);

    nanoTime = (stop.tv_sec - start.tv_sec) * NANO_UNIT + (stop.tv_usec - start.tv_usec) * MICRO_UNIT;
    fprintf(stdout, "Accum = %lld, delta = %lld ns\n", accum, nanoTime);
}

int main(int argc, char * argv[]) {
    for (int i = 0; i < 3; ++i) {
        testIfc();
    }
    return 0;
}

