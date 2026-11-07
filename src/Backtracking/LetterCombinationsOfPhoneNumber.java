package Backtracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LetterCombinationsOfPhoneNumber {

    // 优化1: 使用 String 代替 List<Character>，更紧凑
    private static final Map<Character, String> NUMBER_TO_LETTERS = Map.of(
            '2', "abc", '3', "def", '4', "ghi", '5', "jkl",
            '6', "mno", '7', "pqrs", '8', "tuv", '9', "wxyz"
    );

    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return res;
        }
        // 优化2: 递归函数重命名为 backtrack，语义更清晰
        backtrack(digits, 0, new StringBuilder(), res);
        return res;
    }

    /**
     * 回溯的本质是‌状态恢复‌。
     * <p>
     * 在使用 StringBuilder 这种‌可变对象‌（引用传递）时，所有递归层级共享同一个内存对象。当前层修改了它（append），
     * 为了不影响兄弟分支或上一层的其他选择，必须在递归返回后撤销修改（delete），将对象恢复到进入当前层之前的状态。
     * <p>
     * 若使用 String 等‌不可变对象‌（值传递），每次拼接都会生成新的对象副本，各层级互不干扰，自然无需手动“撤销”，但这会以增加内存分配和 GC 压力为代价。
     * <p>
     * 简言之：‌回溯是为了复用同一块内存空间，通过“现场保护与恢复”来避免频繁创建新对象。
     */
    private void backtrack(String digits, int index, StringBuilder currRes, List<String> res) {
        // 终止条件：当当前构建的字符串长度等于数字串长度时，收集结果
        if (index == digits.length()) {
            res.add(currRes.toString());
            return;
        }

        char num = digits.charAt(index);
        String letters = NUMBER_TO_LETTERS.get(num);
        // 空指针保护（虽然题目保证输入合法，但健壮的代码应考虑）
        if (letters == null) {
            return;
        }

        for (char letter : letters.toCharArray()) {
            currRes.append(letter);
            // 递归下一层
            backtrack(digits, index + 1, currRes, res);
            // 回溯：删除最后一个字符
            // 优化3: 使用 deleteCharAt(length - 1) 更通用
            currRes.deleteCharAt(currRes.length() - 1);
        }
    }
}
