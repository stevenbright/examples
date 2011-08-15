package eg.sample.lb.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Transliteration utilities.
 * Implemented for Cyrillic.
 */
public final class TranslitUtils {

    private static final Map<Character, String> TRANSLITERATED_CHAR_MAP = new HashMap<Character, String>();

    private static final int ESTIMATED_OVERLOAD = 8;

    static {
        final char[] CYRILLIC_UPPERCASE_CHARS = new char[] {
                '\u0410', '\u0411', '\u0412', '\u0413', '\u0414', '\u0415', '\u0401', '\u0416',
                '\u0417', '\u0418', '\u0419', '\u041a', '\u041b', '\u041c', '\u041d', '\u041e',
                '\u041f', '\u0420', '\u0421', '\u0422', '\u0423', '\u0424', '\u0425', '\u0426',
                '\u0427', '\u0428', '\u0429', '\u042a', '\u042b', '\u042c', '\u042d', '\u042e',
                '\u042f'
        };

        final String[] CYRILLIC_UPPERCASE_TRANS = new String[] {
                "A", "B", "V", "G", "D", "E", "YO", "ZH",
                "Z", "I", "I", "K", "L", "M", "N", "O",
                "P", "R", "S", "T", "U", "F", "H", "C",
                "CH", "SH", "SCH", "\'", "Y", "\'", "E", "U",
                "YA"
        };

        assert CYRILLIC_UPPERCASE_CHARS.length == CYRILLIC_UPPERCASE_TRANS.length;

        for (int i = 0; i < CYRILLIC_UPPERCASE_CHARS.length; ++i) {
            TRANSLITERATED_CHAR_MAP.put(CYRILLIC_UPPERCASE_CHARS[i], CYRILLIC_UPPERCASE_TRANS[i]);
        }
    }


    /**
     * Transliterates the given source string
     * @param sourceString Source string
     * @return Transliterated string part
     */
    public static String transliterateString(String sourceString) {
        final StringBuilder stringBuilder = new StringBuilder(sourceString.length() + ESTIMATED_OVERLOAD);

        for (int i = 0; i < sourceString.length(); ++i) {
            final char ch = Character.toUpperCase(sourceString.charAt(i));
            if (ch < 128) {
                stringBuilder.append(ch);
            } else {
                // transliterate
                final String transliteratedChar = TRANSLITERATED_CHAR_MAP.get(ch);
                if (transliteratedChar == null) {
                    stringBuilder.append(".");
                } else {
                    stringBuilder.append(transliteratedChar);
                }
            }
        }

        return stringBuilder.toString();
    }

    /** Hidden ctor */
    private TranslitUtils() {}
}
