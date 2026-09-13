package util;

public class Util {
    public static void assertEquals(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new RuntimeException(String.format("Assertion Failed: Expected [%s], but got [%s]", expected, actual));
        }
    }

    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Assertion Failed: Condition is not true");
        }
    }

    public static void assertNotNull(Object obj) {
        if (obj == null) {
            throw new RuntimeException("Assertion Failed: Object is null");
        }
    }
}
