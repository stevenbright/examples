#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include <sys/time.h>

/* countof */
#ifndef countof
#define countof(arr)    (sizeof(arr)/sizeof((arr)[0]))
#endif

/* unlikely */
#ifndef unlikely
#ifdef __GNUC__
#define unlikely(x)     __builtin_expect((x),0)
#else
#warning "Add compiler-specific 'unlikely' macro here"
#define unlikely(x) (x)
#endif /* __GNUC__ */
#endif /* unlikely */


/******************************************************************************************
 * Object model concept BEGIN
 ******************************************************************************************/

/** Represents arbitrary interface method that returns nothing */
typedef void (* unknown_fn_ptr)();


/** Represents arbitrary interfaces table */
struct Itable {
    /** Unique interface identifier */
    int id;
    
    /** Count of methods defined in the given interface */
    int count;
    
    /** Methods table */
    unknown_fn_ptr * methods;
};

/** Pointer to virtual methods table */
struct Vtable;

/** Class data */
struct Clazz {
    /** Pointer to virtual methods table */
    struct Vtable * vtable;
    
    /** Count of interface tables that this class implements */
    int itable_count;
    
    /** Interface tables */
    struct Itable * itables;
    
    /* ... the rest of the possible class data omitted for clarity... */
};

/** Every object must preserve the given structure */
struct Object {
    struct Clazz * clazz;
};

/**
 * Finds pointer to interface, caller party must be absolutely sure that the given interface exists.
 */
static struct Itable * find_interface(struct Clazz * clazz, int interface_id) {
    int i;
    assert(clazz != NULL);
    
    for (i = 0; i < clazz->itable_count; ++i) {
        struct Itable * itable = clazz->itables + i;
        if (itable->id == interface_id) {
            /* ok, return interface table */
            return itable;
        }
    }
    
    fprintf(stderr, "Attempt to find non-existing interface #%d, aborting", interface_id);
    exit(-1);
    return NULL;
}

/**
 * Finds pointer to an interface method before calling party may invoke it.
 * Caller party *must* ensure that interface_id and method_index are absolutely correct.
 */
static unknown_fn_ptr find_interface_method(struct Clazz * clazz, int interface_id, int method_index) {
    struct Itable * itable = find_interface(clazz, interface_id);
    
    /* XXX: assert that the method exist? - add debug info to itable? */
    return itable->methods[method_index];
}

/******************************************************************************************
 * Object model concept END
 ******************************************************************************************/




/* Non-reusable code part */


#define ITERATIONS          (100000000)


/******************************************************************************************
 * interface Obj BEGIN
 ******************************************************************************************/
/* id of Obj interface */
#define I_OBJ_ID            (32452312)

/* signature of Obj.foo method */
typedef int (* Obj_foo_ptr)(void * self);
/* index of foo method in the interface table */
#define I_OBJ_IDX_FOO       (0)
/******************************************************************************************
 * interface Obj END
 ******************************************************************************************/

/*
 public class MyObj implements Obj { int foo(); }
 */
struct MyObj {
    struct Clazz * clazz;
};

/*
 @Override public int MyObj.foo() { return 0 }
 */
static int MyObj_foo(void * self) {
    return 0;
}

static unknown_fn_ptr g_MyObj_Obj_methods[] = { (unknown_fn_ptr)&MyObj_foo };
static struct Itable g_MyObj_Itables[] = {
    { I_OBJ_ID, countof(g_MyObj_Obj_methods), g_MyObj_Obj_methods }
};

static struct Clazz g_MyObj_Clazz = {
    NULL, /* vtable */
    1, /* itable_count */
    g_MyObj_Itables /* itables */
};



static struct MyObj * createMyObj() {
    struct MyObj * o = malloc(sizeof(struct MyObj));
    o->clazz = &g_MyObj_Clazz;
    return o;
}


static long long as_nano_time(struct timeval * start, struct timeval * stop) {
    const int NANO_UNIT = 1000000000LL;
    const int MICRO_UNIT = 1000LL;
    return (stop->tv_sec - start->tv_sec) * NANO_UNIT + (stop->tv_usec - start->tv_usec) * MICRO_UNIT;
}

static void demo_with_find_interface_method() {
    struct MyObj * obj = createMyObj();
    int i;
    /* Cached pointer to foo method */
    Obj_foo_ptr cached_foo_ptr = NULL;
    
    for (i = 0; i < 4; ++i) {
        struct timeval start;
        struct timeval stop;
        int j;
        
        gettimeofday(&start, NULL);
        for (j = 0; j < ITERATIONS; ++j) {
            /* equivalent of java's obj.foo() */
            if (unlikely(cached_foo_ptr == NULL)) {
                cached_foo_ptr = (Obj_foo_ptr) find_interface_method(obj->clazz, I_OBJ_ID, I_OBJ_IDX_FOO);
            }
            cached_foo_ptr(obj);
        }
        gettimeofday(&stop, NULL);
        
        fprintf(stdout, "Non-volatile C interface call delta = %lld\n", as_nano_time(&start, &stop));
    }
    
    free(obj);
}

/* this method is like the demo_with_find_interface but resembles autogenerated code */
static void demo_with_find_interface() {
    struct MyObj * myObj = createMyObj();
    int i;
    /* 
     * Cached casted pointer to Obj itable, compiler must be pretty sure that
     * myObj implements the given interface
     */
    struct Itable * objItable = NULL;
    
    for (i = 0; i < 4; ++i) {
        struct timeval start;
        struct timeval stop;
        int j;
        
        gettimeofday(&start, NULL);
        for (j = 0; j < ITERATIONS; ++j) {
            /* equivalent of java's myObj.foo() */
            if (unlikely(objItable == NULL)) {
                objItable = find_interface(myObj->clazz, I_OBJ_ID);
            }
            unknown_fn_ptr fooPtr = objItable->methods[I_OBJ_IDX_FOO];
            int result = ((Obj_foo_ptr) fooPtr)(myObj);
            assert(result == 0);
        }
        gettimeofday(&stop, NULL);
        
        fprintf(stdout, "Non-volatile C interface call delta = %lld\n", as_nano_time(&start, &stop));
    }
    
    free(myObj);
}

int main(int argc, const char * argv[]) {
    if (argc > 1 && strcmp(argv[1], "fim") == 0) {
        fprintf(stdout, "Demo with find_interface_method\n");
        demo_with_find_interface_method();
    } else {
        fprintf(stdout, "Demo with find_interface\n");
        demo_with_find_interface();
    }
    return 0;
}

