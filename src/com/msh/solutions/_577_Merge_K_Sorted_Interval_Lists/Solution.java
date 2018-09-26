package com.msh.solutions._577_Merge_K_Sorted_Interval_Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by monkeysayhi on 2018/9/26.
 */

class Interval {
  int start, end;

  Interval(int start, int end) {
    this.start = start;
    this.end = end;
  }
}


public class Solution {
  private static class Info implements Comparable<Info> {
    private Interval itv;
    private int idx1;
    private int idx2;

    private Info(Interval itv, int idx1, int idx2) {
      this.itv = itv;
      this.idx1 = idx1;
      this.idx2 = idx2;
    }

    public int compareTo(Info i) {
      if (this.itv.start == i.itv.start) {
        return this.itv.end - i.itv.end;
      }
      return this.itv.start - i.itv.start;
    }
  }

  /**
   * @param intervals: the given k sorted interval lists
   * @return: the new sorted interval list
   */
  // solution 1: 最小堆，按照 Interval.start 排序，然后取堆顶，正常扫描即可
  public List<Interval> mergeKSortedIntervalLists(List<List<Interval>> intervals) {
    // assume valid
    if (intervals == null || intervals.size() == 0) {
      return new ArrayList<>();
    }

    PriorityQueue<Info> minHeap = new PriorityQueue<>();
    int k = 0;
    int size = 0;
    for (int i = 0; i < intervals.size(); i++) {
      if (intervals.get(i).size() > 0) {
        minHeap.offer(new Info(intervals.get(i).get(0), i, 0));
        k++;
        size += intervals.get(i).size();
      }
    }

    List<Interval> rs = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      Info cur = minHeap.poll();
      if (rs.size() == 0) {
        rs.add(new Interval(cur.itv.start, cur.itv.end));
      } else {
        Interval lastItv = rs.get(rs.size() - 1);
        if (isOverlap(lastItv, cur.itv)) {
          lastItv.end = Math.max(lastItv.end, cur.itv.end);
        } else {
          rs.add(new Interval(cur.itv.start, cur.itv.end));
        }
      }
      if (cur.idx2 < intervals.get(cur.idx1).size() - 1) {
        minHeap.offer(new Info(
            intervals.get(cur.idx1).get(cur.idx2 + 1), cur.idx1, cur.idx2 + 1
        ));
      }
    }
    return rs;
  }

  private boolean isOverlap(Interval l, Interval r) {
    return l.end >= r.start;
  }

  public static void main(String[] args) {
    List<List<Interval>> in = new ArrayList<>();
    {
      List<Interval> l = new ArrayList<>();
      l.add(new Interval(1, 3));
      l.add(new Interval(4, 7));
      l.add(new Interval(6, 8));
      in.add(l);
    }
    {
      List<Interval> l = new ArrayList<>();
      l.add(new Interval(1, 2));
      l.add(new Interval(9, 10));
      in.add(l);
    }

    List<Interval> rs = new Solution().mergeKSortedIntervalLists(in);

    for (Interval r : rs) {
      System.out.println(String.format("[%d, %d]", r.start, r.end));
    }
  }
}