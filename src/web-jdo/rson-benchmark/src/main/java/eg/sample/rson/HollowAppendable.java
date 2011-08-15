package eg.sample.rson;

import java.io.IOException;

/**
 *
 */
public final class HollowAppendable implements Appendable {
    public Appendable append(CharSequence csq) throws IOException {
        return this;
    }

    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        return this;
    }

    public Appendable append(char c) throws IOException {
        return this;
    }
}
