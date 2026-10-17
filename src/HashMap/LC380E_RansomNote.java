package HashMap;

/**
 * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
 * 如果可以，返回 true ；否则返回 false 。
 * <p>
 * magazine 中的每个字符只能在 ransomNote 中使用一次。
 * <p>
 * 示例 1：
 * 输入：ransomNote = "a", magazine = "b"
 * 输出：false
 * <p>
 * 示例 2：
 * 输入：ransomNote = "aa", magazine = "ab"
 * 输出：false
 * <p>
 * 示例 3：
 * 输入：ransomNote = "aa", magazine = "aab"
 * 输出：true
 */
public class LC380E_RansomNote {
    // 最简单的窗口计数
    public boolean canConstruct(String ransomNote, String magazine) {
        if(ransomNote.length() > magazine.length()){
            return false;
        }
        int[] letterToCountInMagazine = new int[256];
        for(char m : magazine.toCharArray()) {
            ++ letterToCountInMagazine[m];
        }

        for(char r : ransomNote.toCharArray()) {
            if(letterToCountInMagazine[r] == 0) {
                return false;
            }
            -- letterToCountInMagazine[r];
        }
        return true;
    }
}
