package com.msh.solutions._143_Sort_Colors_II;

/**
 * Created by monkeysayhi on 2018/10/9.
 */
// 注意到k <= n，则可考虑 O(nlogk) 的算法
public class Solution {
  /**
   * @param colors: A list of integer
   * @param k: An integer
   * @return: nothing
   */
  // solution 2：O(n) 的算法，如计数排序
  public void sortColors2(int[] colors, int k) {
    int[] nums = colors;
    if (nums == null || nums.length <= 1) {
      return;
    }
    countingSort(nums, k);
  }

  private void countingSort(int[] nums, int k) {
    int[] stat = new int[k + 1];
    for (int i = 0; i < nums.length; i++) {
      stat[nums[i]]++;
    }
    for (int i = 1; i < stat.length; i++) {
      stat[i] += stat[i - 1];
    }

    int[] buf = new int[nums.length];
    for (int i = nums.length - 1; i >= 0; i--) {
      buf[stat[nums[i]] - 1] = nums[i];
      stat[nums[i]]--;
    }
    System.arraycopy(buf, 0, nums, 0, buf.length);
  }

//     // solution 1：O(nlogk) 的算法，如彩虹排序
//     public void sortColors2(int[] colors, int k) {
//         int[] nums = colors;
//         if (nums == null || nums.length <= 1) {
//             return;
//         }
//         rainbowSort(nums, 0, nums.length, 1, k);
//     }

//     private void rainbowSort(int[] nums, int l, int r, int min, int max) {
//         if (l == r || l + 1 == r) {
//             return;
//         }
//         if (min == max) {
//             return;
//         }

//         int mid = min + (max - min) / 2;
//         int i = l;
//         int j = r - 1;
//         while (i < j) {
//             // mid 可能等于 min，因此，必须让小于等于 mid 的值位于左侧，这样左侧至少有一个元素
//             while (i < j && nums[i] <= mid) {
//                 i++;
//             }
//             while (i < j && nums[j] > mid) {
//                 j--;
//             }
//             if (i < j) {
//                 swap(nums, i, j);
//                 i++;
//                 j--;
//             }
//         }
//         // 要保证两侧都没有丢下元素
//         if (nums[i] <= mid) {
//             rainbowSort(nums, l, i + 1, min, mid);
//             rainbowSort(nums, i + 1, r, mid + 1, max);
//         } else {
//             rainbowSort(nums, l, i, min, mid);
//             rainbowSort(nums, i, r, mid + 1, max);
//         }
//         // // 一个简单的办法是，让两个分支都处理nums[i]；但这样会破坏已处理的结果，其他题目慎用
//         // rainbowSort(nums, l, i + 1, min, mid);
//         // rainbowSort(nums, i, r, mid + 1, max);
//     }

//     private void swap(int[] nums, int i, int j) {
//         int tmp = nums[i];
//         nums[i] = nums[j];
//         nums[j] = tmp;
//     }

}
