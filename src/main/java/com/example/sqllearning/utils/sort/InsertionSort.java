package com.example.sqllearning.utils.sort;

/**
 * @description: 插入排序，将第 i 个元素插入到前 i-1 个有序的数组中
 * @author: hulei
 * @create: 2020-06-13 01:28:57
 */
public class InsertionSort {
  public static void sort(int[] source) {
    for (int i = 1; i < source.length; i++) {
      for (int j = i; j >= 1; j--) {
        if (source[j] < source[j - 1]) {
          SortUtil.swapNode(source, j, j - 1);
        }
      }
    }
  }
}
