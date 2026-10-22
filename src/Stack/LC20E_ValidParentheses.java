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
            // 剪枝优化：奇数长度直接返回 false
            return false;
        }
        Deque<Character> stack = new ArrayDeque<>();
        for(char letter : s.toCharArray()) {
            if(letter == '(') {
                stack.push(')');
            } else if(letter == '{') {
                stack.push('}');
            } else if(letter == '[') {
                stack.push(']');
            } else {
                if(stack.isEmpty() || stack.pop() != letter){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String s1 = "([])";
        assertTrue(isValid(s1));
    }
}
