package Stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

import static util.Util.assertEquals;

// 逆波兰表示法
public class LC150M_EvaluateReversePolishNotation {

    public static int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        Set<String> operatorSet = Set.of("+", "-", "*", "/");

        for(String token : tokens) {
            if(operatorSet.contains(token)) {
                // 不判断stack的size，默认输入合法，如果非法，则会抛异常
                Integer right = stack.pop(), left = stack.pop();
                Integer currRes = evaluate(token, left, right);
                stack.push(currRes);
            } else {
                stack.push(Integer.valueOf(token));
            }
        }
        // 最后栈里保留的是结果
        return stack.pop();
    }

    static Integer evaluate(String token, Integer left, Integer right) {
        switch (token) {
            case "+" -> {
                return left + right;
            }
            case "-" -> {
                return left - right;
            }
            case "*" -> {
                return left * right;
            }
            case "/" -> {
                return left / right;
            }
            default -> {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        String[] tokens = {"2","1","+","3","*"};
        assertEquals(9, evalRPN(tokens));
    }
}
