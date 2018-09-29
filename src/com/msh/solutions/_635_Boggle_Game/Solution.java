package com.msh.solutions._635_Boggle_Game;

import java.util.*;

/**
 * Created by monkeysayhi on 2018/9/29.
 */
public class Solution {
  private static class TrieNode {
    private char c;
    private Map<Character, TrieNode> children;
    private boolean isWord;
    private String s;

    private TrieNode() {
      this.c = 0;
      children = new HashMap<>();
      isWord = false;
      s = null;
    }

    private static void addWord(TrieNode root, String w) {
      char[] s = w.toCharArray();
      assert s.length > 0;
      TrieNode p = root;
      for (int i = 0; i < s.length; i++) {
        char c = s[i];
        TrieNode node = null;
        if (p.children.containsKey(c)) {
          node = p.children.get(c);
        } else {
          node = new TrieNode();
          node.c = c;
          p.children.put(c, node);
        }
        if (i == s.length - 1) {
          node.isWord = true;
          node.s = w;
        }
        p = node;
      }
    }
  }

  private static final int[][] DIRECS = new int[][]{
      {0, 1}, {0, -1}, {1, 0}, {-1, 0}
  };

  /**
   * @param board a list of lists of character
   * @param words a list of string
   * @return an integer
   */
  // solution 2: 看[题解](https://www.jiuzhang.com/solution/boggle-game/)
  // 。双层回溯暴搜，利用Trie根据前缀剪枝，利用搜索的初始位置定序，以避免重复子问题
  public int boggleGame(char[][] board, String[] words) {
    // assume valid
    char[][] mat = board;
    int m = mat.length;
    int n = mat[0].length;
    TrieNode root = new TrieNode();
    for (String w : words) {
      if (w.length() > 0) {
        TrieNode.addWord(root, w);
      }
    }

    List<String> maxCollection = new ArrayList<>();
    boolean[][] vis = new boolean[m][n];
    Stack<String> buf = new Stack<>();
    dfsFromPoint(mat, 0, 0, vis, root, buf, maxCollection);
    return maxCollection.size();
  }

  private void dfsFromPoint(char[][] mat, int x, int y,
                            boolean[][] vis, TrieNode root,
                            Stack<String> bufCollection, List<String> maxCollection) {
    int m = mat.length;
    int n = mat[0].length;
    for (int i = x; i < m; i++) {
      // 注意控制起点
      for (int j = i == x ? y : 0; j < n; j++) {
        if (vis[i][j]) {
          continue;
        }
        List<List<int[]>> nextWordIdxs = new ArrayList<>();
        Stack<int[]> buf = new Stack<>();
        dfsNextWords(mat, i, j, vis, root, buf, nextWordIdxs);
        for (List<int[]> wordIdx : nextWordIdxs) {
          StringBuilder sb = new StringBuilder();
          for (int[] pair : wordIdx) {
            int chI = pair[0];
            int chJ = pair[1];
            assert !vis[chI][chJ];
            vis[chI][chJ] = true;
            sb.append(mat[chI][chJ]);
          }
          bufCollection.push(sb.toString());

          if (bufCollection.size() > maxCollection.size()) {
            maxCollection.clear();
            maxCollection.addAll(bufCollection);
          }
          dfsFromPoint(mat, i, j, vis, root, bufCollection, maxCollection);

          for (int[] pair : wordIdx) {
            int chI = pair[0];
            int chJ = pair[1];
            vis[chI][chJ] = false;
          }
          bufCollection.pop();
        }
      }
    }
  }

