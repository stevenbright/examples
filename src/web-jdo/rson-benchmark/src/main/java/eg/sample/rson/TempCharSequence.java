package eg.sample.rson;


public class TempCharSequence implements CharSequence {
    public final char[] buffer = new char[512];

    public int length() {
        return buffer.length;
    }

    public char charAt(int index) {
        return buffer[index];
    }

    public CharSequence subSequence(int start, int end) {
        return new String(buffer, start, end);
    }
}
