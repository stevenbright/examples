
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static void test_read_str(const char * content) {
    size_t len = strlen(content);
    size_t i;
    FILE * f = tmpfile();
    if (!f) {
        fprintf(stderr, "Can't open tmp file");
        exit(EXIT_FAILURE);
    }

    fwrite(content, 1, len, f);
    fseek(f, 0, SEEK_SET);

    for (i = 0; i < len; ++i) {
        int ch = fgetc(f);
        if (ch != content[i]) {
            fprintf(stderr, "FAIL: Mismatch for content[%d] = %c, actual = %c (%d)\n", content[i], (char) ch, ch);
            exit(EXIT_FAILURE);
        }
    }

    if (fgetc(f) != EOF) {
        fprintf(stderr, "FAIL: Non-EOF character at the end of stream.\n");
        exit(EXIT_FAILURE);
    }

    fprintf(stdout, "OK: %s\n", content);

    fclose(f);
}

int main() {
    test_read_str("");
    test_read_str("1");
    test_read_str("abc");
    test_read_str("aa b c dd");
    return 0;
}

