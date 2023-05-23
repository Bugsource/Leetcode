package TwoPointer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ReverseWords {


    public static String reverse(String input) {
        String[] splittedStrs = input.trim().split(" +");
        Collections.reverse(Arrays.asList(splittedStrs));
        return String.join(" ", splittedStrs);
    }

    public static void main(String[] a){
        System.out.println("input1 res:" + reverse("the sky is blue"));
        System.out.println("input2 res:" + reverse("  hello world  "));

    }
}
