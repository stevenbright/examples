
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include <stdbool.h>

/* timer - for perf reasons, switch off if not need */
#include <sys/time.h>


/*
 
 mem alloc routines
 
 */

void * xmalloc(size_t size) {
    void * p = malloc(size);
    if (p == NULL) {
        fputs("Out of memory", stderr);
        abort();
    }
    return p;
}

void xfree(void * p) {
    free(p);
}



/*
 
 field type and operations
 
 */

struct field_t {
    int w;
    int h;
    int * arr;
};

static struct field_t *
alloc_field(int w, int h) {
    assert(w > 0 && h > 0);
    size_t size = sizeof(struct field_t) + sizeof(int) * w * h;
    struct field_t * f = xmalloc(size);
    memset(f, 0, size);
    
    f->w = w;
    f->h = h;
    f->arr = (int *)(((char *)f) + sizeof(struct field_t));
    
    return f;
}

/* field accessor */
#define AT2(f, hx, wx) *(assert((wx) < (f)->w && (hx) < (f)->h), (f)->arr + (wx) + (hx) * (f)->h)


/*
 
 sudoku-related routines
 
 */

static void
init_field(struct field_t * f) {
    /* 0 line */
    AT2(f, 0, 0) = 2;
    AT2(f, 0, 2) = 4;
    AT2(f, 0, 4) = 7;
    AT2(f, 0, 6) = 9;
    AT2(f, 0, 8) = 1;
    
    /* 1 line */
    
    /* 2 line */
    AT2(f, 2, 1) = 5;
    AT2(f, 2, 2) = 8;
    AT2(f, 2, 4) = 3;
    AT2(f, 2, 6) = 7;
    AT2(f, 2, 7) = 4;
    
    /* 3 line */
    AT2(f, 3, 3) = 9;
    AT2(f, 3, 5) = 7;
    
    /* 4 line */
    AT2(f, 4, 1) = 9;
    AT2(f, 4, 2) = 6;
    AT2(f, 4, 6) = 8;
    AT2(f, 4, 7) = 2;
    
    /* 5 line */
    AT2(f, 5, 3) = 5;
    AT2(f, 5, 5) = 2;
    
    /* 6 line */
    AT2(f, 6, 1) = 3;
    AT2(f, 6, 2) = 5;
    AT2(f, 6, 4) = 1;
    AT2(f, 6, 6) = 6;
    AT2(f, 6, 7) = 8;
    
    /* 7 line */
    
    /* 8 line */
    AT2(f, 8, 0) = 4;
    AT2(f, 8, 2) = 1;
    AT2(f, 8, 4) = 5;
    AT2(f, 8, 6) = 2;
    AT2(f, 8, 8) = 3;
}

static void 
print_field(struct field_t * f) {
	for (int i = 0; i < f->h; ++i) {
		printf("\n");
		for (int j = 0; j < f->w; ++j) {
			int c = AT2(f, i, j);
			if (c == 0) {
				printf(" .");
			} else {
				printf(" %d", c);
			}
		}
	}
    printf("\n");
}

struct coord_t {
    int h; /* horizontal */
    int v; /* vertical */
};

struct coord_arr_t {
    int size;
    int allocated;
    struct coord_t * arr;
};

static struct coord_arr_t *
alloc_coord_arr(int allocated) {
    assert(allocated > 0);
    int size = sizeof(struct coord_arr_t) + allocated * sizeof(struct coord_t);
    struct coord_arr_t * r = xmalloc(size);
    memset(r, 0, size);
    
    r->allocated = allocated;
    r->arr = (struct coord_t *) (((char *) r) + sizeof(struct coord_arr_t));
    
    return r;
}

static void
print_coord_arr(struct coord_arr_t * ca) {
    FILE * out = stdout;
    fputs("[", out);
    for (int i = 0; i < ca->size; ++i) {
        struct coord_t * c = ca->arr + i;
        if (i > 0) { fputs(", ", out); }
        fprintf(out, "(%d, %d)", c->h, c->v);
    }
    fputs("]\n", out);
}

static void
find_hollow_coords(struct field_t * f, struct coord_arr_t * out_ca) {
    for (int i = 0; i < f->h; ++i) {
        for (int j = 0; j < f->w; ++j) {
            int c = AT2(f, i, j);
            if (c == 0) {
                struct coord_t * c;
                
                /* add another coord */
                assert(out_ca->size < out_ca->allocated);
                c = out_ca->arr + (out_ca->size++);
                
                c->h = j;
                c->v = i;
            }
        }
    }
}

/*
 
 finder
 
 */

#define FIELD_SIZE (9)

/* size of square within the lines */
#define SQ_SIZE (3)

struct finder_t {
    int verFlags[FIELD_SIZE];
    int horFlags[FIELD_SIZE];
    int sqFlags[FIELD_SIZE / SQ_SIZE][FIELD_SIZE / SQ_SIZE];
    
    struct field_t * field;
    struct coord_arr_t * coordinates;
    bool printSolution;
    
};

