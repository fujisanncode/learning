package ink.fujisann.learning.utils.sort;

/**
 * @description: 冒泡排序
 * @author: hulei
 * @create: 2020-06-13 00:41:46
 */
public class BubbleSort {

    public static void sort(int[] source) {
        for (int i = 0; i < source.length - 1; i++) {
            int swapCount = 0;
            for (int j = 0; j < source.length - i - 1; j++) {
                if (source[j] > source[j + 1]) {
                    source[j] = (source[j + 1] + source[j]) - (source[j + 1] = source[j]);
                    swapCount++;
                }
            }
            if (swapCount == 0) {
                // 如果当前轮次没有发生调换，表示已经排序完成
                return;
            }
        }
        return;
    }
}
