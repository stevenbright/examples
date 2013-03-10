import java.util.*;


public final class HeapDemoApp {
    final Random random = new Random(32);

    void run() {
        // fill arr
        final int size = 15;
        final IntHeap heap = new IntHeap();
        final Set<Long> set = new LinkedHashSet<Long>(size);

        for (int i = 0; i < size; ++i) {
            long element;
            do {
                element = random.nextInt(100);
            } while (heap.contains(element));

            System.out.println("Adding " + element);
            heap.add(element);
            System.out.println("Heap array: " + heap.asList());
            heap.validate();

            set.add(element);
        }

        System.out.println("List of elements in order of appearance:\n\t" + set);

        // TODO: fix removal

        System.out.println("---\nRoot removal:\n");
        while (!heap.isEmpty()) {
            final long element = heap.removeRoot();
            System.out.println("Heap array: " + heap.asList());
            heap.validate();

            set.remove(set.iterator().next());
            if (!set.containsAll(heap.asList())) {
                set.removeAll(heap.asList());
                throw new AssertionError("Element " + element + " is not properly deleted, diff: " + set);
            }
        }
    }

    public static void main(String[] args) {
        new HeapDemoApp().run();
    }
}

/**
 * Represents simple heap of longs, no eager allocations (preallocations) are used for simplicity sake.
 */
final class IntHeap {
    private long[] arr;

    public IntHeap() {
        arr = new long[0];
    }

    public void add(long element) {
        long[] newArr = new long[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);

        int last = arr.length;
        arr = newArr;
        arr[last] = element;

        for (;;) {
            int i = swapWithParentIfNeeded(last);
            if (i == last) {
                return;
            }

            last = i;
        }
    }

    public int size() {
        return arr.length;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public long removeRoot() {
        if (arr.length == 0) {
            throw new IllegalStateException("Too few elements in array");
        }

        int last = 0;
        final long element = arr[last];

        // copy last one
        arr[last] = arr[arr.length - 1];

        // copy array
        final long[] newArr = new long[arr.length - 1];
        System.arraycopy(arr, 1, newArr, 0, arr.length - 1);
        arr = newArr;

        // rebuild structure
        for (;;) {
            int i = swapWithChild(last);
            System.out.println("swap " + i + ", " + last);
            if (i == last) {
                break;
            }

            last = i;
        }

        return element;
    }

    public boolean contains(long element) {
        for (long e : arr) {
            if (e == element) {
                return true;
            }
        }
        return false;
    }

    public List<Long> asList() {
        final List<Long> list = new ArrayList<Long>(arr.length);
        for (final long element : arr) {
            list.add(element);
        }
        return Collections.unmodifiableList(list);
    }

    public void validate() {
        for (int i = 0; i < arr.length; ++i) {
            long element = arr[i];
            final int left = getIndexOfLeftChild(i);
            if (left < arr.length && element < arr[left]) {
                throw new AssertionError("Illegal left element arr[" + left + "] = " + arr[left] +
                        " with respect to arr[" + i + "] = " + element);
            }

            final int right = getIndexOfRightChild(i);
            if (right < arr.length && element < arr[right]) {
                throw new AssertionError("Illegal right element arr[" + right + "] = " + arr[right] +
                        " with respect to arr[" + i + "] = " + element);
            }
        }

        // OK!
    }

    //
    // private
    //

    private int swapWithParentIfNeeded(int index) {
        if (index < 1) {
            return index;
        }
        int parentIndex = getIndexOfParent(index);
        final long element = arr[index];
        final long parentElement = arr[parentIndex];
        if (element > parentElement) {
            // swap
            arr[index] = parentElement;
            arr[parentIndex] = element;
            return parentIndex;
        }

        return index;
    }

    private int swapWithChild(int index) {
        final int left = getIndexOfLeftChild(index);
        final int right = getIndexOfRightChild(index);
        int largest = index;

        if (right < arr.length) {
            if (arr[left] > arr[right]) {
                largest = right;
            } else {
                largest = left;
            }

            if (arr[index] > arr[largest]) {
                largest = index;
            }
        } else if (left < arr.length) {
            if (arr[index] < arr[left]) {
                largest = left;
            }
        }

        if (arr[index] < arr[largest]) {
            final long tmp = arr[index];
            arr[index] = arr[largest];
            arr[largest] = tmp;
        }

        return largest;
    }

    private static int getIndexOfParent(int index) {
        assert index > 0;
        return (index - 1) / 2;
    }

    private static int getIndexOfLeftChild(int index) {
        return index * 2 + 1;
    }

    private static int getIndexOfRightChild(int index) {
        return index * 2 + 2;
    }
}
