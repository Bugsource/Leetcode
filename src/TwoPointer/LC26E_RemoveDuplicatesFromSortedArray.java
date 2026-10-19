package TwoPointer;

// 从非递减的序列里，移除重复数字，返回去重后的数字数量
public class LC26E_RemoveDuplicatesFromSortedArray {

    public int removeDuplicates(int[] nums) {
        int slowPointer = 0, fastPointer = 0;
        for(int i = 1; i < nums.length; ++ i) {
            if(nums[i] != nums[i-1]) {
                fastPointer = i;
                // 遇到下一个有效数字，移动慢指针，赋值
                ++ slowPointer;
                nums[slowPointer] = nums[fastPointer];
            }
        }
        return slowPointer+1;
    }
}
