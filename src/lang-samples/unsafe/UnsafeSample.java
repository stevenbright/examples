import sun.misc.Unsafe;

import java.awt.Point;
import java.lang.reflect.Field;

public class UnsafeSample {

    public static void main(String[] args) throws Exception {
        final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        final Unsafe u = (Unsafe) unsafeField.get(null);
        final Point point = new Point(1, 2);

        final long xOffset = u.objectFieldOffset(point.getClass().getField("x"));
        final long yOffset = u.objectFieldOffset(point.getClass().getField("y"));
        final long totalSize = Math.max(xOffset, yOffset) + 8 /*sizeof long*/;

        System.out.println("point size = " + totalSize);
    }
}

