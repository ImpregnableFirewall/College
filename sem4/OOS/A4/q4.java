import java.util.HashSet; import java.util.Set;

public class DuplicateFinder {

// Generic method to find and print duplicate elements in an array
public static <T> void printDuplicates(T[] array) {
Set<T> seen = new HashSet<>();	// Set to store elements we've already encountered
Set<T> duplicates = new HashSet<>(); // Set to store duplicate elements

// Loop through the array to find duplicates
for (T element : array) { if (!seen.add(element)) {
// If element is already in 'seen', it's a duplicate
duplicates.add(element);
}
}

// Print the duplicates
if (duplicates.isEmpty()) { System.out.println("No duplicates found.");
} else {
System.out.println("Duplicate elements: "); for (T duplicate : duplicates) {
System.out.println(duplicate);
 
}
}
}

// Main method to test the generic method
public static void main(String[] args) {
// Test with Integer array
Integer[] intArray = { 1, 2, 3, 2, 4, 5, 3 }; System.out.println("Duplicates in Integer array:"); printDuplicates(intArray);

// Test with String array
String[] strArray = { "Apple", "Banana", "Apple", "Orange", "Banana" }; System.out.println("\nDuplicates in String array:"); printDuplicates(strArray);
}
}
