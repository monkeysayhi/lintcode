package com.msh.solutions._611_Knight_Shortest_Path;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by monkeysayhi on 2018/10/8.
 */
class Point {
  public int x, y;

  public Point() {
    x = 0;
    y = 0;
  }

  public Point(int a, int b) {
    x = a;
    y = b;
  }
}

public class Solution {
  private static int[][] deltaPoint = new int[][]{
      {1, 2}, {1, -2}, {-1, 2}, {-1, -2},
      {2, 1}, {2, -1}, {-2, 1}, {-2, -1}
  };

  /**
   * @param grid    a chessboard included 0 (false) and 1 (true)
   * @param source, destination a point
   * @return the shortest path
   */
  public int shortestPath(boolean[][] grid, Point source, Point destination) {
    // Write your code here
    if (grid == null || grid.length == 0) {
      return -1;
    }
    if (grid[0] == null || grid[0].length == 0) {
      return -1;
    }
    if (source == null
        || source.x < 0 || source.x > grid.length - 1
        || source.y < 0 || source.y > grid[0].length - 1) {
      return -1;
    }
    if (destination == null
        || destination.x < 0 || destination.x > grid.length - 1
        || destination.y < 0 || destination.y > grid[0].length - 1) {
      return -1;
    }

    return bfs(grid, source, destination);
  }

  private int bfs(boolean[][] grid, Point src, Point dest) {
    Queue<Point> queue = new LinkedList<>();
    queue.offer(src);
    grid[src.x][src.y] = true;
    int len = 0;
    while (!queue.isEmpty()) {
      int curLevelSize = queue.size();
      for (int i = 0; i < curLevelSize; i++) {
        Point point = queue.poll();
        if (point.x == dest.x && point.y == dest.y) {
          return len;
        }
        // 好可怜，，没看到Clarification牺牲了一次WA
        for (int j = 0; j < deltaPoint.length; j++) {
          int x = point.x + deltaPoint[j][0];
          int y = point.y + deltaPoint[j][1];
          if ((x >= 0 && x <= grid.length - 1)
              && (y >= 0 && y <= grid[0].length - 1)
              && !grid[x][y]) {
            queue.offer(new Point(x, y));
            grid[x][y] = true;
          }
        }
      }
      len++;
    }

    return -1;
  }
}