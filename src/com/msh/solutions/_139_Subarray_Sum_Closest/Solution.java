package com.msh.solutions._139_Subarray_Sum_Closest;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by monkeysayhi on 2018/10/8.
 */
public class Solution {
  private static class Info {
    private int sum;
    private int index;
    Info(int sum, int index) {
      this.sum = sum;
      this.index = index;
    }
  }

  /**
   * @param nums: A list of integers
   * @return: A list of integers includes the index of the first number
   *          and the index of the last number
   */
  // 记录截止到每一位的sum，排序，遍历一遍，找min{sum[i]-sum[i - 1], |sum[j]|}
  public int[] subarraySumClosest(int[] nums) {
    if (nums == null || nums.length == 0) {
      return null;
    }
    if (nums.length == 1) {
      return new int[]{0, 0};
    }

    Info[] infos = new Info[nums.length];
    int sum = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      infos[i] = new Info(sum, i);
    }

    Arrays.sort(infos, new Comparator<Info>() {
      public int compare(Info info1, Info info2) {
        return info1.sum - info2.sum;
      }
    });
    int minDiffAbs = Math.abs(infos[0].sum);
    int[] minIndices = new int[]{0, infos[0].index};
    for (int i = 1; i < nums.length; i++) {
      int preSum = Math.abs(infos[i].sum);
      int midSum = infos[i].sum - infos[i - 1].sum;
      if (preSum > minDiffAbs && midSum > minDiffAbs) {
        continue;
      }
      if (preSum <= midSum) {
        minDiffAbs = preSum;
        minIndices[0] = 0;
        minIndices[1] = infos[i].index;
      } else {
        minDiffAbs = midSum;
        minIndices[0] = infos[i].index;
        minIndices[1] = infos[i - 1].index;
        Arrays.sort(minIndices);
        minIndices[0]++;
      }
      if (minDiffAbs == 0) {
        break;
      }
    }
    return minIndices;
  }

  // public int[] subarraySumClosest(int[] nums) {
  //     if (nums == null || nums.length == 0) {
  //         return null;
  //     }
  //     if (nums.length == 1) {
  //         return new int[]{0, 0};
  //     }

  //     int n = nums.length;
  //     Info[] infos = new Info[n + 1];
  //     int sum = 0;
  //     for (int i = 0; i < nums.length; i++) {
  //         sum += nums[i];
  //         infos[i] = new Info(sum, i);
  //     }
  //     infos[n] = new Info(0, -1);

  //     Arrays.sort(infos, new Comparator<Info>() {
  //         public int compare(Info info1, Info info2) {
  //             return info1.sum - info2.sum;
  //         }
  //     });
  //     assert infos.length >= 2;
  //     int minDiffAbs = Integer.MAX_VALUE;
  //     int[] minIndices = new int[2];
  //     for (int i = 1; i <= n; i++) {
  //         int midSum = infos[i].sum - infos[i - 1].sum;
  //         if (midSum < minDiffAbs) {
  //             minDiffAbs = midSum;
  //             minIndices[0] = infos[i].index;
  //             minIndices[1] = infos[i - 1].index;
  //         }
  //         if (minDiffAbs == 0) {
  //             break;
  //         }
  //     }
  //     Arrays.sort(minIndices);
  //     minIndices[0]++;
  //     return minIndices;
  // }
}

// // TLE, dp, easy but O(n^2)
// public class Solution {
//     private class SubArray {
//         public int l;
//         public int r;
//         public int sum;
//         public SubArray(int l, int r, int sum) {
//             this.l = l;
//             this.r = r;
//             this.sum = sum;
//         }
//         public SubArray(SubArray subArray) {
//             this.l = subArray.l;
//             this.r = subArray.r;
//             this.sum = subArray.sum;
//         }
//     }
//     /**
//      * @param nums: A list of integers
//      * @return: A list of integers includes the index of the first number
//      *          and the index of the last number
//      */
//     public int[] subarraySumClosest(int[] nums) {
//         // write your code here
//         if (nums == null || nums.length == 0) {
//             return null;
//         }

//         // dp[i] = cloestToZero{dp[i-1], nums[i-j:i](1<=j<=i)}
//         int[] sum = new int[nums.length];
//         SubArray[] dp = new SubArray[nums.length];

//         sum[0] = nums[0];
//         dp[0] = new SubArray(0, 0, nums[0]);
//         for (int i = 1; i < nums.length; i++) {
//             sum[i] = sum[i - 1] + nums[i];
//             dp[i] = new SubArray(0, i, sum[i]);
//             if (Math.abs(dp[i - 1].sum) < Math.abs(dp[i].sum)) {
//                 dp[i] = new SubArray(dp[i - 1]);
//             }
//             for (int j = 1; j <= i; j++) {
//                 if (Math.abs(sum[i] - sum[i - j]) < Math.abs(dp[i].sum)) {
//                     dp[i] = new SubArray(i - j + 1, i, sum[i] - sum[i - j]);
//                 }
//             }
//         }

//         return new int[]{dp[nums.length - 1].l, dp[nums.length - 1].r};
//     }
// }
