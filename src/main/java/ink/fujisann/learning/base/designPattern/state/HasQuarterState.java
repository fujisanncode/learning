package ink.fujisann.learning.base.designPattern.state;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HasQuarterState extends State {

    Random random = new Random(System.currentTimeMillis());

    private GumballMachine gumballMachine;

    public HasQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void turnCrank() {
        log.info("turn crank");
        // 转动曲杆后，根据概率判定需要设定状态为售出，还是赢家售出模式
        int i = random.nextInt(10);
        // 随机数为十分之一概率，并且糖果数量超过1，可以设定赢家模式
        if ((i == 0) && this.gumballMachine.getGumballNumber() > 1) {
            log.info("you are winner, you will get two gumball");
            this.gumballMachine.setState(this.gumballMachine.getWinnerState());
        } else {
            log.info("you are normal sold, you will get one gumball");
            this.gumballMachine.setState(this.gumballMachine.getSoldState());
        }
    }
}
