import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongestConsecutive {

    public int longestConsecutive(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            // 空数组情况，currConsecutive为1会过不了，逻辑不好统一处理，所以这里提前返回
            return 0;
        }

        // 时间复杂度nlogn
        Arrays.sort(nums);

        int distinctIndex = 1;
        for (int i = 0; i < n - 1; ++i) {
            // 双指针去重
            if (nums[i + 1] != nums[i]) {
                nums[distinctIndex] = nums[i + 1];
                distinctIndex++;
            }
        }
        int maxConsecutive = 0;
        int currConsecutive = 1;
        // 注意distinctIndex最后会是去重元素的下一个位置
        for (int i = 0; i < distinctIndex - 1; ++i) {
            if (nums[i + 1] == nums[i] + 1) {
                ++currConsecutive;
            } else {
                maxConsecutive = Math.max(maxConsecutive, currConsecutive);
                // 注意，当前连续数量初始值是1
                currConsecutive = 1;
            }
        }
        maxConsecutive = Math.max(maxConsecutive, currConsecutive);
        return maxConsecutive;
    }

    public static void main(String[] a) {
        LongestConsecutive l = new LongestConsecutive();
        int[] input1 = new int[]{100, 4, 200, 1, 3, 2};
        // [1, 2, 3, 4]
        System.out.println("res1:" + l.longestConsecutive(input1));

        int[] input2 = new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
        System.out.println("res2:" + l.longestConsecutive(input2));

        int[] input3 = new int[]{1, 2, 0, 1};
        System.out.println("res3:" + l.longestConsecutive(input3));

        int[] input4 = new int[]{9, 1, 4, 7, 3, -1, 0, 5, 8, -1, 6};
        System.out.println("res4:" + l.longestConsecutive(input4));

        List<String> bb = new ArrayList<>();
        bb.add("aa");
        bb.add("bb");
        List<Object> aa = new ArrayList<>(bb);

        System.out.println(aa.toString());
    }
}
