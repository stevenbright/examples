/**
 * Forward declarations of the defined objects.
 * @file jobjfwd.h
 * @author A. Shabanov, avshabanov@gmail.com
 */

#pragma once

#include "jbase.h"

/* ========================================================================= */

/* Classes */

struct Object;
struct Class;
struct String;



/* ========================================================================= */

/* arrays */

struct MethodArray {
    int length;
    struct Method * arr[0];
};

struct CharArray {
    int length;
    jchar arr[0];
};
