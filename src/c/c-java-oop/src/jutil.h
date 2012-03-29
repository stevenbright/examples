
#pragma once

#include <stddef.h>


/* offsetof */
#if !defined(offsetof)
#define offsetof(st, m) ((char *)&((st *)(0))->m - (char *)((st *)(0)))
#endif


/* upcast */
#define upcast(ptr, st, m) ((st *)(((char *)ptr) - offsetof(st, m)))


/* inline special case */
#ifdef _MSC_VER
#define inline __forceinline
#endif


/* alloc */
void * xmalloc(size_t s);
void * xrealloc(void * p, size_t s);
void xfree(void * p);
