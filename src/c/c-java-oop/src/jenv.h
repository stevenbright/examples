/**
 * Java environment utilities.
 * @file jenv.h
 * @author A. Shabanov, avshabanov@gmail.com
 */

#pragma once

#include "jobjfwd.h"


/**
 * Generic dispose method.
 * FIXME: dispose by using jenv.
 * @param obj Object to be disposed.
 */
void jdispose(struct Object * obj);


/**
 * Allocates object.
 * @return Allocated object.
 */
struct Object * Object_new();

/**
 * Allocates string.
 * @param utfChars UTF-8-encoded null-terminated string.
 * @return Newly allocated string.
 */
struct String * String_new_fromUtf(char * utfChars);
