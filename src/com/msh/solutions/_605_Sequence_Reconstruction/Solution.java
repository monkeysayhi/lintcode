package com.msh.solutions._605_Sequence_Reconstruction;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by monkeysayhi on 2018/9/26.
 */
public class Solution {
  /**
   * @param org:  a permutation of the integers from 1 to n
   * @param seqs: a list of sequences
   * @return: true if it can be reconstructed only one or false
   */
  // 看(题解)[https://www.jiuzhang.com/solution/sequence-reconstruction/]
  // seqs[i] 描述了不同元素的偏序关系，所有 seq 构成了一个有向图 g，那么只需要证明“org 是有向图 g 的唯一拓扑排序”
  // 即，每一时刻，只存在一个 入度为 0 的节点，且等于 org[i]
  public boolean sequenceReconstruction(int[] org, int[][] seqs) {
    // assume valid
    int n = org.length;
    Set<Integer>[] g = constructGraph(n, seqs);
    if (g == null) {
      return false;
    }
    int[] indgs = new int[n + 1];
    for (int u = 1; u <= n; u++) {
      for (int v : g[u]) {
        indgs[v]++;
      }
    }

    Queue<Integer> q = new LinkedList<>();
    for (int u = 1; u <= n; u++) {
      if (indgs[u] == 0) {
        q.offer(u);
      }
    }
    for (int i = 0; i < n; i++) {
      if (q.size() != 1) {
        return false;
      }
      int u = q.poll();
      if (u != org[i]) {
        return false;
      }
      for (int v : g[u]) {
        indgs[v]--;
        if (indgs[v] == 0) {
          q.offer(v);
        }
      }
    }
    return true;
  }

  private Set<Integer>[] constructGraph(int n, int[][] seqs) {
    Set<Integer>[] g = new HashSet[n + 1];
    for (int u = 1; u <= n; u++) {
      g[u] = new HashSet<>();
    }
    Set<Integer> set = new HashSet<>();
    for (int[] seq : seqs) {
      if (seq == null || seq.length == 0) {
        continue;
      }
      if (seq[0] < 1 || seq[0] > n) {
        return null;
      }
      set.add(seq[0]);
      for (int i = 1; i < seq.length; i++) {
        if (seq[i] < 1 || seq[i] > n) {
          return null;
        }
        set.add(seq[i]);
        int u = seq[i - 1];
        int v = seq[i];
        g[u].add(v);
      }
    }
    if (set.size() < n) {
      return null;
    }
    return g;
  }
}