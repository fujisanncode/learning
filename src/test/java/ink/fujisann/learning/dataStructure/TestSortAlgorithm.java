package ink.fujisann.learning.dataStructure;


import ink.fujisann.learning.base.algorithm.GeneratorArray;
import ink.fujisann.learning.base.algorithm.sort.BucketSort;
import ink.fujisann.learning.base.algorithm.tree.TreeNode;
import ink.fujisann.learning.base.algorithm.tree.TreeOperation;
import ink.fujisann.learning.base.algorithm.tree.TreeUtil;
import ink.fujisann.learning.base.utils.sort.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 测试排序算法
 * @author: hulei
 * @create: 2020-06-14 15:22:05
 */
@Slf4j
public class TestSortAlgorithm {

  private int[] testArr;

  @Before
  public void initTestArr() {
    testArr = new int[] {1, 3, 2, 6, 3, 7, 8};
  }

  /**
   * @description 1、测试归并排序的正确性
   * @return void
   * @author hulei
   * @date 2020-06-14 15:00:22
   */
  @Test
  public void testMergerSort() {
    int[] source = new int[] {1, 3, 5, 7, 8, 2, 3, 6, 8, 9};
    MergerSort.recursionSplit(source);
    log.info(Arrays.toString(source));
    Assert.assertEquals(2, source[1]);
  }

  /**
   * @description 2、测试冒泡排序的正确性
   * @return void
   * @author hulei
   * @date 2020-06-14 15:01:51
   */
  @Test
  public void testBubbleSort() {
    int[] source1 = new int[] {1, 3, 5, 7, 8, 2, 3, 6, 8, 9};
    BubbleSort.sort(source1);
    log.info(Arrays.toString(source1));
  }

  /**
   * @description 3、测试快速排序的正确性
   * @return void
   * @author hulei
   * @date 2020-06-14 15:06:04
   */
  @Test
  public void testQuickSort() {
    int[] source2 = new int[] {7, 2, 6, 3, 8};
    QuickSort.sorted(source2);
    log.info(Arrays.toString(source2));
    Assert.assertArrayEquals(new int[] {2, 3}, new int[] {source2[0], source2[1]});
  }

  /**
   * @description 测试快速排序耗时
   * @return void
   * @author hulei
   * @date 2020-06-14 22:15:58
   */
  @Test
  public void testQuickSortCost() {
    int len = 100000;
    int[] source2 = GeneratorArray.get(len, 0, len + 1);
    QuickSort.sorted(source2);
    // QuickSort3.quickSort(source2, 0, source2.length - 1);
    log.info(
        Arrays.toString(
            new int[] {
              source2[0], source2[1], source2[source2.length - 2], source2[source2.length - 1]
            }));
    // Assert.assertArrayEquals(new int[] {2, 3}, new int[] {source2[0], source2[1]});
  }

  /**
   * @description 4、测试桶排序的正确性
   * @return void
   * @author hulei
   * @date 2020-06-14 15:06:55
   */
  @Test
  public void testBucketSort() {
    int[] source2 = new int[] {7, 3, 2, 2, 6, 8, 7, 3, 8};
    new BucketSort().sort(source2, 10);
    log.info(Arrays.toString(source2));
  }

  /**
   * @description 打印二叉树
   * @return void
   * @author hulei
   * @date 2020-06-15 22:48:38
   */
  @Test
  public void printTree() {
    TreeNode treeNode = TreeUtil.generatorTree(new Integer[] {1, 3, 2, 6, 3, 7, 8});
    List<String> returnListLeft = TreeOperation.show(treeNode);
    TreeUtil.reverse(treeNode);
    List<String> returnListRight = TreeOperation.show(treeNode);
    for (int i = 0; i < returnListLeft.size(); i++) {
      log.info(returnListLeft.get(i).concat("   |   ").concat(returnListRight.get(i)));
    }
  }

  /**
   * @description 测试堆排序
   * @return void
   * @author hulei
   * @date 2020-06-16 01:06:07
   */
  @Test
  public void testHeapSort() {
    int[] source = new int[] {1, 3, 2, 6, 3, 7, 8};
    HeapSort.sort(source);
    log.info(Arrays.toString(source));
  }

  /**
   * @description 测试选择排序
   * @author hulei
   * @date 2020-06-17 00:04:02
   */
  @Test
  public void testSelectSort() {
    SelectSort.sort(testArr);
    log.info(Arrays.toString(testArr));
  }

  /**
   * @description 测试希尔排序
   * @author hulei
   * @date 2020-06-17 22:23:40
   */
  @Test
  public void testShellSort() {
    // ShellSort.sort(testArr, ShellSort.getIncrementSeries(ShellSort.shell, testArr.length));
    ShellSort.sort(testArr, ShellSort.getIncrementSeries(ShellSort.hibbard, testArr.length));
    log.info(Arrays.toString(testArr));
  }

