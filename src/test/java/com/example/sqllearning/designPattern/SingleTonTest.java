package com.example.sqllearning.designPattern;

import com.example.sqllearning.SqlLearningApplication;
import com.example.sqllearning.designPattern.singleton.SimpleSingleton;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = SqlLearningApplication.class)
public class SingleTonTest {

    @Test
    public void create() {
        SimpleSingleton simpleSingleton = SimpleSingleton.getInstance();
    }

}
