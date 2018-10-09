package com.msh.solutions._533_Two_Sum_Closest_to_target;

import java.util.Arrays;

/**
 * Created by monkeysayhi on 2018/10/9.
 */
public class Solution {
  /*
   * @param nums: an integer array
   * @param target: An integer
   * @return: the difference between the sum and the target
   */
  // 在基础twosum上，每次算出来个和就判断diff
  // 最后的diff是个绝对值
  public int twoSumClosest(int[] nums, int target) {
    if (nums == null || nums.length <= 1) {
      return Integer.MAX_VALUE;
    }

    Arrays.sort(nums);
    int l = 0;
    int r = nums.length - 1;
    int minAbsDiff = Integer.MAX_VALUE;;
    while (l < r) {
      int realDiff = nums[l] + nums[r] - target;
      int absDiff = Math.abs(realDiff);
      minAbsDiff = Math.min(minAbsDiff, absDiff);
      if (realDiff < 0) {
        l++;
      } else if (realDiff > 0) {
        r--;
      } else {
        return 0;
      }
    }
    return minAbsDiff;
  }
}