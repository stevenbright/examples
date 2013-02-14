#include <stdio.h>
#include <stdlib.h>


struct node {
    int val;
    struct node * next;
};

static struct node * list = NULL;

static void add(int val) {
    struct node * n = malloc(sizeof(struct node));
    n->next = list;
    n->val = val;
    list = n;
}

static void prn() {
    for (struct node * n = list; n != NULL; n = n->next) {
        fprintf(stdout, "(%d) ", n->val);
    }
    fputs(".\n", stdout);
}


static void reverse(struct node ** list) {
    struct node * i = *list;
    struct node * tail = NULL;

    while (i != NULL) {
        struct node * prev = i->next;
        i->next = tail;
        tail = i;
        i = prev;
    }

    *list = tail;
}

int main() {
    add(1);
    add(2);
    add(3);
    add(4);

    prn();

    reverse(&list);

    prn();

    return 0;
}


