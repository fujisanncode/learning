package ink.fujisann.learning.base.utils.sort;

/**
 * @description: 选择排序,从后n-i个元素中选择最小的放到i位置
 * @author: hulei
 * @create: 2020-06-13 01:28:13
 */
public class SelectSort {
  public static void sort(int[] source) {
    for (int i = 0; i < source.length - 1; i++) {
      for (int j = i + 1; j < source.length; j++) {
        if (source[j] < source[i]) {
          SortUtil.swapNode(source, i, j);
        }
      }
    }
  }
}
