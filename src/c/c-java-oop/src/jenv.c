
#include "jenv.h"
#include "jobj.h"
#include "jutil.h"

#include <string.h>
#include <assert.h>

/**
 * {@inheritDoc}
 */
void jdispose(struct Object * obj) {
    assert(obj != NULL);

    obj->vmt->finalize(obj);
    xfree(obj);
}


/**
 * {@inheritDoc}
 */
struct Object * Object_new() {
    struct Object * result = xmalloc(sizeof(struct Object));
    result->vmt = &Object_vmt;
    return result;
}

/**
 * {@inheritDoc}
 */
struct String * String_new_fromUtf(char * utfChars) {
    size_t len;
    struct String * result;
    jchar * arr;
    int i;

    len = strlen(utfChars);
    /* alloc String and string array at once */
    result = xmalloc(sizeof(struct String) + sizeof(jchar) * len);
    arr = (jchar *)((char *)result + sizeof(struct String));

    /* init string */
    result->vmt = &String_CharSequence_vmt;
    result->comparableImpl.vmt = &String_Comparable_vmt;
    result->cachedHashCode = 0;
    result->pos = 0;
    result->length = (int) len;
    result->chars = arr;

    for (i = 0; i < (int) len; ++i) {
        /* copy with transform to jchar */
        arr[i] = utfChars[i];
    }

    return result;
}
