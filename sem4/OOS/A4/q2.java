import java.util.HashMap;
import java.util.Map;

public class FrequencyCounter {
    public static <T> Map<T, Integer> findFrequency(T[] array) {
        Map<T, Integer> frequencyMap = new HashMap<>();
        for (T element : array) {
            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
        }
        return frequencyMap;
    }

    public static void main(String[] args) {
        // Test with Integer array
        Integer[] intArray = {1, 2, 3, 1, 2, 1, 4};
        System.out.println("Frequency of Integer array: " + findFrequency(intArray));

        // Test with String array
        String[] strArray = {"apple", "banana", "apple", "orange", "banana"};
        System.out.println("Frequency of String array: " + findFrequency(strArray));

        // Test with Character array
        Character[] charArray = {'a', 'b', 'a', 'c', 'b', 'a'};
        System.out.println("Frequency of Character array: " + findFrequency(charArray));
    }
