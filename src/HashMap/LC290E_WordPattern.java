package HashMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static util.Util.assertTrue;

/**
 * 给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。
 * <p>
 * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的每个非空单词之间存在着双射。具体来说：
 * <p>
 * - pattern 中的每个字母都 恰好 映射到 s 中的一个唯一单词。
 * - s 中的每个唯一单词都 恰好 映射到 pattern 中的一个字母。
 * - 没有两个字母映射到同一个单词，也没有两个单词映射到同一个字母。
 * <p>
 * 示例1:
 * <p>
 * 输入: pattern = "abba", s = "dog cat cat dog"
 * 输出: true
 * 示例 2:
 * <p>
 * 输入:pattern = "abba", s = "dog cat cat fish"
 * 输出: false
 * 示例 3:
 * <p>
 * 输入: pattern = "aaaa", s = "dog cat cat dog"
 * 输出: false
 */
public class LC290E_WordPattern {
    public static boolean wordPattern(String pattern, String s) {
        // split(" ")：仅按单个空格切分，连续空格会产生空字符串 ""。
        // split("\\s+")：按任意空白字符（空格、Tab、换行等）切分，并自动合并连续空白，结果更干净。
        String[] words = s.split("\\s+");
        int length = pattern.length();
        if(length != words.length) {
            return false;
        }
        char[] letters = pattern.toCharArray();
        Map<Character, String> letterToString = new HashMap<>();
        Set<String> wordsSet = new HashSet<>();
        for(int i = 0; i < length; ++i){
            char currLetter = letters[i];
            String currWord = words[i];
            String stringTryGet = letterToString.get(currLetter);
            if(stringTryGet != null) {
                // map中存在这个字母，则当前下标的单词，必须和原来保存的相等; 否则意味着一个字母映射到两个单词
                if(!stringTryGet.equals(currWord)){
                    return false;
                }
            } else {
                letterToString.put(currLetter, currWord);
                boolean firstAdded = wordsSet.add(currWord);
                if(!firstAdded) {
                    // 如果字母第一次出现，而单词已经出现过了，那就说明两个字母映射到同一个单词
                    return false;
                }
            }
        }
        return true;
    }

    // 网上看来的一个Map的版本；简洁但是稍显晦涩难懂。还有两个Map的实现，不如我一个Map + Set

    public boolean wordPattern2(String pattern, String str) {
        String[] words = str.split(" ");
        //字符和单词是互相映射，数量必须相等
        if (words.length != pattern.length()) {
            return false;
        }
        Map<Object, Integer> map = new HashMap<>();
        for (Integer i = 0; i < words.length; i++) {
        /*
            如果key不存在，插入成功，返回null；如果key存在，返回之前对应的value。

            以pattern = "abba", str = "dog cat cat dog"为例，
            第1次：map.put('a',0)返回null，map.put("dog",0)返回null，两者相等；
            第2次：map.put('b',1)返回null，map.put("cat",1)返回null，两者相等；
            第3次：map.put('b',2)返回1，map.put("cat",2)返回1，两者相等；
            第4次：map.put('a',3)返回0，map.put("dog",3)返回0，两者相等，
            结果为 true。

            以pattern = "abba", str = "dog cat cat fish"为例，
            第1次：map.put('a',0)返回null，map.put("dog",0)返回null，两者相等；
            第2次：map.put('b',1)返回null，map.put("cat",1)返回null，两者相等；
            第3次：map.put('b',2)返回1，map.put("cat",2)返回1，两者相等；
            第4次：map.put('a',3)返回0，map.put("fish",3)返回null，两者不相等，
            结果为 false。
        */
            if (map.put(pattern.charAt(i), i) != map.put(words[i], i)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] a){

        String pattern = "abba", s = "dog dog dog dog";
        assertTrue(!wordPattern(pattern, s));

        String pattern2 = "abba", s2 = "dog cat cat dog";
        assertTrue(!wordPattern(pattern2, s2));
    }
}
