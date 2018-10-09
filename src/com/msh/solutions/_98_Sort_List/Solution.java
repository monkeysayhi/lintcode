package com.msh.solutions._98_Sort_List;

/**
 * Created by monkeysayhi on 2018/10/9.
 */

class ListNode {
    int val;
    ListNode next;
    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

public class Solution {
  /**
   * @param head: The head of linked list.
   * @return: You should return the head of the sorted linked list,
   * using constant space complexity.
   */
  public ListNode sortList(ListNode head) {
    // write your code here
    // solution 1, merge sort
    return mergeSort(head);
    // // solution 2, quick sort
    // return quickSort(head);
    // // solution 3, 3-seg quick sort
    // return quickSort3Segs(head);
  }

  private ListNode quickSort3Segs(ListNode head) {
    if (head == null) {
      return null;
    }
    if (head.next == null) {
      return head;
    }

    // for badcase：基本有序
    head = randomTuneHead(head);
    // for badcase：存在大量相同元素
    ListNode[] nodes = partition3Segs(head);
    ListNode newHead = nodes[0];
    ListNode lt = nodes[1];
    ListNode eq = nodes[2];

    if (lt == null) {
      eq.next = quickSort(eq.next);
      return newHead;
    }

    eq.next = quickSort(eq.next);

    ListNode midStart = lt.next;
    lt.next = null;
    ListNode left = quickSort(newHead);
    getTail(left).next = midStart;

    return left;
  }

  private ListNode quickSort(ListNode head) {
    if (head == null) {
      return null;
    }
    if (head.next == null) {
      return head;
    }

    head = randomTuneHead(head);
    ListNode[] nodes = partition(head);
    ListNode newHead = nodes[0];
    ListNode lt = nodes[1];

    if (lt == null) {
      newHead.next = quickSort(newHead.next);
      return newHead;
    }

    ListNode mid = lt.next;
    ListNode right = quickSort(mid.next);
    mid.next = right;
    lt.next = null;
    ListNode left = quickSort(newHead);
    getTail(left).next = mid;

    return left;
  }

  private ListNode getTail(ListNode head) {
    while (head.next != null) {
      head = head.next;
    }
    return head;
  }

  private ListNode randomTuneHead(ListNode head) {
    if (head == null) {
      return null;
    }
    if (head.next == null) {
      return head;
    }

    ListNode prevMid = randomChoosePrevMid(head);
    if (prevMid != null && prevMid.next != null) {
      ListNode mid = prevMid.next;
      prevMid.next = mid.next;
      mid.next = head;
      head = mid;
    }

    return head;
  }

  private ListNode randomChoosePrevMid(ListNode head) {
    if (head == null) {
      return null;
    }

    int len = 0;
    for (ListNode p = head; p != head; p = p.next) {
      len++;
    }
    int midPos = (int)(Math.random() * len);

    ListNode prevMid = null;
    ListNode mid = head;
    for (int i = 0; i < midPos && prevMid.next != null; i++) {
      if (prevMid == null) {
        prevMid = head;
      } else {
        prevMid = prevMid.next;
      }
      mid = prevMid.next;
    }
    return prevMid;
  }

  private ListNode[] partition3Segs(ListNode head) {
    if (head == null) {
      return null;
    }
    if (head.next == null) {
      return new ListNode[]{head, null, head};
    }

    ListNode lt = head;
    ListNode eq = head;
    ListNode gt = head;
    ListNode cur = head.next;
    while (cur != null) {
      if (cur.val == head.val) {
        if (eq == gt) {
          eq = eq.next;
          gt = gt.next;
        } else {
          gt.next = cur.next;
          cur.next = eq.next;
          eq.next = cur;

          eq = eq.next;
        }
      } else if (cur.val < head.val) {
        if (lt == gt) {
          lt = lt.next;
          eq = eq.next;
          gt = gt.next;
        } else if (eq == lt) {
          gt.next = cur.next;
          cur.next = lt.next;
          lt.next = cur;

          lt = lt.next;
          eq = eq.next;
        } else {
          gt.next = cur.next;
          cur.next = lt.next;
          lt.next = cur;

          lt = lt.next;
        }
      } else {
        gt = gt.next;
      }
      cur = gt.next;
    }

    if (lt == head) {
      return new ListNode[]{head, null, eq};
    }

    ListNode newHead = head.next;

    if (eq == lt) {
      head.next = lt.next;
      lt.next = head;

      eq = eq.next;
      return new ListNode[]{newHead, lt, eq};
    }

    head.next = lt.next;
    lt.next = head;
    return new ListNode[]{newHead, lt, eq};
  }

