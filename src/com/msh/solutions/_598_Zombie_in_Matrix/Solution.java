package com.msh.solutions._598_Zombie_in_Matrix;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by monkeysayhi on 2018/10/8.
 */
public class Solution {
  private class Point {
    public int x;
    public int y;
    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
  /**
   * @param grid  a 2D integer grid
   * @return an integer
   */
  public int zombie(int[][] grid) {
    // Write your code here

    if (grid == null || grid.length == 0) {
      return 0;
    }
    if (grid[0] == null || grid[0].length == 0) {
      return 0;
    }

    int days = bfs(grid);

    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[0].length; y++) {
        if (grid[x][y]  == 0) {
          return -1;
        }
      }
    }
    return days;
  }

  private int bfs(int[][] grid) {
    Queue<Point> queue = new LinkedList<>();
    // 0代僵尸作为第一层节点
    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[0].length; y++) {
        if (grid[x][y]  == 1) {
          queue.offer(new Point(x, y));
        }
      }
    }
    int days = -1;
    while (!queue.isEmpty()) {
      int curLevelSize = queue.size();
      for (int i = 0; i < curLevelSize; i++) {
        Point point = queue.poll();
        int x = point.x;
        int y = point.y;
        if (x > 0 && grid[x - 1][y] == 0) {
          grid[x - 1][y] = 1;
          queue.offer(new Point(x - 1, y));
        }
        if (x < grid.length - 1 && grid[x + 1][y] == 0) {
          grid[x + 1][y] = 1;
          queue.offer(new Point(x + 1, y));
        }
        if (y > 0 && grid[x][y - 1] == 0) {
          grid[x][y - 1] = 1;
          queue.offer(new Point(x, y - 1));
        }
        if (y < grid[0].length - 1 && grid[x][y + 1] == 0) {
          grid[x][y + 1] = 1;
          queue.offer(new Point(x, y + 1));
        }
      }
      days++;
    }
    return days;
  }
}