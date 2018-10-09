package com.msh.solutions._129_Rehashing;

/**
 * Created by monkeysayhi on 2018/10/9.
 */

class ListNode {
  int val;
  ListNode next;

  ListNode(int x) {
    val = x;
    next = null;
  }
}

public class Solution {
  /**
   * @param hashTable: A list of The first node of linked list
   * @return: A list of The first node of linked list which have twice size
   */
  public ListNode[] rehashing(ListNode[] hashTable) {
    // write your code here
    if (hashTable == null || hashTable.length == 0) {
      return hashTable;
    }

    int oldCap = hashTable.length;
    int newCap = oldCap * 2;
    ListNode[] newHashTable = new ListNode[newCap];
    for (int i = 0; i < hashTable.length; i++) {
      while (hashTable[i] != null) {
        ListNode node = hashTable[i];
        hashTable[i] = hashTable[i].next;
        int newIndex = indexFor(hashcode(node.val, newCap), newCap);
        // refer to implemention in Java 8, new node always inserted at head
        // node.next = newHashTable[newIndex];
        // newHashTable[newIndex] = node;
        // but in this problem,,, it seems that appending at tail is required
        node.next = null;
        newHashTable[newIndex] = append(newHashTable[newIndex], node);
      }
    }

    return newHashTable;
  }

  private ListNode append(ListNode head, ListNode node) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode tail = dummy;
    while (tail.next != null) {
      tail = tail.next;
    }
    tail.next = node;
    return dummy.next;
  }

  private int indexFor(int hash, int capacity) {
    return (hash % capacity + capacity) % capacity;
  }

  private int hashcode(int key, int capacity) {
    return key % capacity;
  }
}
