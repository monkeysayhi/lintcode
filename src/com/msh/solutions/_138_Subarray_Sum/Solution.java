package com.msh.solutions._138_Subarray_Sum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by monkeysayhi on 2018/10/8.
 */
public class Solution {
  /*
   * @param nums: A list of integers
   * @return: A list of integers includes the index of the first number and the index of the last number
   */
  // 记录截止到每一位的sum，如果存在sum相同的两个位置，寻找{sum[i] == sum[j] or sum[k] == 0}
  public List<Integer> subarraySum(int[] nums) {
    // no edge condition
    List<Integer> result = new ArrayList<>(2);
    Map<Integer, Integer> sums = new HashMap<>(nums.length + 1);
    int sum = 0;
    sums.put(0, 0);
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      if (sums.containsKey(sum)) {
        result.add(sums.get(sum));
        result.add(i);
        return result;
      }
      sums.put(sum, i + 1);
    }
    throw new UnknownError("Impossible");
  }
}