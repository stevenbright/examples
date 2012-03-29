
#include "jobj.h"

#include <assert.h>


/* ========================================================================= */

/* Class */

static struct Class Class_class;

struct ObjectVmt Class_vmt = {
    0,
    &Class_class, /* class of java.lang.Class */

    &Object_finalize,
    &Object_toString,
    &Object_hashCode,
    &Object_equals
};

/* Class.class */

static struct Method g_class_method_finalize = {
    "finalize"
};

static struct Method g_class_method_toString = {
    "toString"
};

static struct Method g_class_method_hashCode = {
    "hashCode"
};

static struct Method g_class_method_equals = {
    "equals"
};


static struct MethodArray g_class_class_declared_methods = {
    4, /* TODO: methods of the class */
    {
        &g_class_method_finalize, 
        &g_class_method_toString, 
        &g_class_method_hashCode, 
        &g_class_method_equals
    }
};


static const jchar g_class_class_name_char_array[] = {
    'C', 'l', 'a', 's', 's'
};

static struct String g_class_class_name = {
    &String_CharSequence_vmt,       /* String's CharSequence vmt */
    { &String_Comparable_vmt },     /* String's comparableImpl */
    0,                              /* cachedHashCode */
    0,                              /* pos */
    5,                              /* length */
    g_class_class_name_char_array   /* chars */
};


static struct Class Class_class = {
    &Class_vmt,
    
    /* MethodArray * declaredMethods */
    &g_class_class_declared_methods,

    /* String * name */
    &g_class_class_name
};

/* ========================================================================= */

/* String */

struct String * String_toString(void * ps) {
    struct String * self = NONNULL_CAST(struct String, ps);
    return self;
}

int String_hashCode(void * ps) {
    struct String * self = NONNULL_CAST(struct String, ps);
    
    if (self->cachedHashCode == 0) {
        /* calculate hash code */
        int h = 1;
        int i;

        for (i = 0; i < self->length; ++i) {
            h += 31 * self->chars[i];
        }

        self->cachedHashCode = h;
    }

    return self->cachedHashCode;
}

boolean String_equals(void * ps, struct Object * obj) {
    struct String * self = NONNULL_CAST(struct String, ps);
    struct String * rhs;
    int i;
    
    /* if (obj == null || !(obj instanceof String)) */
    if (obj == NULL || obj->vmt->klass != &String_class) {
        return false;
    }

    /* unchecked cast */
    rhs = NONNULL_CAST(struct String, obj);
    if (self->length != rhs->length) {
        return false;
    }

    /* compare chars */
    /* TODO: memcmp */
    for (i = 0; i < self->length; ++i) {
        if (self->chars[i] != rhs->chars[i]) {
            return false;
        }
    }

    return true;
}

jchar String_charAt(void * ps, int pos) {
    struct String * self = ps;
    
    if (pos >= self->length) {
        /* TODO: throw new IndexOutOfBoundsException */
        assert(!"Not impl");
    }

    return self->chars[pos];
}

int String_length(void * ps) {
    struct String * self = ps;
    return self->length;
}

struct CharSequence * String_subSequence(void * ps, int pos, int count) {
    /* TODO: new CharSequence */
    assert(!"Not impl");
    return NULL;
}

int String_compareTo(void * ps, struct Object * obj) {
    int result;
    
    if (obj != NULL) {
        struct String * rhs;
        struct String * self = NONNULL_CAST(struct String, ps);

        /* check that object is string */
        if (obj->vmt->klass != &String_class) {
            /* TODO: throw new IllegalArgumentException */
            assert(!"Not impl");
        }
        rhs = NONNULL_CAST(struct String, obj);

        if (self->length == rhs->length) {
            /* strings of equal length */
            /* TODO: memcmp */
            int i;
            for (i = 0; i < self->length; ++i) {
                result = (self->chars[i] - rhs->chars[i]);
                if (result != 0) {
                    break;
                }
            }
        } else {
            /* length mismatch */
            result = self->length - rhs->length;
        }
    } else {
        /* null rhs, non-null string always greater than null one. */
        result = -1;
    }

    return result;
}

/* CharSequence VMT */
struct CharSequenceVmt String_CharSequence_vmt = {
    0, /* vmtOffset */
    &String_class, /* class */

    /* Object VMT */
    &Object_finalize,
    &String_toString,
    &String_hashCode,
    &String_equals,

    /* CharSequence VMT */
    &String_charAt,
    &String_length,
    &String_subSequence
};

/* Comparable VMT */
struct ComparableVmt String_Comparable_vmt = {
    offsetof(struct String, comparableImpl), /* vmtOffset */
    &String_class, /* class */

    /* Object VMT */
    &Object_finalize,
    &String_toString,
    &String_hashCode,
    &String_equals,

    /* Comparable VMT */
    &String_compareTo
};


/* String.class */

static struct Method g_string_method_finalize = {
    "finalize"
};

static struct Method g_string_method_toString = {
    "toString"
};

static struct Method g_string_method_hashCode = {
    "hashCode"
};

static struct Method g_string_method_equals = {
    "equals"
};

static struct Method g_string_method_charAt = {
    "charAt"
};


static struct MethodArray g_string_class_declared_methods = {
    5, /* TODO: methods of the object */
    {
        &g_string_method_finalize, 
        &g_string_method_toString, 
        &g_string_method_hashCode, 
        &g_string_method_equals,
        &g_string_method_charAt
    }
};


static const jchar g_string_class_name_char_array[] = {
    'S', 't', 'r', 'i', 'n', 'g'
};

static struct String g_string_class_name = {
    &String_CharSequence_vmt,       /* String's CharSequence vmt */
    { &String_Comparable_vmt },     /* String's comparableImpl */
    0,                              /* cachedHashCode */
    0,                              /* pos */
    6,                              /* length */
    g_string_class_name_char_array  /* chars */
};

struct Class String_class = {
    &Class_vmt,
    
    /* MethodArray * declaredMethods */
    &g_string_class_declared_methods,

    /* String * name */
    &g_string_class_name
};

/* Non-virtual methods */
struct CharArray * String_toCharArray(struct String * self) {
    assert(!"Not impl");
    return NULL;
}