  private void dfsNextWords(char[][] mat, int x, int y,
                            boolean[][] vis, TrieNode parent,
                            Stack<int[]> bufWordIdx, List<List<int[]>> nextWordIdxs) {
    if (vis[x][y]) {
      return;
    }
    if (!parent.children.containsKey(mat[x][y])) {
      return;
    }
    TrieNode node = parent.children.get(mat[x][y]);
    if (node.isWord) {
      bufWordIdx.push(new int[]{x, y});
      nextWordIdxs.add(new ArrayList<>(bufWordIdx));
      bufWordIdx.pop();
      return;
    }

    bufWordIdx.push(new int[]{x, y});
    vis[x][y] = true;

    for (int[] direc : DIRECS) {
      int i = x + direc[0];
      int j = y + direc[1];
      if (i >= 0 && i < mat.length && j >= 0 && j < mat[0].length) {
        dfsNextWords(mat, i, j, vis, node, bufWordIdx, nextWordIdxs);
      }
    }

    vis[x][y] = false;
    bufWordIdx.pop();
  }
}


// public class Solution {
//     private static final int[][] DIRECS = new int[][]{
//         {0, 1}, {1, 0}, {0, -1}, {-1, 0}
//     };

//     /*
//      * @param board: a list of lists of character
//      * @param words: a list of string
//      * @return: an integer
//      */

//     // solution 1: 暴力，枚举所有组合，检查，O(2^n)
//     // 重复子问题：如果两个集合都包含同一个子集，那么显然子集的状态只需要判断一次，且该子集一定在之前已经计算过
//     // 容易被漏掉的子问题：可以重用 word，比如输入“[[a, a]], a”，那么最大集为“[a, a]”
//     // solution -2: 最优化，求最大。设 dp[i] 为 “以 w[i] 结尾的最大集合的大小，多种方案为 vis[i][][][]”，则 dp[i] =
// max{dp[j] + 1 | noOverlap(vis[j][][][], w[i])}，边界 dp[0] = 1, 答案 max{dp[i]}

//     // solution -2
//     public int boggleGame(char[][] board, String[] words) {
//         // assume valid
//         char[][] mat = board;
//         int n = words.length;
//         int m1 = mat.length;
//         int m2 = mat.length;
//         int[] dp = new int[n];
//         Set<boolean[][]>[] vis = new HashSet[n];
//         if (check(words[0], mat)) {
//             dp[0] = 1;
//             vis[0] = new HashSet<>();
//             assert addVis(mat, vis[0], new boolean[m1][m2], words[0]);
//             int len = 1;
//             for (Set<boolean[][]> v : vis[0])
//         }
//         int max = dp[0];
//         for (int i = 1; i < n; i++) {
//             if (check(words[i], mat)) {
//                 dp[i] = 1;
//                 vis[i] = new HashSet<>();
//                 assert addVis(mat, vis[i], new boolean[m1][m2], words[i]);
//                 for (int j = i - 1; j >= 0; j--) {
//                     if (dp[i] < dp[j] + 1) {
//                         Set<boolean[][]> bakVisI = vis[i];
//                         int bakDpI = dp[j];
//                         vis[i] = new HashSet<>();
//                         for (boolean[][] v : vis[j]) {
//                             if (addVis(mat, vis[i], v, words[i])) {
//                                 dp[i] = dp[j] + 1;
//                             }
//                         }
//                         if (vis[i].size() == 0) {
//                             vis[i] = bakVisI;
//                             dp[i] = bakDpI;
//                         }
//                     }
//                 }
//             }
//             max = Math.max(max, dp[i]);
//         }
//         return max;
//     }

//     private boolean addVis(char[][] mat,
//                           Set<boolean[][]> set, boolean[][] src, String w) {
//         char[] s = w.toCharArray();
//         boolean[][] dst = copy(src);
//         if (visit(mat, dst, s)) {
//             set.add(dst);
//             return true;
//         }
//         return false;
//     }

//     private boolean visit(char[][] mat, boolean[][] vis, char[] s) {
//         int m = vis.length;
//         int n = vis[0].length;
//         for (int i = 0; i < m; i++) {
//             for (int j = 0; j < m; j++) {
//                 if (!vis[i][j] && visitInt(mat, vis, s, i, j, 0)) {
//                     return true;
//                 }
//             }
//         }
//         return false;
//     }

