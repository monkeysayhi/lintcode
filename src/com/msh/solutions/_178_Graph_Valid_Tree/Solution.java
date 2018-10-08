package com.msh.solutions._178_Graph_Valid_Tree;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by monkeysayhi on 2018/10/8.
 */
public class Solution {
  static enum Status {
    NONE, VISITING, VISITED
  }
  /**
   * @param n: An integer
   * @param edges: a list of undirected edges
   * @return: true if it's a valid tree, or false
   */
  // 树 = 无向图 + 2{连通， 无环， n = e + 1}

  // solution 1: n = e + 1 && 无环
  public boolean validTree(int n, int[][] edges) {
    // assume valid
    int m = edges.length;
    if (n != m + 1) {
      return false;
    }

    Set<Integer>[] graph = constructGraph(n, edges);
    Status[] vis = new Status[n];
    for (int u = 0; u < n; u++) {
      vis[u] = Status.NONE;
    }
    for (int u = 0; u < n; u++) {
      if (vis[u] == Status.NONE) {
        if (!dfsWuhuan(graph, vis, u)) {
          return false;
        }
      }
    }
    return true;
  }

  private Set<Integer>[] constructGraph(int n, int[][] edges) {
    Set<Integer>[] graph = new Set[n];
    for (int u = 0; u < n; u++) {
      graph[u] = new HashSet<>();
    }
    for (int[] edge : edges) {
      int u = edge[0];
      int v = edge[1];
      graph[u].add(v);
      graph[v].add(u);
    }
    return graph;
  }

  private boolean dfsWuhuan(Set<Integer>[] graph, Status[] vis, int u) {
    assert vis[u] == Status.NONE;
    vis[u] = Status.VISITING;
    for (int v : graph[u]) {
      graph[v].remove(u);
      if (vis[v] == Status.VISITING) {
        return false;
      }
      if (vis[v] == Status.NONE) {
        if (!dfsWuhuan(graph, vis, v)) {
          return false;
        }
      }
    }
    vis[u] = Status.VISITED;
    return true;
  }

  // // solution 1: n = e + 1 && 连通
  // public boolean validTree(int n, int[][] edges) {
  //     // assume valid
  //     int m = edges.length;
  //     if (n != m + 1) {
  //         return false;
  //     }

  //     Set<Integer>[] graph = constructGraph(n, edges);
  //     boolean[] vis = new boolean[n];
  //     dfs(graph, vis, 0);
  //     for (int u = 0; u < n; u++) {
  //         if (!vis[u]) {
  //             return false;
  //         }
  //     }
  //     return true;
  // }

  // private Set<Integer>[] constructGraph(int n, int[][] edges) {
  //     Set<Integer>[] graph = new Set[n];
  //     for (int u = 0; u < n; u++) {
  //         graph[u] = new HashSet<>();
  //     }
  //     for (int[] edge : edges) {
  //         int u = edge[0];
  //         int v = edge[1];
  //         graph[u].add(v);
  //         graph[v].add(u);
  //     }
  //     return graph;
  // }

  // private void dfs(Set<Integer>[] graph, boolean[] vis, int u) {
  //     vis[u] = true;
  //     for (int v : graph[u]) {
  //         if (!vis[v]) {
  //             dfs(graph, vis, v);
  //         }
  //     }
  // }
}