  /**
   * @description 测试插入排序
   * @author hulei
   * @date 2020-06-17 00:25:59
   */
  @Test
  public void testInsertionSort() {
    InsertionSort.sort(testArr);
    log.info(Arrays.toString(testArr));
  }

  /**
   * @description 测试各种排序的性能
   * @return void
   * @author hulei
   * @date 2020-06-14 15:16:20
   */
  @Test
  public void testSortCost() {
    int len = 70000;
    int max = len + 1;
    int[] generatorArray1 = GeneratorArray.get(len, 1, max);
    int[] generatorArray2 = Arrays.copyOf(generatorArray1, generatorArray1.length);
    int[] generatorArray3 = Arrays.copyOf(generatorArray1, generatorArray1.length);
    int[] generatorArray4 =
        Arrays.copyOfRange(generatorArray1, 0, generatorArray1.length); // 从0到length-1,不包含length序号
    int[] generatorArray5 = generatorArray1.clone(); // clone对于对象中的对对象会浅拷贝(拷贝引用)
    int[] generatorArray6 = Arrays.copyOf(generatorArray1, generatorArray1.length);
    int[] generatorArray7 = Arrays.copyOf(generatorArray1, generatorArray1.length);
    int[] generatorArray8 = Arrays.copyOf(generatorArray1, generatorArray1.length);
    int[] generatorArray9 = Arrays.copyOf(generatorArray1, generatorArray1.length);
    int[] generatorArray10 = Arrays.copyOf(generatorArray1, generatorArray1.length);
    StopWatch stopWatch = new StopWatch("测试排序的时间复杂度");
    stopWatch.start("冒泡排序");
    BubbleSort.sort(generatorArray2);
    stopWatch.stop();
    stopWatch.start("选择排序");
    BubbleSort.sort(generatorArray6);
    stopWatch.stop();
    stopWatch.start("插入排序");
    InsertionSort.sort(generatorArray7);
    stopWatch.stop();
    stopWatch.start("希尔排序1");
    ShellSort.sort(
        generatorArray8, ShellSort.getIncrementSeries(ShellSort.shell, generatorArray8.length));
    stopWatch.stop();
    stopWatch.start("希尔排序2");
    ShellSort.sort(
        generatorArray9, ShellSort.getIncrementSeries(ShellSort.hibbard, generatorArray9.length));
    stopWatch.stop();
    stopWatch.start("希尔排序3");
    ShellSort.sort(generatorArray10, new int[] {1, 2, 4, 8, 16, 32});
    stopWatch.stop();
    stopWatch.start("归并排序");
    MergerSort.recursionSplit(generatorArray1);
    stopWatch.stop();
    stopWatch.start("堆排序");
    HeapSort.sort(generatorArray5);
    stopWatch.stop();
    stopWatch.start("快速排序");
    QuickSort.sorted(generatorArray4);
    stopWatch.stop();
    stopWatch.start("桶排序");
    new BucketSort().sort(generatorArray3, max);
    stopWatch.stop();
    log.info(stopWatch.prettyPrint());

    // 断言判断排序后首尾节点值是否相等
    log.info("开始执行断言");
    int[] expect = new int[] {generatorArray1[0], generatorArray1[generatorArray1.length - 1]};
    int[] actual1 = new int[] {generatorArray2[0], generatorArray2[generatorArray2.length - 1]};
    Assert.assertArrayEquals(expect, actual1);
    int[] actual2 = new int[] {generatorArray3[0], generatorArray3[generatorArray3.length - 1]};
    Assert.assertArrayEquals(expect, actual2);
    int[] actual3 = new int[] {generatorArray4[0], generatorArray4[generatorArray4.length - 1]};
    Assert.assertArrayEquals(expect, actual3);
    int[] actual4 = new int[] {generatorArray5[0], generatorArray5[generatorArray5.length - 1]};
    Assert.assertArrayEquals(expect, actual4);
    int[] actual5 = new int[] {generatorArray6[0], generatorArray6[generatorArray6.length - 1]};
    Assert.assertArrayEquals(expect, actual5);
    int[] actual6 = new int[] {generatorArray7[0], generatorArray7[generatorArray7.length - 1]};
    Assert.assertArrayEquals(expect, actual6);
    int[] actual7 = new int[] {generatorArray8[0], generatorArray8[generatorArray8.length - 1]};
    Assert.assertArrayEquals(expect, actual7);
    int[] actual8 = new int[] {generatorArray9[0], generatorArray9[generatorArray9.length - 1]};
    Assert.assertArrayEquals(expect, actual8);
    int[] actual9 = new int[] {generatorArray10[0], generatorArray10[generatorArray10.length - 1]};
    Assert.assertArrayEquals(expect, actual9);
  }
}
