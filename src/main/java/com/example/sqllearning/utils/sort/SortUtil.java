package com.example.sqllearning.utils.sort;

/**
 * @description: 排序工具
 * @author: hulei
 * @create: 2020-06-17 00:00:49
 */
public class SortUtil {
  // 交换数组两个位置的值
  public static void swapNode(int[] source, int fIndex, int lIndex) {
    int fValue = source[fIndex];
    source[fIndex] = source[lIndex];
    source[lIndex] = fValue;
  }
}
