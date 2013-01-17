package org.refsample;

import org.refsample.service.impl.Eagle;

import java.lang.reflect.Field;

public class RefWriteFinalFieldApp {

    public static final class TestIntField {
        int foo;
    }

    private static void setIntField(Object o, String fieldName, int value) throws Exception {
        final Field field = o.getClass().getDeclaredField(fieldName);
        final boolean accessible = field.isAccessible();
        field.setAccessible(true);

        field.setInt(o, value);

        field.setAccessible(accessible);
    }

    public static void main(String[] args) throws Exception {
        final TestIntField t = new TestIntField();
        setIntField(t, "foo", 1324);
        System.out.println("t.foo = " + t.foo);

        final Eagle eagle = new Eagle(1);
        try {
            setIntField(eagle, "index", 134);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // fuck, I was wrong :((((
        System.out.println("eagle.index = " + eagle.getIndex());
    }
}
