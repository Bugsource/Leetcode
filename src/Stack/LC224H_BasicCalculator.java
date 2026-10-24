package Stack;

import java.util.ArrayDeque;

import static util.Util.assertEquals;
import static util.Util.assertTrue;

/**
 * Example 3:
 * <p>
 * Input: s = "(1+(4+5+2)-3)+(6+8)"
 * Output: 2
 */
public class LC224H_BasicCalculator {

    // 这题看起来只用了栈，但是逻辑很复杂，主要是状态何时要复位，当前该计算的是什么，细节很多
    public static int calculate(String s) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int res = 0;      // 当前层级的计算结果
        int num = 0;      // 当前正在解析的数字
        int sign = 1;     // 当前数字的符号，1为正，-1为负

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                // 构建多位数
                num = num * 10 + (c - '0');
            } else if (c == '+') {
                // 结算前一个数字：结果 += 符号 * 数字
                res += sign * num;
                // 重置状态，准备接收下一个数字
                num = 0;
                sign = 1;
            } else if (c == '-') {
                // 结算前一个数字
                res += sign * num;
                // 重置状态
                num = 0;
                sign = -1;
            } else if (c == '(') {
                // 【关键】进入子表达式，保存当前现场
                stack.push(res);   // 保存括号前的累计结果
                stack.push(sign);  // 保存括号前的符号

                // 重置状态，在括号内从头计算
                res = 0;
                sign = 1;
                num = 0; // 括号内第一个数字前没有隐含数字，num归零更安全
            } else if (c == ')') {
                // 1. 结算括号内最后一个数字
                res += sign * num;

                // 2. 恢复现场
                // 注意出栈顺序：先出 sign，再出之前的 res
                int prevSign = stack.pop();
                int prevRes = stack.pop();

                // 3. 合并：括号外的结果 + (括号外的符号 * 括号内的结果)
                res = prevRes + prevSign * res;

                // 4. 重置 num，因为括号整体被视为一个数字，后续遇到 +/- 会重新累加
                num = 0;
            }
        }

        // 【易漏点】循环结束后，最后一个数字可能还没结算
        res += sign * num;

        return res;
    }

    public static void main(String[] args) {
        String s1 = "(1+(4+5+2)-3)+(6+8)";
        assertEquals(23, calculate(s1));

        String s2 = "- (3 + (4 + 5))";
        assertEquals(-12, calculate(s2));
    }
}