//     private boolean visitInt(char[][] mat, boolean[][] vis,
//                              char[] s, int x, int y, int cur) {
//         if (mat[x][y] != s[cur]) {
//             return false;
//         }
//         if (cur == s.length) {
//             return true;
//         }
//         vis[x][y] = true;
//         for (int[] direc : DIRECS) {
//             int i = x + direc[0];
//             int j = y + direc[0];
//             if (i >= 0 && i < mat.length && j >= 0 && j < mat[0].length) {
//                 if (!vis[i][j] && visitInt(mat, vis, s, i, j, cur + 1)) {
//                     return true;
//                 }
//             }
//         }
//         vis[x][y] = false;
//         return false;
//     }

//     // maybe no need
//     private boolean check(String w, char[][] mat) {
//         char[] s = w.toCharArray();
//         boolean[][] vis = new boolean[mat.length][mat[0].length];
//         return visit(mat, vis, s);
//     }

//     private boolean[][] copy(boolean[][] org) {
//         int m = org.length;
//         int n = org[0].length;
//         boolean[][] cpy = new boolean[m][n];
//         for (int i = 0; i < m; i++) {
//             for (int j = 0; j < m; j++) {
//                 cpy[i][j] = org[i][j];
//             }
//         }
//         return cpy;
//     }

//     // // solution -1: 最优化，求最大。题目理解错误
//     // // 设 dp[i] 为 “以 w[i] 结尾的最大集合的大小，多种方案为 vis[i][][]”
//     // // 则 dp[i] = max{dp[j] + 1 | noOverlap(vis[j][][], w[i])}
//     // // 边界 dp[0] = 1, 答案 max{dp[i]}
//     // public int boggleGame(char[][] board, String[] words) {
//     //     // assume valid
//     //     int n = words.length;
//     //     int m = board.length * board[0].length;
//     //     int[] dp = new int[n];
//     //     Set<boolean[]>[] vis = new HashSet[n];
//     //     if (check(words[0], m)) {
//     //         dp[0] = 1;
//     //         vis[0] = new HashSet<>();
//     //         addVis(vis[0], new boolean[m], words[0]);
//     //     }
//     //     int max = dp[0];
//     //     for (int i = 1; i < n; i++) {
//     //         if (check(words[i], m)) {
//     //             dp[i] = 1;
//     //             vis[i] = new HashSet<>();
//     //             addVis(vis[i], new boolean[m], words[i]);
//     //             for (int j = i - 1; j >= 0; j--) {
//     //                 if (dp[i] < dp[j] + 1) {
//     //                     Set<boolean[]> bakVisI = vis[i];
//     //                     int bakDpI = dp[j];
//     //                     vis[i] = new HashSet<>();
//     //                     for (boolean[] v : vis[j]) {
//     //                         if (noOverlap(v, words[i])) {
//     //                             dp[i] = dp[j] + 1;
//     //                             addVis(vis[i], v, words[i]);
//     //                         }
//     //                     }
//     //                     if (vis[i].size() == 0) {
//     //                         vis[i] = bakVisI;
//     //                         dp[i] = bakDpI;
//     //                     }
//     //                 }
//     //             }
//     //         }
//     //         max = Math.max(max, dp[i]);
//     //     }
//     //     return max;
//     // }

//     // private void addVis(Set<boolean[]> set, boolean[] src, String s) {
//     //     int m = src.length;
//     //     boolean[] dst = new boolean[src.length];
//     //     System.arraycopy(src, 0, dst, 0, m);
//     //     for (char c : s.toCharArray()) {
//     //         assert !dst[c - 'a'];
//     //         dst[c - 'a'] = true;
//     //     }
//     //     set.add(dst);
//     // }

//     // private boolean noOverlap(boolean[] v, String s) {
//     //     for (char c : s.toCharArray()) {
//     //         if (v[c - 'a']) {
//     //             return false;
//     //         }
//     //     }
//     //     return true;
//     // }

//     // // maybe no need
//     // private boolean check(String s, int m) {
//     //     // for (char c : s.toCharArray()) {
//     //     //     if (c - 'a' < 0 || c - 'a' >= m) {
//     //     //         return false;
//     //     //     }
//     //     // }
//     //     return true;
//     // }
// }