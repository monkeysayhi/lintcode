package com.msh.solutions._953_The_Biggest_Score_On_The_Tree;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by monkeysayhi on 2018/9/27.
 */
public class Solution {
  private static class TreeNode {
    private int profit;
    private Map<TreeNode, Integer> children;

    private TreeNode(int p) {
      this.profit = p;
      children = new HashMap<>();
    }
  }

  /**
   * @param x:      The vertex of edge
   * @param y:      The another vertex of edge
   * @param cost:   The cost of edge
   * @param profit: The profit of vertex
   * @return: Return the max score
   */
  // 分治，max
  public int getMaxScore(int[] x, int[] y, int[] cost, int[] profit) {
    // assume valid
    TreeNode root = buildTree(x, y, cost, profit);
    return getMaxScoreInt(root);
  }

  private int getMaxScoreInt(TreeNode root) {
    if (root == null) {
      return 0;
    }
    if (root.children.size() == 0) {
      return root.profit;
    }
    int maxChildP = Integer.MIN_VALUE;
    for (TreeNode child : root.children.keySet()) {
      int childP = getMaxScoreInt(child);
      int cost = root.children.get(child);
      maxChildP = Math.max(maxChildP, childP - cost);
    }
    return maxChildP + root.profit;
  }

  private TreeNode buildTree(int[] xs, int[] ys, int[] cs, int[] ps) {
    Map<Integer, TreeNode> nodes = new HashMap<>();
    for (int i = 0; i < xs.length; i++) {
      int u = xs[i];
      int v = ys[i];
      if (!nodes.containsKey(u)) {
        nodes.put(u, new TreeNode(ps[u]));
      }
      TreeNode uNode = nodes.get(u);
      if (!nodes.containsKey(v)) {
        nodes.put(v, new TreeNode(ps[v]));
      }
      TreeNode vNode = nodes.get(v);
      if (!uNode.children.containsKey(vNode)) {
        uNode.children.put(vNode, cs[i]);
      }
    }
    return nodes.get(0);
  }
}