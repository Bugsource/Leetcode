package HashMap;

import java.util.*;

/**
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * <p>
 * 示例 1:
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * <p>
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * <p>
 * 解释：
 * 在 strs 中没有字符串可以通过重新排列来形成 "bat"。
 * 字符串 "nat" 和 "tan" 是字母异位词，因为它们可以重新排列以形成彼此。
 * 字符串 "ate" ，"eat" 和 "tea" 是字母异位词，因为它们可以重新排列以形成彼此。
 * <p>
 * 示例 2:
 * 输入: strs = [""]
 * 输出: [[""]]
 */
public class LC49M_GroupAnagrams {

    public List<List<String>> groupAnagrams(String[] strs) {
        // key是一组异位词的唯一键
        Map<String, List<String>> res = new HashMap<>();
        for(String str: strs) {
            // 关键在于，如何用O(n)复杂度获取key；
            // 次一点的做法，每次都对所有字符排序，唯一确定一个key，复杂度是nlogn；
            // 更好的做法，一次遍历就确定唯一key，无需排序
            String key = getIndexedArrayKey(str);

            res.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        return new ArrayList<>(res.values());
    }

    private static String getIndexedArrayKey(String str) {
        int[] map = new int[26];
        for(char c : str.toCharArray()) {
            ++map[c - 'a'];
        }
        return Arrays.toString(map);
    }
}
