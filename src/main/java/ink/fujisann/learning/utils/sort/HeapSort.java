package ink.fujisann.learning.utils.sort;

import ink.fujisann.learning.algorithm.tree.TreeNode;
import ink.fujisann.learning.algorithm.tree.TreeOperation;
import ink.fujisann.learning.algorithm.tree.TreeUtil;

/**
 * @description: 堆排序:构建大顶堆 -> 首尾交换 -> 大顶堆调整 -> 重复2、3
 * @author: hulei
 * @create: 2020-06-15 22:11:35
 */
public class HeapSort {
  /**
   * @description 数组按照大顶堆构造顺序，然后排序
   * @param source
   * @author hulei
   * @date 2020-06-16 23:17:06
   */
  public static void sort(int[] source) {
    buildMaxHead(source, source.length); // 构建大顶堆
    // TreeOperation.show(ConstructTree.constructTree(source)); // 数组桉树打印
    for (int lastIndex = source.length - 1; lastIndex >= 1; lastIndex--) { // 最大值调整到尾节点，重新调整大顶堆；重复
      SortUtil.swapNode(source, 0, lastIndex); // 交换最大值(首节点)和尾元素
      adjustHeap(0, source, lastIndex - 1); // 首节点值调换后，需要重新调整堆(因为首节点不一定比左右子节点大)
      // TreeOperation.show(ConstructTree.constructTree(source)); // 数组桉树打印
    }
  }

  // 构建大顶堆
  private static void buildMaxHead(int[] source, int length) {
    // 数组序号从0开始, 最大一个非叶子节点为 (length/2 -1), length为节点数量
    for (int rootIndex = length / 2 - 1; rootIndex >= 0; rootIndex--) {
      adjustHeap(rootIndex, source, source.length - 1);
    }
  }

  // 将第i个节点，和其左右子节点交换，最大者调整至i位置
  private static void adjustHeap(int rootIndex, int[] source, int maxIndex) {
    int lIndex = 2 * rootIndex + 1; // 左子节点序号
    int rIndex = 2 * rootIndex + 2; // 右子节点序号
    int largestIndex = rootIndex; // 先假定根节点是三个节点中最大
    if (lIndex <= maxIndex
        && source[lIndex] > source[largestIndex]) { // 如果左子节点大，赋值给最大节点序号; 子节点序号不超过最大序号
      largestIndex = lIndex;
    }
    if (rIndex <= maxIndex
        && source[rIndex] > source[largestIndex]) { // 如果右子节点大，赋值给最大节点序号; 子节点序号不超过最大序号
      largestIndex = rIndex;
    }
    // 如果最大值不是父节点，需要调换节点，并递归调整子层节点；如果是父节点，则其左右子节点值不变，不需要调整
    if (largestIndex != rootIndex) {
      SortUtil.swapNode(source, rootIndex, largestIndex); // 交换rootIndex和largestIndex的值
      // 因为rootIndex的值被调整到左子节点(或者右子节点)，所以左子节点的值不一定大于其子节点，需要递归调整；右子节点值没变不用动
      adjustHeap(largestIndex, source, maxIndex);
    }
  }

  /**
   * @description 递归树，将最大值调整到顶部
   * @author hulei
   * @date 2020-06-16 23:16:42
   */
  public static void sort4Tree(Integer[] source) {
    TreeNode root = TreeUtil.generatorTree(source);
    TreeOperation.show(root);
    maxHeap(root);
    TreeOperation.show(root);
  }

  // 二叉数中最大值调整到顶部
  private static void maxHeap(TreeNode root) {
    TreeNode left = root.left;
    TreeNode right = root.right;
    if (left != null) {
      maxHeap(left);
    }
    if (right != null) {
      maxHeap(right);
    }
    if (left != null && left.val > root.val) {
      int tmp = left.val;
      left.val = root.val;
      root.val = tmp;
    }
    if (right != null && right.val > root.val) {
      int tmp = right.val;
      right.val = root.val;
      root.val = tmp;
    }
  }
}
