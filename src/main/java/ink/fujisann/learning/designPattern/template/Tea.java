package ink.fujisann.learning.designPattern.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Tea extends CaffeineBeverage {

    @Override
    void brew() {
        log.info("用沸水泡茶");
    }

    @Override
    void addCondiments() {
        log.info("加入柠檬");
    }

    @Override
    public boolean hook() {
        return isAddCondiments();
    }

    // 通过客户输入决定是否添加调料
    private boolean isAddCondiments() {
        System.out.println("is add condiments(Y/N): ");
        String line = "N";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            log.info(e.toString());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                log.info(e.toString());
            }
        }
        if ("Y".equals(line.toUpperCase())) {
            return true;
        } else {
            return false;
        }
    }
}