  private ListNode[] partition(ListNode head) {
    if (head == null) {
      return null;
    }
    if (head.next == null) {
      return new ListNode[]{head, null};
    }

    ListNode lt = head;
    ListNode ge = head;
    ListNode cur = head.next;
    while (cur != null) {
      if (cur.val < head.val) {
        if (lt == ge) {
          lt = lt.next;
          ge = ge.next;
        } else {
          ge.next = cur.next;
          cur.next = lt.next;
          lt.next = cur;
          lt = lt.next;
        }
      } else {
        ge = ge.next;
      }
      cur = ge.next;
    }

    if (lt == head) {
      return new ListNode[]{head, null};
    }

    ListNode newHead = head.next;
    head.next = lt.next;
    lt.next = head;
    return new ListNode[]{newHead, lt};
  }

  private ListNode mergeSort(ListNode head) {
    if (head == null) {
      return null;
    }
    if (head.next == null) {
      return head;
    }

    ListNode mid = findMiddle(head);
    ListNode right = mergeSort(mid.next);
    mid.next = null;
    ListNode left = mergeSort(head);

    return merge2SortedLists(left, right);
  }

  private ListNode merge2SortedLists(ListNode head1, ListNode head2) {
    if (head1 == null && head2 == null) {
      return null;
    }
    if (head1 == null) {
      return head2;
    }
    if (head2 == null) {
      return head1;
    }

    ListNode head = null;
    ListNode tail = null;
    ListNode p1 = head1;
    ListNode p2 = head2;
    while (p1 != null && p2 != null) {
      if (p1.val <= p2.val) {
        if (head == null || tail == null) {
          head = p1;
          tail = p1;
        } else {
          tail.next = p1;
          tail = tail.next;
        }
        ListNode next = p1.next;
        p1.next = null;
        p1 = next;
      } else {
        if (head == null || tail == null) {
          head = p2;
          tail = p2;
        } else {
          tail.next = p2;
          tail = tail.next;
        }
        ListNode next = p2.next;
        p2.next = null;
        p2 = next;
      }
    }
    if (p1 != null) {
      tail.next = p1;
    }
    if (p2 != null) {
      tail.next = p2;
    }

    return head;
  }

  // return the mid whose val le the val of real mid
  private ListNode findMiddle(ListNode head) {
    if (head == null) {
      return null;
    }

    ListNode slowItr = head;
    ListNode fastItr = head.next; // important!!
    while (fastItr != null && fastItr.next != null) {
      slowItr = slowItr.next;
      fastItr = fastItr.next.next;
    }
    return slowItr;
  }
}

// public class Solution {
//     public ListNode sortList(ListNode head) {
//         if (head == null || head.next == null) {
//             return head;
//         }

//         ListNode mid = findMedian(head); // O(n)

//         ListNode leftDummy = new ListNode(0), leftTail = leftDummy;
//         ListNode rightDummy = new ListNode(0), rightTail = rightDummy;
//         ListNode middleDummy = new ListNode(0), middleTail = middleDummy;
//         while (head != null) {
//             if (head.val < mid.val) {
//                 leftTail.next = head;
//                 leftTail = head;
//             } else if (head.val > mid.val) {
//                 rightTail.next = head;
//                 rightTail = head;
//             } else {
//                 middleTail.next = head;
//                 middleTail = head;
//             }
//             head = head.next;
//         }

//         leftTail.next = null;
//         middleTail.next = null;
//         rightTail.next = null;

//         ListNode left = sortList(leftDummy.next);
//         ListNode right = sortList(rightDummy.next);

//         return concat(left, middleDummy.next, right);
//     }

//     private ListNode findMedian(ListNode head) {
//         ListNode slow = head, fast = head.next;
//         while (fast != null && fast.next != null) {
//             slow = slow.next;
//             fast = fast.next.next;
//         }
//         return slow;
//     }

//     private ListNode concat(ListNode left, ListNode middle, ListNode right) {
//         ListNode dummy = new ListNode(0), tail = dummy;

//         tail.next = left; tail = getTail(tail);
//         tail.next = middle; tail = getTail(tail);
//         tail.next = right; tail = getTail(tail);
//         return dummy.next;
//     }

//     private ListNode getTail(ListNode head) {
//         if (head == null) {
//           return null;
//         }

//         while (head.next != null) {
//             head = head.next;
//         }
//         return head;
//     }
// }

