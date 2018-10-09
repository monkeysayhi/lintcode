package com.msh.solutions._447_Search_in_a_Big_Sorted_Array;

/**
 * Created by monkeysayhi on 2018/10/9.
 */

interface ArrayReader {
     // get the number at index, return -1 if index is less than zero.
     int get(int index);
}

public class Solution {
  /**
   * @param reader: An instance of ArrayReader.
   * @param target: An integer
   * @return : An integer which is the index of the target number
   */
  public int searchBigSortedArray(ArrayReader reader, int target) {
    // write your code here
    if (reader == null) {
      return -1;
    }

    if (reader.get(0) > target) {
      return -1;
    }

    int[] range = computeRange(reader, target);
    if (range == null) {
      return -1;
    }

    int k = bsearchLowerBound(reader, range[0], range[1], target);
    if (reader.get(k) != target) {
      return -1;
    }

    return k;
  }

  private int[] computeRange(ArrayReader reader, int target){
    int r = 1;
    while (reader.get(r) < target) {
      r <<= 1;
    }

    int l = r >> 1;
    while (r >= l && reader.get(r) == 2147483647) {
      r--;
    }
    if (r < l) {
      return null;
    }

    return new int[]{l, r};
  }

  private int bsearchLowerBound(ArrayReader reader, int l, int r, int v) {
    while (l < r) {
      int m = l + (r - l) / 2;
      if (reader.get(m) >= v) {
        r = m;
      } else {
        l = m + 1;
      }
    }
    return l;
  }
}