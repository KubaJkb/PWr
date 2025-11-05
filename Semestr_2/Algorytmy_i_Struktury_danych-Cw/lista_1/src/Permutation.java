public class Permutation  {
    public static boolean nextPermutation(int[] nums) {
        int len = nums.length;

        int i = len - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i == -1) {
            return false;
        }


        int j = len - 1;
        while (nums[j] <= nums[i]) {
            j--;
        }

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;


        reverse(nums, i + 1, len - 1);

        return true;
    }

    static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;

            start++;
            end--;
        }
    }
}
