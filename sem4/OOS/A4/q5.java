import java.lang.reflect.*;



public class ReflectionDemo {
    public static void main(String[] args) {
        try {
            // Create instance and get Class object using getClass()
            SampleClass sample = new SampleClass();
            Class<?> clazz = sample.getClass();
            
            // Get all public methods (including inherited ones)
            System.out.println("\nPublic Methods:");
            for (Method method : clazz.getMethods()) {
                System.out.println("- " + method.getName());
            }

            // Get all public constructors
            System.out.println("\nPublic Constructors:");
            for (Constructor<?> constructor : clazz.getConstructors()) {
                System.out.println("- " + constructor);
            }

            // Access private method using getDeclaredMethod()
            Method privateMethod = clazz.getDeclaredMethod("privateMethod", String.class);
            System.out.println("\nAccessed private method: " + privateMethod.getName());
            
            // Bypass access check using setAccessible()
            privateMethod.setAccessible(true);
            privateMethod.invoke(sample, "secret parameter");

            // Access private field using getDeclaredField()
            Field privateField = clazz.getDeclaredField("privateField");
            System.out.println("\nAccessed private field: " + privateField.getName());
            
            // Modify private field value
            privateField.setAccessible(true);
            System.out.println("Original field value: " + privateField.get(sample));
            privateField.set(sample, "modified value");
            System.out.println("Updated field value: " + privateField.get(sample));

            // Demonstrate getDeclaredConstructors() vs getConstructors()
            System.out.println("\nAll Declared Constructors:");
            for (Constructor<?> ctor : clazz.getDeclaredConstructors()) {
                System.out.println("- " + ctor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class SampleClass {
    private String privateField;

    public SampleClass() {
        privateField = "initial value";
    }

    private SampleClass(String value) {
        privateField = value;
    }

    public void publicMethod() {
        System.out.println("Public method called");
    }

    private void privateMethod(String param) {
        System.out.println("Private method called with: " + param);
        System.out.println("Current field value: " + privateField);
    }
}
