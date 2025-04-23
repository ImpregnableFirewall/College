import java.util.Arrays;

public class GenericSorter {
    public static <T extends Comparable<T>> void sortArray(T[] array) {
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    // Swap elements
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void main(String[] args) {
        // Test with Integer array
        Integer[] intArray = {5, 2, 9, 1, 5};
        System.out.println("Original Integer array: " + Arrays.toString(intArray));
        sortArray(intArray);
        System.out.println("Sorted Integer array: " + Arrays.toString(intArray));

        // Test with String array
        String[] strArray = {"banana", "apple", "pear", "orange"};
        System.out.println("\nOriginal String array: " + Arrays.toString(strArray));
        sortArray(strArray);
        System.out.println("Sorted String array: " + Arrays.toString(strArray));

        // Test with Double array
        Double[] doubleArray = {3.4, 1.2, 6.7, 2.1};
        System.out.println("\nOriginal Double array: " + Arrays.toString(doubleArray));
        sortArray(doubleArray);
        System.out.println("Sorted Double array: " + Arrays.toString(doubleArray));
    }
}
