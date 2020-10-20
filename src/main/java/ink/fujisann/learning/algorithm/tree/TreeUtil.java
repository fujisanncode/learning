package ink.fujisann.learning.algorithm.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 二叉树工具
 * @author: hulei
 * @create: 2020-06-15 23:01:23
 */
public class TreeUtil {
  public static void reverse(TreeNode treeNode) {
    swapNodeRecursion(treeNode);
  }

  // 递归交换节点
  private static void swapNodeRecursion(TreeNode treeNode) {
    TreeNode tmp = treeNode.left;
    treeNode.left = treeNode.right;
    treeNode.right = tmp;
    if (treeNode.left != null) {
      swapNodeRecursion(treeNode.left);
    }
    if (treeNode.right != null) {
      swapNodeRecursion(treeNode.right);
    }
  }

  public static TreeNode generatorTree(Integer[] source) {
    TreeNode root = new TreeNode(source[0]);
    addNodeRecursion(
        new ArrayList() {
          {
            add(root);
          }
        },
        1,
        source);
    return root;
  }

  private static void addNodeRecursion(
      List<TreeNode> treeNodeList, int levelStart, Integer[] source) {
    List<TreeNode> newLevel = new ArrayList<>();
    for (TreeNode treeNode : treeNodeList) {
      treeNode.left = new TreeNode(source[levelStart++]);
      newLevel.add(treeNode.left);
      if (levelStart >= source.length) {
        return;
      }
      treeNode.right = new TreeNode(source[levelStart++]);
      newLevel.add(treeNode.right);
      if (levelStart >= source.length) {
        return;
      }
    }
    addNodeRecursion(newLevel, levelStart, source);
  }
}
