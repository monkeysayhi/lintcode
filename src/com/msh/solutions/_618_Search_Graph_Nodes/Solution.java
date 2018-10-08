package com.msh.solutions._618_Search_Graph_Nodes;

import java.util.*;

/**
 * Created by monkeysayhi on 2018/10/8.
 */

class UndirectedGraphNode {
    int label;
    ArrayList<UndirectedGraphNode> neighbors;
    UndirectedGraphNode(int x) {
        label = x; neighbors = new ArrayList<UndirectedGraphNode>();
    }
};

public class Solution {
  /**
   * @param graph a list of Undirected graph node
   * @param values a hash mapping, <UndirectedGraphNode, (int)value>
   * @param node an Undirected graph node
   * @param target an integer
   * @return the a node
   */
  public UndirectedGraphNode searchNode(ArrayList<UndirectedGraphNode> graph,
                                        Map<UndirectedGraphNode, Integer> values,
                                        UndirectedGraphNode node,
                                        int target) {
    // Write your code here
    return bfs(graph, values, node, target);
  }

  private UndirectedGraphNode bfs(ArrayList<UndirectedGraphNode> graph,
                                  Map<UndirectedGraphNode, Integer> values,
                                  UndirectedGraphNode startNode,
                                  int targetVal) {
    if (graph == null || startNode == null || values == null
        || !graph.contains(startNode)) {
      return null;
    }

    Map<UndirectedGraphNode, Boolean> vis = new HashMap<>(values.size());
    Queue<UndirectedGraphNode> queue = new LinkedList<>();
    queue.offer(startNode);
    vis.put(startNode, true);
    while (!queue.isEmpty()) {
      UndirectedGraphNode node = queue.poll();
      // visit node
      if (values.get(node) != null
          && values.get(node) == targetVal) {
        return node;
      }
      for (UndirectedGraphNode neighbor : node.neighbors) {
        if (vis.get(neighbor) == null || vis.get(neighbor) == false) {
          queue.offer(neighbor);
          vis.put(neighbor, true);
        }
      }
    }

    return null;
  }
}