static struct finder_t *
alloc_finder(struct field_t * field, struct coord_arr_t * coordinates) {
    int size = sizeof(struct finder_t);
    struct finder_t * r = xmalloc(size);
    memset(r, 0, size);
    
    assert((field->w % SQ_SIZE == 0) && (field->h % SQ_SIZE == 0));
    r->field = field;
    r->coordinates = coordinates;
    
    return r;
}

static void
init_finder(struct finder_t * finder) {
    /* init hor flags */
    for (int i = 0; i < FIELD_SIZE; ++i) {
        int flag = 0;
        for (int j = 0; j < FIELD_SIZE; ++j) {
            int c = AT2(finder->field, i, j);
            if (c == 0) { continue; }
            
            int f = (1 << c);
            if ((flag & f) != 0) {
                fprintf(stderr, "Duplicate hor entry: (%d, %d)=%d", j, i, c);
                abort();
            }
            flag |= f;
        }
        finder->horFlags[i] = flag;
    }
    
    /* init ver flags */
    for (int i = 0; i < FIELD_SIZE; ++i) {
        int flag = 0;
        for (int j = 0; j < FIELD_SIZE; ++j) {
            int c = AT2(finder->field, j, i);
            if (c == 0) { continue; }
            
            int f = (1 << c);
            if ((flag & f) != 0) {
                fprintf(stderr, "Duplicate ver entry: (%d, %d)=%d", i, j, c);
                abort();
            }
            flag |= f;
        }
        finder->verFlags[i] = flag;
    }
    
    /* init sq flags */
    for (int i = 0; i < SQ_SIZE; ++i) {
        for (int j = 0; j < SQ_SIZE; ++j) {
            int vStart = i * SQ_SIZE;
            int hStart = j * SQ_SIZE;
            
            int flag = 0;
            for (int lv = vStart; lv < (vStart + SQ_SIZE); ++lv) {
                for (int lh = hStart; lh < (hStart + SQ_SIZE); ++lh) {
                    int c = AT2(finder->field, lv, lh);
                    if (c == 0) { continue; }
                    
                    int f = (1 << c);
                    if ((flag & f) != 0) {
                        fprintf(stderr, "Duplicate sq entry: (%d, %d)=%d", lh, lv, c);
                        abort();
                    }
                    flag |= f;
                }
            }
            
            finder->sqFlags[i][j] = flag;
        }
    }
} /* END of init_finder */

static void
find_solution(struct finder_t * finder, int pos) {
    if (pos >= finder->coordinates->size) {
        if (finder->printSolution) {
            print_field(finder->field);
        }
        return;
    }
    
    struct coord_t * coord = finder->coordinates->arr + pos;
    assert(AT2(finder->field, coord->v, coord->h) == 0);
    
    /* iterate over the available numbers 1..9 */
    for (int i = 1; i <= 9; ++i) {
        int flag = 1 << i;
        
        /* availability check */
        if ((finder->horFlags[coord->v] & flag) != 0 ||
            (finder->verFlags[coord->h] & flag) != 0 ||
            (finder->sqFlags[coord->v / SQ_SIZE][coord->h / SQ_SIZE] & flag) != 0) {
            continue;
        }
        
        /* set flags and field */
        finder->horFlags[coord->v] |= flag;
        finder->verFlags[coord->h] |= flag;
        finder->sqFlags[coord->v / SQ_SIZE][coord->h / SQ_SIZE] |= flag;
        
        AT2(finder->field, coord->v, coord->h) = i;
        
        /* proceed to the next one */
        find_solution(finder, pos + 1);
        
        /* restore flags and field */
        finder->horFlags[coord->v] &= ~flag;
        finder->verFlags[coord->h] &= ~flag;
        finder->sqFlags[coord->v / SQ_SIZE][coord->h / SQ_SIZE] &= ~flag;
        
        AT2(finder->field, coord->v, coord->h) = 0;
    }
}

/*
 
 app entry point
 
 */

#define RUN_TIMES (16)

int main() {
	puts("Initial state:\n");
	{
        struct field_t * f = alloc_field(FIELD_SIZE, FIELD_SIZE);
        struct coord_arr_t * ca = alloc_coord_arr(f->w * f->h);
        struct finder_t * finder;
        struct timeval start;
        struct timeval stop;
        
        init_field(f);
        print_field(f);
        
        find_hollow_coords(f, ca);
        
        fprintf(stdout, "\ncoord_arr = ");
        print_coord_arr(ca);
        
        finder = alloc_finder(f, ca);
        init_finder(finder);
    
        for (int i = 0; i < RUN_TIMES; ++i) {
            finder->printSolution = (i == 0);
            
            /* find solution */
            gettimeofday(&start, NULL);
            find_solution(finder, 0);
            gettimeofday(&stop, NULL);
            
            /* print the elapsed time */
            long long diff = (stop.tv_usec - start.tv_usec) * 1000L;
            fprintf(stdout, "%d Elapsed time: %lld nanoseconds\n", i, diff);
        }
        
        puts("\n\nEND\n");
        xfree(finder);
        xfree(f);
        xfree(ca);
    }
    
	return 0;
}
