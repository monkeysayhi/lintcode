package com.msh.solutions._532_Reverse_Pairs;

/**
 * Created by monkeysayhi on 2018/10/9.
 */
public class Solution {
  /*
   * @param A: an array
   * @return: total of reverse pairs
   */
  // 归并排序变种 - 统计逆序对
  public long reversePairs(int[] A) {
    int[] nums = A;
    if (nums == null || nums.length <= 1) {
      return 0;
    }
    return countReversePairs(nums, 0, nums.length);
  }

  private long countReversePairs(int[] nums, int l, int r) {
    if (l == r || l + 1 == r) {
      return 0;
    }

    int m = l + (r - l) / 2;
    long left = countReversePairs(nums, l, m);
    long right = countReversePairs(nums, m, r);
    long cross = merge(nums, l, m, r);
    return left + right + cross;
  }

  private long merge(int[] nums, int l, int m, int r) {
    int[] buf = new int[r - l];
    int i = l;
    int j = m;
    int k = 0;
    int cross = 0;
    while (i < m && j < r) {
      if (nums[i] <= nums[j]) {
        buf[k++] = nums[i++];
      } else {
        buf[k++] = nums[j++];
        cross += m - i;
      }
    }
    while (i < m) {
      buf[k++] = nums[i++];
    }
    while (j < r) {
      buf[k++] = nums[j++];
    }
    System.arraycopy(buf, 0, nums, l, buf.length);
    return cross;
  }
}