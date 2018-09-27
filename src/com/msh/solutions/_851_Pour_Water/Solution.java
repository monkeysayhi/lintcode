package com.msh.solutions._851_Pour_Water;

/**
 * Created by monkeysayhi on 2018/9/27.
 */
public class Solution {
  /**
   * @param heights: the height of the terrain
   * @param V:       the units of water
   * @param K:       the index
   * @return: how much water is at each index
   */
  // solution 1: 暴力，每次先遍历左边，再遍历右边，都不行就中间，O(n * v)
  // solution -2: 左右都用最小堆维护，哪边的堆顶小，就去哪边，O(n * lgn)（初始化最小堆的成本）——不能越过高地
  // solution 1
  public int[] pourWater(int[] heights, int V, int K) {
    // assume valid
    int[] hs = heights;
    while (V-- > 0) {
      int m = hs[K];

      int l = Integer.MAX_VALUE;
      int lI = -1;
      for (int i = K - 1; i >= 0 && hs[i] <= hs[i + 1]; i--) {
        if (l == Integer.MAX_VALUE || l > hs[i]) {
          l = hs[i];
          lI = i;
        }
      }
      if (l < m) {
        hs[lI]++;
        continue;
      }

      int r = Integer.MAX_VALUE;
      int rI = -1;
      for (int i = K + 1; i <= hs.length - 1 && hs[i] <= hs[i - 1]; i++) {
        if (r == Integer.MAX_VALUE || r > hs[i]) {
          r = hs[i];
          rI = i;
        }
      }
      if (r < m) {
        hs[rI]++;
        continue;
      }

      hs[K]++;
    }
    return hs;
  }
}