package Stack;

import java.util.ArrayDeque;
import java.util.Deque;

import static util.Util.assertTrue;

/**
 * 判断括号有效性。有小括号，中括号，大括号三种
 * <p>
 * Example 4:  Input: s = "([])"
 * <p>
 * Output: true
  */

public class LC20E_ValidParentheses {

    public static boolean isValid(String s) {
        if(s.length() % 2 != 0) {
            // 优化：奇数长度直接返回 false
            return false;
        }
        Deque<Character> stack = new ArrayDeque<>();
        for(int i = 0; i < s.length(); ++ i) {
            char letter = s.charAt(i);
            if(letter == ')') {
                if(!check(stack, '(')) {
                    return false;
                }
            } else if(letter == '}') {
                if(!check(stack, '{')) {
                    return false;
                }
            } else if(letter == ']') {
                if(!check(stack, '[')) {
                    return false;
                }
            } else {
                stack.push(letter);
            }
        }
        return stack.isEmpty();
    }

    private static Boolean check(Deque<Character> stack, Character validOpen) {
        if(!stack.isEmpty()) {
            Character peek = stack.pop();
            return peek.equals(validOpen) ;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {

        String s1 = "([])";
        assertTrue(isValid(s1));
    }
}
