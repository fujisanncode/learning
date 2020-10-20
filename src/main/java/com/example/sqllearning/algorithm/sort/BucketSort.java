package com.example.sqllearning.algorithm.sort;

/**
 * @description: 桶排序 简易桶排序，用桶的空间换取时间，总时间复杂度n+m 实际桶的数量应该减少，每个桶中多个元素
 * @author: hulei
 * @create: 2020-06-08 22:06:19
 */
public class BucketSort implements Sort {

  /**
   * @return int[]
   * @description 总时间复杂度，n+m，即数组长度和桶长度
   * @author hulei
   * @date 2020-06-08 22:33:32
   */
  public void sort(int[] source, int max) {
    int[] bucket = new int[max];
    // 时间复杂度n
    for (int i = 0; i < source.length; i++) {
      bucket[source[i]]++;
    }
    int returnArrayIndex = 0;
    // 时间复杂度m
    for (int i = 0; i < bucket.length; i++) {
      while (bucket[i] > 0) {
        source[returnArrayIndex] = i;
        returnArrayIndex++;
        bucket[i]--;
      }
    }
  }
}
