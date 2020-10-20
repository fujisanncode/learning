package ink.fujisann.learning.designPattern;

import ink.fujisann.learning.LearningApplication;
import ink.fujisann.learning.designPattern.singleton.SimpleSingleton;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = LearningApplication.class)
public class SingleTonTest {

    @Test
    public void create() {
        SimpleSingleton simpleSingleton = SimpleSingleton.getInstance();
    }

}
