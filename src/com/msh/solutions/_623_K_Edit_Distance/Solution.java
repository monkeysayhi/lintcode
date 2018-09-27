package com.msh.solutions._623_K_Edit_Distance;

import java.util.*;

/**
 * Created by monkeysayhi on 2018/9/27.
 */
public class Solution {
  /**
   * @param words:  a set of stirngs
   * @param target: a target string
   * @param k:      An integer
   * @return: output all the strings that meet the requirements
   */
  // solution -1: 暴力，n 次 动规求最小编辑距离，O(n * l1 * l2)，TLE
  // 查题解时看到一种[多次查询编辑距离的方法](http://www.matrix67.com/blog/archives/333)，但不适用于此处

  // solution 1: test case给的不好。出现极端多的前缀相同的words，这样就能像[题解](https://www.jiuzhang
  // .com/solution/k-edit-distance/)一样，利用 Trie 树，重复利用相同前缀的 dp。最坏情况下，时间复杂度仍然是 O(n * l1 * l2)
  public List<String> kDistance(String[] words, String target, int k) {
    List<String> rs = new ArrayList<>();
    TrieNode root = buildTrieTree(words);
    Map<Integer, int[]> dp = new HashMap<>();
    dfs(0, root, target.toCharArray(), dp, k, rs);
    return rs;
  }

  private void dfs(int cur, TrieNode root, char[] t,
                   Map<Integer, int[]> dp,
                   int k, List<String> rs) {
    int n = t.length;
    int[] dpCur = new int[n + 1];
    dp.put(cur, dpCur);
    dpCur[0] = cur;
    if (cur == 0) {
      for (int i = 1; i <= n; i++) {
        dp.get(0)[i] = i;
      }
    } else {
      for (int i = 1; i <= n; i++) {
        dpCur[i] = Integer.MAX_VALUE;
        if (root.c == t[i - 1]) {
          dpCur[i] = Math.min(dpCur[i], dp.get(cur - 1)[i - 1]);
        }
        dpCur[i] = Math.min(dpCur[i], 1 + dp.get(cur)[i - 1]);  // insert
        dpCur[i] = Math.min(dpCur[i], 1 + dp.get(cur - 1)[i]);  // delete
        dpCur[i] = Math.min(dpCur[i], 1 + dp.get(cur - 1)[i - 1]);  // replace
      }
    }
    if (root.isWord && dpCur[n] <= k) {
      for (int i = 0; i < root.cnt; i++) {
        rs.add(root.s);
      }
    }

    for (TrieNode child : root.children.values()) {
      dfs(cur + 1, child, t, dp, k, rs);
    }
  }

  private static class TrieNode {
    private char c;
    private Map<Character, TrieNode> children;

    private boolean isWord;
    private int cnt;
    private String s;

    private TrieNode() {
      c = 0;
      children = new HashMap<>();
      isWord = false;
      s = null;
      cnt = 0;
    }

    private static void addWord(TrieNode root, String w) {
      assert root.s.equals("");
      if (w.equals("")) {
        root.isWord = true;
        root.cnt++;
        return;
      }
      TrieNode p = root;
      char[] s = w.toCharArray();
      for (int i = 0; i < s.length; i++) {
        char c = s[i];
        TrieNode node = null;
        if (p.children.containsKey(c)) {
          node = p.children.get(c);
        } else {
          node = new TrieNode();
          node.c = s[i];
          p.children.put(c, node);
        }
        if (i == s.length - 1) {
          node.s = w;
          node.isWord = true;
          node.cnt++;
        }
        p = node;
      }
    }
  }

  private TrieNode buildTrieTree(String[] ws) {
    TrieNode root = new TrieNode();
    root.s = "";
    for (String w : ws) {
      TrieNode.addWord(root, w);
    }
    return root;
  }

  // // solution -1
  // public List<String> kDistance(String[] words, String target, int k) {
  //     // assume valid
  //     List<String> rs = new ArrayList<>();
  //     for (String w: words) {
  //         if (editDistance(w, target) <= k) {
  //             rs.add(w);
  //         }
  //     }
  //     return rs;
  // }

  // private int editDistance(String src, String dst) {
  //     if (src.length() == 0 && dst.length() == 0) {
  //         return 0;
  //     }
  //     if (src.length() == 0) {
  //         return dst.length();
  //     }
  //     if (dst.length() == 0) {
  //         return src.length();
  //     }

  //     char[] s = src.toCharArray();
  //     char[] t = dst.toCharArray();
  //     int m = s.length;
  //     int n = t.length;
  //     int[][] dp = new int[m + 1][n + 1];
  //     for (int i = 0; i <= m; i++) {
  //         for (int j = 0; j <= n; j++) {
  //             dp[i][j] = Integer.MAX_VALUE;
  //             if (i == 0 && j == 0) {
  //                 dp[0][0] = 0;
  //             } else if (i == 0) {
  //                 dp[0][j] = j;
  //             } else if (j == 0) {
  //                 dp[i][0] = i;
  //             } else {
  //                 if (s[i - 1] == t[j - 1]) {
  //                     dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
  //                 }
  //                 dp[i][j] = Math.min(dp[i][j], 1 + dp[i][j - 1]); // insert
  //                 dp[i][j] = Math.min(dp[i][j], 1 + dp[i - 1][j]); // delete
  //                 dp[i][j] = Math.min(dp[i][j], 1 + dp[i - 1][j - 1]); // replace
  //             }
  //         }
  //     }
  //     return dp[m][n];
  // }
}