package com.example.sqllearning.dataStructure;

import com.example.sqllearning.algorithm.GeneratorArray;
import com.example.sqllearning.algorithm.SortProxy;
import com.example.sqllearning.algorithm.sort.BucketSort;
import com.example.sqllearning.algorithm.sort.Sort;
import com.example.sqllearning.algorithm.sort.SortFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @description: 数据结构入门1
 * @author: hulei
 * @create: 2020-05-01 07:51:38
 */
// @SpringBootTest (classes = SqlLearningApplication.class)
@Slf4j
public class SortTest {

  @Test
  public void testInput() throws IOException {
    InputStream input = System.in;
    // input = new FileInputStream("");
    InputStreamReader inputStream = new InputStreamReader(input);
    BufferedReader bufferedReader = new BufferedReader(inputStream);
    System.out.println("please input to console: ");
    String line = bufferedReader.readLine();
    System.out.println(line);
  }

  @Test
  public void testGeneratorArray() {
    log.info(Arrays.toString(GeneratorArray.get()));
  }

  @Test
  public void testSimpleBucketSort() {
    int[] sourceArray = GeneratorArray.get(100, 1, 10);
    int[] destArray = proxyBucketSort(sourceArray, 10);
    log.info(Arrays.toString(destArray));
  }

  public int[] proxyBucketSort(int[] sourceArray, int max) {
    // enhancer配置父类和回调
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(BucketSort.class);
    enhancer.setCallback(new SortProxy());
    // 通过enhancer生成代理对象，被代理对象的子类
    BucketSort proxyBucketSort = (BucketSort) enhancer.create();
    proxyBucketSort.sort(sourceArray, max);
    return sourceArray;
  }

  @Test
  public void testFactory() {
    Sort bubble = SortFactory.newSort(BucketSort.class);
  }
}
