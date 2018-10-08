package com.msh.solutions._531_Six_Degrees;

import java.util.*;

/**
 * Created by monkeysayhi on 2018/10/8.
 */

class UndirectedGraphNode {
    int label;
    List<UndirectedGraphNode> neighbors;
    UndirectedGraphNode(int x) {
        label = x;
        neighbors = new ArrayList<UndirectedGraphNode>();
    }
};

public class Solution {
  /**
   * @param graph a list of Undirected graph node
   * @param s, t two Undirected graph nodes
   * @return an integer
   */
  public int sixDegrees(List<UndirectedGraphNode> graph,
                        UndirectedGraphNode s,
                        UndirectedGraphNode t) {
    // Write your code here
    if (graph == null || s == null || t == null) {
      return -1;
    }
    if (s.equals(t)) {
      return 0;
    }

    return bfs(s, t);
  }

  private int bfs(UndirectedGraphNode start,
                  UndirectedGraphNode end) {
    Queue<UndirectedGraphNode> queue = new LinkedList<>();
    Set<UndirectedGraphNode> vis = new HashSet<>();
    queue.offer(start);
    vis.add(start);
    int level = 0;
    while (!queue.isEmpty()) {
      int curLevelSize = queue.size();
      level++;
      for (int i = 0; i < curLevelSize; i++) {
        UndirectedGraphNode u = queue.poll();
        for (UndirectedGraphNode v : u.neighbors) {
          if (!vis.contains(v)) {
            if (v.equals(end)) {
              return level;
            }
            queue.offer(v);
          }
        }
      }
    }
    return -1;
  }
}