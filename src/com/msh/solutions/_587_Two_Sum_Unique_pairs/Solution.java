package com.msh.solutions._587_Two_Sum_Unique_pairs;

import java.util.Arrays;

/**
 * Created by monkeysayhi on 2018/10/9.
 */
public class Solution {
  /**
   * @param nums an array of integer
   * @param target an integer
   * @return an integer
   */
  // 和无序数组上的twoSum思路相同，这里仅给出两头收缩的方法
  // 加个去重而已
  public int twoSum6(int[] nums, int target) {
    if (nums == null || nums.length <= 1) {
      return 0;
    }

    Arrays.sort(nums);
    int l = 0;
    int r = nums.length - 1;
    int cnt = 0;
    while (l < r) {
      if (l > 1 && nums[l] == nums[l - 1]) {
        l++;
        continue;
      }
      if (r < nums.length - 1 && nums[r] == nums[r + 1]) {
        r--;
        continue;
      }
      int sum = nums[l] + nums[r];
      if (sum < target) {
        l++;
      } else if (sum > target) {
        r--;
      } else {
        cnt++;
        l++;
        r--;
      }
    }
    return cnt;
  }
}