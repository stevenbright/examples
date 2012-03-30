/**
 * Definitions of the java.lang.* and java.lang.reflect.*
 * @file jobj.h
 * @author A. Shabanov, avshabanov@gmail.com
 */

#pragma once

#include "jobjbase.h"


/* ========================================================================= */

/* final class java.lang.Class */

struct Class {
    struct ObjectVmt * vmt;

    /* Method[] getDeclaredMethods() */
    struct MethodArray * declaredMethods;

    /* String getName(); */
    struct String * name;
};

struct ObjectVmt Class_vmt;

/* ========================================================================= */

/* interface java.lang.CharSequence */

struct CharSequenceVmt {
    /* Object */
    OBJECT_VMT();

    /* CharSequence */
    jchar (* charAt)(void * ps, int pos);
    int (* length)(void * ps);
    struct CharSequence * (* subSequence)(void * ps, int pos, int count);
    /* overridden String toString(); */
};

struct CharSequence {
    struct CharSequenceVmt * vmt;
};

/* ========================================================================= */

/* interface java.lang.Comparable */

struct ComparableVmt {
    /* Object */
    OBJECT_VMT();

    /* Comparable */
    int (* compareTo)(void * ps, struct Object * obj);
};

struct Comparable {
    struct ComparableVmt * vmt;
};


/* ========================================================================= */

/* final class java.lang.String implements CharSequence, Comparable, Serializable */

struct String {
    struct CharSequenceVmt * vmt;
    struct Comparable comparableImpl;

    /* cached hash code */
    int cachedHashCode;

    /* string from pos to length */
    int pos;
    int length;
    const jchar * chars;
};

struct CharSequenceVmt String_CharSequence_vmt;
struct ComparableVmt String_Comparable_vmt;

struct Class String_class;

struct CharArray * String_toCharArray(struct String * self);

/* ========================================================================= */

/* final class java.lang.reflect.Method implements java.lang.reflect.AccessibleObject */
struct Method {
    /* TODO: implement as Object */
    const char * utfName;
};

