package TwoPointer;

import static util.Util.assertTrue;

/**
 * Given two strings s and t, return true if s is a subsequence of t, or false otherwise.
 * <p>
 * A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters.
 * (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
 */
public class LC392E_IsSubsequence {

    // 子串的概念，必须顺序一致，而不是任意字母的打乱组合，所以并不能用map来判断包含关系
    // 这题容易想复杂，循环控制在T，符合条件才移动S的指针，实现是最干净的
    public static boolean isSubsequence(String s, String t) {
        int lengthOfS = s.length(), lengthOfT = t.length();
        if(lengthOfS == 0) {
            return true;
        }
        if(lengthOfS > lengthOfT) {
            return false;
        }
        int indexS = 0, indexT = 0;
        while(indexT < lengthOfT) {
            if(t.charAt(indexT) == s.charAt(indexS)) {
                // s中字符找到，才会移动S的指针
                ++indexS;
                if(indexS == lengthOfS) {
                    // s的最后一个字符找到
                    return true;
                }
            }

            ++ indexT;
        }

        // t都遍历完了，s还没有走到头，说明没有解
        return false;
    }

    public static void main(String[] a){

        String s = "abc", t = "ahbgdc";
        assertTrue(isSubsequence(s, t));

        String s2 = "aaaaaa", t2 = "bbaaaa";
        assertTrue(!isSubsequence(s2, t2));

        String s3 = "t", t3 = "c";
        assertTrue(!isSubsequence(s3, t3));
    }
}
