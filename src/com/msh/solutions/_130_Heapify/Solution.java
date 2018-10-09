package com.msh.solutions._130_Heapify;

/**
 * Created by monkeysayhi on 2018/10/9.
 */
public class Solution {
  /**
   * @param A: Given an integer array
   * @return: void
   */
  // 建堆
  public void heapify(int[] A) {
    int[] nums = A;
    if (nums == null || nums.length <= 1) {
      return;
    }
    buildMinHeap(nums, nums.length);
  }

  private void buildMinHeap(int[] nums, int heapSize) {
    for (int i = heapSize / 2; i >=0 ; i--) {
      minHeapify(nums, heapSize, i);
    }
  }

  private void minHeapify(int[] nums, int heapSize, int root) {
    int min = root;
    while (true) {
      root = min;
      int left = 2 * root + 1;
      int right = 2 * root + 2;
      if (left < heapSize && nums[left] < nums[min]) {
        min = left;
      }
      if (right < heapSize && nums[right] < nums[min]) {
        min = right;
      }
      if (min == root) {
        break;
      }
      swap(nums, min, root);
      root = min;
    }
  }

  private void swap(int[] nums, int i, int j) {
    int tmp = nums[i];
    nums[i] = nums[j];
    nums[j] = tmp;
  }
}