package ink.fujisann.learning.designPattern.state;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoQuarterState extends State {

    private GumballMachine gumballMachine;

    public NoQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        log.info("wait for quarter, current gumball number is {}", this.gumballMachine.getGumballNumber());
        log.info("insert quarter");
        this.gumballMachine.setState(gumballMachine.getHasQuarterState());
    }
}
