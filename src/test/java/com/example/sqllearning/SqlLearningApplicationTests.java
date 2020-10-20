package com.example.sqllearning;

import org.junit.Test;

// @RunWith (SpringRunner.class)
// @SpringBootTest
public class SqlLearningApplicationTests {

  @Test
  public void contextLoads() {
    int c = 20;
    long a = 100;
    long b = 10000;
    int result = (int) ((a + c) / b * 100);
    System.out.println(result);
  }
}
