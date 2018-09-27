package com.msh.solutions._950_Sliding_Puzzle_III;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by monkeysayhi on 2018/9/27.
 */
public class Solution {
  private static final int[][] DIRECS = new int[][]{
      {0, 1}, {1, 0}, {0, -1}, {-1, 0}
  };
  private static final int M = 3;
  private static final int N = 3;

  /**
   * @param matrix: The 3*3 matrix
   * @return: The answer
   */
  // 以 dst 为根，层序bfs，遍历到 src
  // 因为 2^9 种摆放方案，暴力枚举即可
  public String jigsawPuzzle(int[][] matrix) {
    // assume valid
    int[][] src = matrix;
    int[][] dst = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    int[][] root = dst;
    int[][] target = src;
    if (hash(root) == hash(target)) {
      return "YES";
    }

    int hashTarget = hash(target);
    // TODO: opt: replace all mat with hash
    Queue<int[][]> q = new LinkedList<>();
    q.offer(root);
    Set<Integer> vis = new HashSet<>();
    vis.add(hash(root));
    while (q.size() > 0) {
      int curLevelSize = q.size();
      for (int i = 0; i < curLevelSize; i++) {
        int[][] u = q.poll();
        int[] pair = findZero(u);
        for (int[] direc : DIRECS) {
          int x = pair[0] + direc[0];
          int y = pair[1] + direc[1];
          if (x >= 0 && x < M && y >= 0 && y < N) {
            int[][] v = swap(u, pair[0], pair[1], x, y);
            int hashV = hash(v);
            if (!vis.contains(hashV)) {
              if (hashV == hashTarget) {
                return "YES";
              }
              q.offer(v);
              vis.add(hashV);
            }
          }
        }
      }
    }
    return "NO";
  }

  private int[][] swap(int[][] u, int zeroX, int zeroY, int swapingX, int swapingY) {
    int[][] v = new int[M][N];
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        v[i][j] = u[i][j];
      }
    }
    v[zeroX][zeroY] = u[swapingX][swapingY];
    v[swapingX][swapingY] = u[zeroX][zeroY];
    return v;
  }

  private int[] findZero(int[][] mat) {
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        if (mat[i][j] == 0) {
          return new int[]{i, j};
        }
      }
    }
    assert true;
    return null;
  }

  private int hash(int[][] mat) {
    int hash = 0;
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        hash = hash * 10 + mat[i][j];
      }
    }
    return hash;
  }
}