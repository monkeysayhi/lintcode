package com.msh.solutions._892_Alien_Dictionary;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by monkeysayhi on 2018/9/27.
 */
public class Solution {
  /**
   * @param words: a list of words
   * @return: a string which is correct order
   */
  // 按照字典序读取 偏序关系，抽象为图，拓扑排序
  public String alienOrder(String[] words) {
    // assume valid
    Set<Integer> chars = new HashSet<>();
    for (String w : words) {
      for (char c : w.toCharArray()) {
        chars.add(c - 'a');
      }
    }

    Set<Integer>[] g = constructGraph(words, chars);
    int[] indgs = new int[26];
    for (int u = 0; u < 26; u++) {
      if (chars.contains(u)) {
        for (int v : g[u]) {
          indgs[v]++;
        }
      }
    }

    PriorityQueue<Integer> q = new PriorityQueue<>();
    Set<Integer> vis = new HashSet<>();
    for (int u = 0; u < 26; u++) {
      if (chars.contains(u)) {
        if (indgs[u] == 0) {
          q.offer(u);
          vis.add(u);
        }
      }
    }
    StringBuilder sb = new StringBuilder();
    while (q.size() > 0) {
      int u = q.poll();
      sb.append((char) (u + 'a'));
      for (int v : g[u]) {
        indgs[v]--;
        if (indgs[v] == 0) {
          if (!vis.contains(v)) {
            vis.add(v);
            q.offer(v);
          }
        }

      }
    }
    if (sb.length() < chars.size()) {
      return "";
    }
    return sb.toString();
  }

  private Set<Integer>[] constructGraph(String[] ws, Set<Integer> cs) {
    Set<Integer>[] g = new HashSet[26];
    for (int c : cs) {
      g[c] = new HashSet<>();
    }
    for (int i = 1; i < ws.length; i++) {
      char[] w1 = ws[i - 1].toCharArray();
      char[] w2 = ws[i].toCharArray();
      for (int j = 0; j < w1.length && j < w2.length; j++) {
        if (w1[j] != w2[j]) {
          int u = w1[j] - 'a';
          int v = w2[j] - 'a';
          g[u].add(v);
          break;
        }
      }
    }
    return g;
  }
}