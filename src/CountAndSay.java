import java.util.HashMap;
import java.util.Map;

public class CountAndSay {
    public String countAndSay(int n) {

        Map<String, String> originToTransformed = new HashMap<>();
        return countAndSay(n, originToTransformed);
    }

    private String countAndSay(int n, Map<String, String> originToTransformed) {
        if (n == 1) {
            return "1";
        }

        String prev = countAndSay(n - 1);
        if (originToTransformed.containsKey(prev)) {
            return originToTransformed.get(prev);
        }
        int prevLength = prev.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prevLength; ++i) {
            // 能进来循环，就代表i有合法值，先初始化计数为1
            int currRepeatCount = 1;
            while (i + 1 < prevLength && prev.charAt(i) == prev.charAt(i + 1)) {
                ++i;
                ++currRepeatCount;
            }
            sb.append(currRepeatCount).append(prev.charAt(i));
        }
        String curr = sb.toString();
        originToTransformed.put(prev, curr);
        return curr;
    }

    public String countAndSay0(int n) {
        if (n == 1) {
            return "1";
        }

        String prev = countAndSay(n - 1);
        int prevLength = prev.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prevLength; ++i) {
            // 能进来循环，就代表i有合法值，先初始化计数为1
            int currRepeatCount = 1;
            while (i + 1 < prevLength && prev.charAt(i) == prev.charAt(i + 1)) {
                ++i;
                ++currRepeatCount;
            }
            sb.append(currRepeatCount).append(prev.charAt(i));
        }
        return sb.toString();
    }

    public static void main(String[] args){
        CountAndSay countAndSay = new CountAndSay();
        for(int i = 1; i < 5; ++ i){
            System.out.println("count:" + i + ",res:" + countAndSay.countAndSay(i));
        }
    }
}
