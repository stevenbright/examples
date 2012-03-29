
#include <stdio.h>

#include "jobj.h"
#include "jutil.h"
#include "jenv.h"


static void jputs(struct String * str, FILE * ostream) {
    int i;
    for (i = 0; i < str->length; ++i) {
        fputc(str->chars[i], ostream);
    }
}



static void testJObjFwd() {
    fprintf(stdout, "MethodArray size = %d\n", sizeof(struct MethodArray));
}

static void testObj() {
    struct Object * o;
    int h;

    o = Object_new();

    h = o->vmt->hashCode(o);
    fprintf(stdout, "o.hashCode() = %d\n", h);

    fputs("o.getClass().getName() = ", stdout);
    jputs(o->vmt->klass->name, stdout);
    fputs("\n", stdout);


    jdispose(o);
}

#define Comparable_compareTo(c, obj) ((c)->vmt->compareTo(c, (struct Object *) obj))

static void testStrings() {
    struct String * s11 = String_new_fromUtf("s1");
    struct String * s12 = String_new_fromUtf("s4");
    struct String * s13 = String_new_fromUtf("s1");

    /* comparable transform */
    {
        /* Comparable c1 = s11; */
        struct Comparable * c1 = &s11->comparableImpl;
        struct Comparable * c2 = &s12->comparableImpl;
        struct Comparable * c3 = &s13->comparableImpl;
        int r;

        r = Comparable_compareTo(c1, c1);
        fprintf(stdout, "c1.compareTo(c1) = %d\n", r);
        
        r = Comparable_compareTo(c1, c2);
        fprintf(stdout, "c1.compareTo(c2) = %d\n", r);

        r = Comparable_compareTo(c2, c1);
        fprintf(stdout, "c2.compareTo(c1) = %d\n", r);

        r = Comparable_compareTo(c1, c3);
        fprintf(stdout, "c1.compareTo(c3) = %d\n", r);

        r = Comparable_compareTo(c3, s12);
        fprintf(stdout, "c3.compareTo(s12) = %d\n", r);

        r = Comparable_compareTo(c3, s11);
        fprintf(stdout, "c3.compareTo(s11) = %d\n", r);

        /* NB: direct calls to String_equals might be used here */
        fprintf(stdout, "c1.equals(c3) = %d\n", c1->vmt->equals(c1, (struct Object *)c3));
        fprintf(stdout, "c1==c3 = %d\n", Object_equals(c1, (struct Object *)c3));
        fprintf(stdout, "c1.equals(c2) = %d\n", c1->vmt->equals(c1, (struct Object *)c2));
    }

    jdispose((struct Object *)s11);
    jdispose((struct Object *)s12);
    jdispose((struct Object *)s13);
}

int main(int argc, char ** argv) {
    fputs("c-oop sample\n", stdout);

    if (argc < 9) {
        testStrings();
    } else if (argc > 9) {
        testObj();
        testJObjFwd();
    }

    getchar();
    return 0;
}
