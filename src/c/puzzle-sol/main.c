
#include <stdio.h>
#include <string.h>

static int g_field[9][9]  = { 0 };

static void init_field() {
	/* 0 line */
	g_field[0][0] = 2;
	g_field[2][0] = 4;
	g_field[4][0] = 7;
	g_field[6][0] = 9;
	g_field[8][0] = 1;

	/* 1 line */

	/* 2 line */
        g_field[1][2] = 5;
        g_field[2][2] = 8;
        g_field[4][2] = 3;
        g_field[6][2] = 7;
        g_field[7][2] = 4;

	/* 3 line */
        g_field[3][3] = 9;
        g_field[5][3] = 7;

	/* 4 line */
        g_field[1][4] = 9;
        g_field[2][4] = 6;
        g_field[6][4] = 8;
        g_field[7][4] = 2;

	/* 5 line */
        g_field[3][5] = 5;
        g_field[5][5] = 2;

	/* 6 line */
        g_field[1][6] = 3;
        g_field[2][6] = 5;
        g_field[4][6] = 1;
        g_field[6][6] = 6;
        g_field[7][6] = 8;
	
	/* 7 line */

	/* 8 line */
        g_field[0][8] = 4;
        g_field[2][8] = 1;
        g_field[4][8] = 5;
        g_field[6][8] = 2;
        g_field[8][8] = 3;
}

static void print_field() {
	for (int i = 0; i < 9; ++i) {
		printf("\n");
		for (int j = 0; j < 9; ++j) {
			int c = g_field[j][i];
			if (c == 0) {
				printf(" .");
			} else {
				printf(" %d", c);
			}
		}
	}
}

struct coordinate {
	int h;
	int v;
};

static struct coordinate g_coords[81] = { 0 }
static int g_coords_size = 0;


static void prepare_field() {
	for (int i = 0; i < 9; ++i) {
		/* init lines */
		for (int j = 0; j < 9; ++j) {
			int u = j + 1;
			g_avail_hor[u][i] = g_avail_ver[u][i] = u;
		}

		/* init frozen cells */
		for (int j = 0; j < 9; ++j) {
			int c = g_field[j][i];
			if (c != 0) {
				g_frozen[j][i] = 1;

				g_avail_hor[c + 1][i] = 0;
				//memcpy(&g_avail_hor[c + 2][i], &g_avail_hor[c + 1][i], 9 - c + 1);
			}
		}
	}
}

int main() {
	puts("Initial state:\n");
	init_field();
	print_field();

	/*prepare_field();*/

	return 0;
}

