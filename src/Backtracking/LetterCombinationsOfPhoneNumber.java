package Backtracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LetterCombinationsOfPhoneNumber {

    private final static Map<Character, List<Character>> numberToLettersMap = Map.of(
            '2', List.of('a', 'b', 'c'),
            '3', List.of('d', 'e', 'f'),
            '4', List.of('g', 'h', 'i'),
            '5', List.of('j', 'k', 'l'),
            '6', List.of('m', 'n', 'o'),
            '7', List.of('p', 'q', 'r', 's'),
            '8', List.of('t', 'u', 'v'),
            '9', List.of('w', 'x', 'y', 'z')
    );

    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        int length = digits.length();
        if(length == 0) {
            return res;
        }
        letterCombinations(digits, 0, length, new StringBuilder(), res);
        return res;
    }
    public void letterCombinations(String digits, int index, int length, StringBuilder currRes, List<String> res) {
        if(index >= length) {
            // toString会new一个新对象，因此是深拷贝
            res.add(currRes.toString());
            return;
        }
        Character num = digits.charAt(index);
        List<Character> letters = numberToLettersMap.get(num);
        for(Character letter : letters) {
            currRes.append(letter);
            // dfs
            letterCombinations(digits, index+1, length, currRes, res);
            // 删除当前结果，准备遍历下一个候选元素
            currRes.deleteCharAt(index);
        }
    }
}
