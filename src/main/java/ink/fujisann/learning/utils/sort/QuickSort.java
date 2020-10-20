package ink.fujisann.learning.utils.sort;

/**
 * @description: 快速排序: 数组内交换元素，只需要o(1), 即固定常数各内存记录基数和指针位置
 * @author: hulei
 * @create: 2020-06-13 10:50:02
 */
public class QuickSort {
  public static void sorted(int[] source) {
    radixRecursion(source, 0, source.length - 1);
  }

  // 每次递归，找到数组中第一个元素的实际位置
  public static void radixRecursion(int[] source, int headIndex, int tailIndex) {
    if (headIndex >= tailIndex) { // 如果数组长度为1，则结束递归
      return;
    }
    int radix = source[headIndex]; // 头指针所谓基数
    int start = headIndex;
    int end = tailIndex;
    while (headIndex < tailIndex) { // 一次循环，指针最后还是指打尾指针
      while (headIndex < tailIndex && source[tailIndex] >= radix) { // 尾指针处值大于基数，不需要调整尾值到首处
        tailIndex--;
      }
      source[headIndex] = source[tailIndex]; // 否则交换
      while (headIndex < tailIndex && source[headIndex] < radix) { // 首指针小于基数，不需要调整首值到尾处
        headIndex++;
      }
      source[tailIndex] = source[headIndex]; // 否则交换
    }
    // headIndex 位置需要填入radix
    source[headIndex] = radix;
    radixRecursion(source, start, headIndex - 1); // start ~ index-1
    radixRecursion(source, headIndex + 1, end); // index+1 ~ end
  }
}
