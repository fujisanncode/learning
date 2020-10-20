package ink.fujisann.learning.utils.sort;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @description: 希尔排序: 按照指定增量分组，分组进行插入排序； 插入排序对于数据量小或者近似完成排序，效率高；因此每轮排序都比较快
 * @author: hulei
 * @create: 2020-06-17 20:57:51
 */
@Slf4j
public class ShellSort {
  // 2^k -1
  public static String hibbard = "hibbardSeries";
  // 2^k
  public static String shell = "shellSeries";

  public static int[] getIncrementSeries(String seriesName, int length) {
    try {
      Class clazz = ShellSort.class;
      Method method = clazz.getMethod(seriesName, Integer.class);
      return (int[]) method.invoke(clazz.newInstance(), length);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
    return null;
  }

  public int[] shellSeries(Integer length) {
    int[] series = new int[] {0};
    for (int i = 0, j = (int) Math.pow(2, i); j <= length / 2; ) {
      series[i] = j;
      j = (int) Math.pow(2, ++i);
      series = Arrays.copyOf(series, i + 1); // 扩容
    }
    series = Arrays.copyOf(series, series.length - 1); // 去除最后一个元素
    log.info(Arrays.toString(series));
    return series;
  }

  public int[] hibbardSeries(Integer length) {
    int[] series = new int[] {0};
    for (int i = 0, j = (int) Math.pow(2, i + 1) - 1; j <= length / 2; ) {
      series[i] = j;
      j = (int) Math.pow(2, ++i + 1) - 1;
      series = Arrays.copyOf(series, i + 1); // 扩容
    }
    series = Arrays.copyOf(series, series.length - 1); // 去除最后一个元素
    log.info(Arrays.toString(series));
    return series;
  }

  public static void sort(int[] source, int[] incrementSeries) {
    for (int incrementIndex = incrementSeries.length - 1; incrementIndex >= 0; incrementIndex--) {
      int increment = incrementSeries[incrementIndex]; // 增量值
      for (int group = 0; group < increment; group++) { // 分成increment个组，每个组使用插入排序
        groupInsertionSort(source, group, increment);
      }
    }
  }

  public static void groupInsertionSort(int[] source, int start, int increment) {
    for (int i = 0; i < source.length - increment; i += increment) {
      for (int j = i + increment; j >= 0 + increment; j -= increment) {
        if (source[j - increment] > source[j]) {
          SortUtil.swapNode(source, j - increment, j);
        } else {
          continue; // 如果当前位和前一位不需要交换，表示位置正确，不需要继续向前调整
        }
      }
    }
  }
}
