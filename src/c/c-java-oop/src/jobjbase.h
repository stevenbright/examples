/**
 * Definition of the java.lang.Object and the corresponding helpers.
 * @file jobjbase.h
 * @author A. Shabanov, avshabanov@gmail.com
 */

#pragma once

#include "jobjfwd.h"

/*
 * Remarks:
 * void * ps - pointer to self
 */


/* ========================================================================= */

/* class Object */

/*
 * Object's VMT:
 * vmtOffset - offset of the virtual methods table from the base class (non-zero for 2-nd and further VMT's).
 * klass - pointer to the class this object corresponds to.
 *
 * The following virtual methods belong to the object:
 * void finalize();
 * String toString();
 * int hashCode();
 * boolean equals(Object obj);
 */
#define OBJECT_VMT() \
    int vmtOffset; \
    struct Class * klass; \
    \
    void (* finalize)(void * ps); \
    struct String * (* toString)(void * ps); \
    int (* hashCode)(void * ps); \
    boolean (* equals)(void * ps, struct Object * obj)

struct ObjectVmt {
    OBJECT_VMT();
};

struct Object {
    struct ObjectVmt * vmt;
};

/* ========================================================================= */

/* Methods */
void Object_finalize(void * ps);
struct String * Object_toString(void * ps);
int Object_hashCode(void * ps);
boolean Object_equals(void * ps, struct Object * obj);

/* VMT */
struct ObjectVmt Object_vmt;


/**
 * Unchecked cast helper - casts non-null pointer to the desired object which must implement
 * the object's (ps) interface.
 */
#define NONNULL_CAST(st, ps) ((st *)((char *)(ps) - ((struct Object *)(ps))->vmt->vmtOffset))

#define NULLABLE_CAST(st, ps) ((ps) != NULL ? NONNULL_CAST(st, ps) : NULL)

