package ink.fujisann.learning.algorithm;

/**
 * @description: 生成数组
 * 用随机数生成指定范围，指定长度数组
 * @author: hulei
 * @create: 2020-06-08 22:09:25
 */
public class GeneratorArray {

  private static final int len = 100;
  private static final int from = 0;
  private static final int to = 99;

  public static int[] get() {
    return generator(len, from, to);
  }

  public static int[] get(int len, int from, int to) {
    return generator(len, from, to);
  }

  private static int[] generator(int len, int from, int to) {
    int[] returnArray = new int[len];
    for (int i = 0; i < len; i++) {
      int tmp = (int) (Math.random() * (to - from)) + from;
      returnArray[i] = tmp;
    }
    return returnArray;
  }
}
