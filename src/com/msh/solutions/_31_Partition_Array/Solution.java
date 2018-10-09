package com.msh.solutions._31_Partition_Array;

/**
 * Created by monkeysayhi on 2018/10/9.
 */
public class Solution {
  /*
   * @param nums: The integer array you should partition
   * @param k: An integer
   * @return: The index after partition
   */
  // 朴素划分 lt、ge
  // 如果没有限制，则只统计一下小于k的有多少即可
  public int partitionArray(int[] nums, int k) {
    int lt = -1;
    for (int cur = 0; cur < nums.length; cur++) {
      if (nums[cur] < k) {
        swap(nums, cur, ++lt);
      }
    }
    return lt + 1;
  }

  private void swap(int[] nums, int i, int j) {
    int tmp = nums[i];
    nums[i] = nums[j];
    nums[j] = tmp;
  }
}