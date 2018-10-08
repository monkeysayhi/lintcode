package com.msh.solutions._541_Zigzag_Iterator_II;

import java.util.List;

/**
 * Created by monkeysayhi on 2018/10/8.
 */
// 分别记录listIndex、valIndex
public class ZigzagIterator2 {
  private List<List<Integer>> lists;
  private int listIndex;
  private int valIndex;

  private int cur;
  private final int size;

  /*
  * @param vecs: a list of 1d vectors
  */public ZigzagIterator2(List<List<Integer>> vecs) {
    lists = vecs;
    listIndex = 0;
    valIndex = 0;
    cur = 0;
    size = cntSize();
  }

  private int cntSize() {
    int cnt = 0;
    for (List<Integer> list : lists) {
      cnt += list.size();
    }
    return cnt;
  }

  /*
   * @return: An integer
   */
  public int next() {
    // assume valid
    while (valIndex >= lists.get(listIndex).size()) {
      nextIndex();
    }
    int x = lists.get(listIndex).get(valIndex);
    nextIndex();
    cur++;
    return x;
  }

  private void nextIndex() {
    listIndex++;
    if (listIndex == lists.size()) {
      listIndex = 0;
      valIndex++;
    }
  }

  /*
   * @return: True if has next
   */
  public boolean hasNext() {
    return cur < size;
  }
}

/**
 * Your ZigzagIterator2 object will be instantiated and called as such:
 * ZigzagIterator2 solution = new ZigzagIterator2(vecs);
 * while (solution.hasNext()) result.add(solution.next());
 * Output result
 */