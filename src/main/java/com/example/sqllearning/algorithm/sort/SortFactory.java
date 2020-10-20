package com.example.sqllearning.algorithm.sort;

/**
 * @description: 生成排序算法的工厂
 * @author: hulei
 * @create: 2020-06-09 00:12:05
 */
public class SortFactory {

  public static Sort newSort(Class sortClass) {
    Sort obj = null;
    try {
      obj = (Sort) Class.forName(sortClass.getName()).newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return obj;
  }
}
