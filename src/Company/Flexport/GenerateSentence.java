package Company.Flexport;


/*
 * Click `Run` to execute the snippet below!
 this is a sentence it is a good one and it is also bad. 1point 3 acres
 5

 is not a sentence it
 */

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GenerateSentence {
    public String getNewWords(String words, int n){
        if(words==null){
            return "";
        }
        StringBuilder ans = new StringBuilder();
        String[] wordsString = words.split(" ");
        Map<String, Set<String>> wordsMap = new HashMap<>();
        int length = wordsString.length;
        // 构建map
        for(int i=0;i<length-1;i++){
            String tmpKey = wordsString[i];
            String tmpValue = wordsString[i+1];
            if(!wordsMap.containsKey(tmpKey)){
                Set<String> wordSet = new HashSet<>();
                wordSet.add(tmpValue);
                wordsMap.put(tmpKey, wordSet);
            }else{
                wordsMap.get(tmpKey).add(tmpValue);
            }
        }

        if(!wordsMap.containsKey(wordsString[length-1])){
            Set<String> wordSet = new HashSet<>();
            wordSet.add(wordsString[0]);
            wordsMap.put(wordsString[length-1], wordSet);
        }else{
            wordsMap.get(wordsString[length-1]).add(wordsString[0]);
        }

        int randomI = new Random().nextInt(length-1);
        String randomWord = wordsString[randomI];
        while(n>0){
            ans.append(randomWord).append(" ");
            Set<String> wordTmpSet = wordsMap.get(randomWord);
            int tmpLen = wordTmpSet.size();
            randomI = new Random().nextInt(tmpLen);
            String[] tm = wordTmpSet.toArray(new String[tmpLen]);
            randomWord = tm[randomI];
            n--;
        }

        return ans.toString();
    }

    public static void main(String[] args) {
        String words = "this is a sentence it is not a good one and it is also bad";
        int n = 5;
        String ans = new GenerateSentence().getNewWords(words,n);
        System.out.println(ans);
    }
}

