package TwoPointer;

public class LC80M_RemoveDuplicatesFromSortedArray2 {

    public static int removeDuplicates(int[] nums) {
        int slowIndex = 1, currDuplicateCount = 1;

        for(int i = 1; i < nums.length; ++ i) {
            while(currDuplicateCount < 2 && i < nums.length && nums[i] == nums[i-1]) {
                ++i;
                ++slowIndex;
                ++currDuplicateCount;
            }
            if(currDuplicateCount == 2) {
                // 如果上面是因为找到两个重复数，退出了循环，那么继续向前，寻找下一个有效数字；
                // 注意，上一个循环，除了推进快指针i，主要负责推进慢指针slowIndex
                while(i < nums.length && nums[i] == nums[i-1]) {
                    ++i;
                }
            }
            // 重置当前重复数
            currDuplicateCount = 1;
            nums[slowIndex] = nums[i];
        }
        return slowIndex;
    }

    public static void main(String[] a) {
        int[] input1 = new int[]{1,1,1,2,2,3};
        System.out.println("res1:" + removeDuplicates(input1));

        int[] input2 = new int[]{0,0,1,1,1,1,2,3,3};
        System.out.println("res2:" + removeDuplicates(input2));
    }
}
