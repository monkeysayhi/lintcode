package com.msh.solutions._609_Two_Sum_Less_than_or_equal_to_target;

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
  public int twoSum5(int[] nums, int target) {
    if (nums == null || nums.length <= 1) {
      return 0;
    }

    Arrays.sort(nums);
    int l = 0;
    int r = nums.length - 1;
    int cnt = 0;
    while (l < r) {
      if (nums[l] + nums[r] <= target) {
        cnt += r - l;
        l++;
      } else {
        r--;
      }
    }
    return cnt;
  }
}