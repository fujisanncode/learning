package ink.fujisann.learning.utils.sort;

import org.junit.Test;

/**
 * @description: 归并排序: 利用分治思想排序
 * @author: hulei
 * @create: 2020-06-12 23:13:58
 */
public class MergerSort {

    // 递归分割数组，分割到最细粒度后归并
    public static void recursionSplit(int[] source) {
        int middle = source.length / 2;
        int[] left = new int[middle];
        for (int i = 0; i < middle; i++) {
            left[i] = source[i];
        }
        int[] right = new int[source.length - middle];
        for (int i = middle; i < source.length; i++) {
            right[i - middle] = source[i];
        }
        // 判断是否继续递归
        if (left.length > 1) {
            recursionSplit(left);
        }
        if (right.length > 1) {
            recursionSplit(right);
        }
        // 递归结束后，归并数组，并赋值给源数组
        int[] mergerResult = merger(left, right);
        for (int i = 0; i < source.length; i++) {
            source[i] = mergerResult[i];
        }
    }

    // left 和right是两个已经排序的数组
    public static int[] merger(int[] left, int[] right) {
        // Arrays.sort(left);
        // Arrays.sort(right);
        int leftLen = left.length;
        int rightLen = right.length;
        int leftIndex = 0;
        int rightIndex = 0;
        int[] returnMerger = new int[leftLen + rightLen];
        int returnMergerIndex = 0;
        // left 和right 对比着插入新数组中
        do {
            returnMerger[returnMergerIndex++] =
                    left[leftIndex] <= right[rightIndex] ? left[leftIndex++] : right[rightIndex++];
        } while (leftIndex < leftLen && rightIndex < rightLen);
        // left 或者right中剩余数据直接拼接到新数组中
        for (int i = leftIndex; i < leftLen; i++) {
            returnMerger[returnMergerIndex++] = left[i];
        }
        for (int i = rightIndex; i < rightLen; i++) {
            returnMerger[returnMergerIndex++] = right[i];
        }
        return returnMerger;
    }

    @Test
    public void testA() {

    }

    private void transfer(int a) {
        a = 2;
    }
}
