package com.msh.solutions._610_Two_Sum_Difference_equals_to_target;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by monkeysayhi on 2018/10/9.
 */
public class Solution {
  private static class Info {
    private int val;
    private int index;
    Info(int val, int index) {
      this.val = val;
      this.index = index;
    }
  }

  /*
   * @param nums an array of Integer
   * @param target an integer
   * @return [index1 + 1, index2 + 1] (index1 < index2)
   */
  // i、j从左侧开始，大于就i++，小于就j++
  // 注意，target可能为负值，要取target的绝对值
  public int[] twoSum7(int[] nums, int target) {
    if (nums == null || nums.length < 2) {
      return null;
    }

    Info[] infos = new Info[nums.length];
    for (int i = 0; i < nums.length; i++) {
      infos[i] = new Info(nums[i], i);
    }
    Arrays.sort(infos, new Comparator<Info>(){
      public int compare(Info info1, Info info2) {
        return info1.val - info2.val;
      }
    });

    int i = 0;
    int j = 1;
    // badcase：target为负值
    target = Math.abs(target);
    while (i < j && j < nums.length) {
      int diff = infos[j].val - infos[i].val;
      if (diff < target) {
        j++;
      } else if (diff > target) {
        i++;
        // badcase：target为0
        if (i == j) {
          j++;
        }
      } else {
        int[] rs = new int[]{infos[i].index + 1, infos[j].index + 1};
        Arrays.sort(rs);
        return rs;
      }
    }
    return null;
  }
}

// public class Solution {
//     private class Info {
//         public int index;
//         public int val;
//         Info(int index, int val) {
//             this.index = index;
//             this.val = val;
//         }
//     }
//     /*
//      * @param nums an array of Integer
//      * @param target an integer
//      * @return [index1 + 1, index2 + 1] (index1 < index2)
//      */
//     public int[] twoSum7(int[] nums, int target) {
//         // write your code here
//         if (nums == null || nums.length <= 1) {
//             return null;
//         }

//         target = Math.abs(target);
//         Info[] infos = new Info[nums.length];
//         for (int i = 0; i < nums.length; i++) {
//             infos[i] = new Info(i, nums[i]);
//         }
//         Arrays.sort(infos, new Comparator<Info>() {
//             @Override
//             public int compare(Info info1, Info info2) {
//                 return info1.val - info2.val;
//             }
//         });

//         int l = 0;
//         int r = 1;
//         int[] indices = null;
//         while (l < r && r < infos.length) {
//             int diff = infos[r].val - infos[l].val;
//             if (diff < target) {
//                 r++;
//             } else if (diff > target) {
//                 l++;
//                 if (l == r) {
//                     r++;
//                 }
//             } else {
//                 indices = new int[]{infos[l].index + 1, infos[r].index + 1};
//                 Arrays.sort(indices);
//                 break;
//             }
//         }
//         return indices;
//     }
// }