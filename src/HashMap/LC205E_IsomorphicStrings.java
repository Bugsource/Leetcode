package HashMap;

/**
 * 给定两个字符串 s 和 t ，判断它们是否是同构的。
 * <p>
 * 如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
 * <p>
 * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "egg", t = "add"
 * 输出：true
 * <p>
 * 解释：字符串 s 和 t 可以通过以下方式变得相同：
 * <p>
 * 将 'e' 映射为 'a'。
 * 将 'g' 映射为 'd'。
 * <p>
 * 示例 2：
 * <p>
 * 输入：s = "f11", t = "b23"
 * 输出：false
 * <p>
 * 解释：字符串 s 和 t 无法变得相同，因为 '1' 需要同时映射到 '2' 和 '3'。
 * <p>
 * 示例 3：
 * <p>
 * 输入：s = "paper", t = "title"
 * 输出：true
 */
public class LC205E_IsomorphicStrings {

    /**
     * 如果两个字符串是同构的，那么对于任意位置i，字符 s[i] 在 s 中‌上一次出现的位置‌，必须与字符 t[i] 在 t 中‌上一次出现的位置‌完全一致。
     * <p>
     * 这隐式地完成了双向的检查，避开了维护 Key->Value 映射关系的复杂性，转而维护 Char->LastIndex 的状态。
     * <p>
     * 核心在于‌将“字符映射关系”转化为“字符出现模式的比对”‌。
     */
    public boolean isIsomorphic(String s, String t) {
        if(s.length() != t.length()){
            return false;
        }
        // 假设字符是 ASCII 码（0-255）
        int[] map1 = new int[256], map2 = new int[256];

        for(int i = 0; i < s.length(); ++ i) {
            char letter1 = s.charAt(i), letter2 = t.charAt(i);
            if(map1[letter1] != map2[letter2]) {
                // 如果两个字母都是第一次出现，那应该值都是0，应该相等；
                // 如果有不是第一次出现，那么值存的是上一次出现的下标+1，二者也应该相等；
                return false;
            }
            // 更新最近一次出现的位置; 值为0，代表从未出现过，为了区分从未出现和在首位出现，所以不直接用i
            map1[letter1] = i + 1;
            map2[letter2] = i + 1;
        }
        return true;
    }
}
