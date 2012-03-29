
#include "jobj.h"
#include "jutil.h"

#include <assert.h>

/* ========================================================================= */

/* class Object */


void Object_finalize(void * ps) {
    /* do nothing */
}

struct String * Object_toString(void * ps) {
    return NULL;
}

int Object_hashCode(void * ps) {
    return (int)ps;
}

boolean Object_equals(void * ps, struct Object * obj) {
    return NONNULL_CAST(struct Object, ps) == NULLABLE_CAST(struct Object, obj);
}

/* obj class */
static struct Class g_object_class;

/* VMT */
struct ObjectVmt Object_vmt = {
    0, /* vmtOffset */
    &g_object_class, /* class */
    &Object_finalize,
    &Object_toString,
    &Object_hashCode,
    &Object_equals
};



/* Object.class */

static struct Method g_object_method_finalize = {
    "finalize"
};

static struct Method g_object_method_toString = {
    "toString"
};

static struct Method g_object_method_hashCode = {
    "hashCode"
};

static struct Method g_object_method_equals = {
    "equals"
};


static struct MethodArray g_object_class_declared_methods = {
    4, /* methods of the object */
    {
        &g_object_method_finalize, 
        &g_object_method_toString, 
        &g_object_method_hashCode, 
        &g_object_method_equals
    }
};


static const jchar g_object_class_name_char_array[] = {
    'O', 'b', 'j', 'e', 'c', 't'
};

static struct String g_object_class_name = {
    &String_CharSequence_vmt,   /* String's CharSequence vmt */
    { &String_Comparable_vmt },  /* String's comparableImpl */
    0,      /* cachedHashCode */
    0,      /* pos */
    6,      /* length */

    /* chars */
    g_object_class_name_char_array
};


/* TODO: don't initialize object class here in case of the the class loader feature */
static struct Class g_object_class = {
    &Class_vmt,
    
    /* MethodArray * declaredMethods */
    &g_object_class_declared_methods,

    /* String * name */
    &g_object_class_name
};


