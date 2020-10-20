package ink.fujisann.learning.designPattern;

import ink.fujisann.learning.LearningApplication;
import ink.fujisann.learning.designPattern.template.Person;
import ink.fujisann.learning.designPattern.template.Tea;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// 指定测试环境和指定启动类
@RunWith (SpringRunner.class)
@SpringBootTest (classes = LearningApplication.class)
@Slf4j
public class TemplateTest {

    @Test
    public void testCaffeineBeverage() {
        Tea tea = new Tea();
        tea.prepareRecipe();
    }

    @Test
    public void testPersons() {
        Person[] persons = {new Person(20), new Person(18), new Person(19)};
        // 对象数组使用排序方法，必须先实现comparable接口
        Arrays.sort(persons);
        log.info(Arrays.toString(persons));
    }

}
