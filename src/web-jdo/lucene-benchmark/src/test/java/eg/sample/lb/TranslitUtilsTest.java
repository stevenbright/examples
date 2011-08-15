package eg.sample.lb;


import org.junit.Test;

import static eg.sample.lb.utils.TranslitUtils.transliterateString;
import static org.junit.Assert.assertEquals;

public class TranslitUtilsTest {
    @Test
    public void testTranslit() {
        assertEquals("HELLO", transliterateString("hello"));
        assertEquals("PRIVET!", transliterateString("\u041f\u0440\u0438\u0432\u0435\u0442\u0021"));
    }
}
