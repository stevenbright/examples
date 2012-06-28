
import java.util.Arrays;

public final class QuickSortApp {

    private static void partialRangeSort(Integer[] arr, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return;
        }

        int l = leftIndex;
        int r = rightIndex;
        Integer x = arr[(l + r) / 2];

        do {
            while (arr[l] < x) {
                ++l;
            }

            while (arr[r] > x) {
                --r;
            }

            if (l < r) {
                // swap elements
                Integer tmp = arr[l];
                arr[l] = arr[r];
                arr[r] = tmp;
                System.out.println("\t[step] " + Arrays.asList(arr));
            }
        } while (l < r);


        partialRangeSort(arr, leftIndex, r - 1);
        partialRangeSort(arr, l + 1, rightIndex);
    }

    public static void inplaceQuickSort(Integer[] arr) {
        partialRangeSort(arr, 0, arr.length - 1);
    }


    private static void sortAndPrint(String name, Integer... numbers) {
        System.out.println("Unsorted " + name + ": " + Arrays.asList(numbers));
        inplaceQuickSort(numbers);
    }

    public static void main(String[] args) {
        sortAndPrint("back_order", 9, 8, 7, 6, 5, 4, 3, 2, 1);
        sortAndPrint("natural_order", 1, 2, 3, 4, 5);
        sortAndPrint("mixed_order", 1, 5, 4, 3, 2);
    }
}

