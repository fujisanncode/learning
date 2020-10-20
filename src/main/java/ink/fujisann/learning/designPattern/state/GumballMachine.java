package ink.fujisann.learning.designPattern.state;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GumballMachine {

    private State noQuarterState;
    private State hasQuarterState;
    private State soldState;
    private State soldOutState;
    private State winnerState;

    // 初始化状态
    private State state;

    // 记录糖果数量
    private int GumballNumber;

    public GumballMachine(int gumballNumber) {
        // 初始化糖果数量和糖果机状态
        this.GumballNumber = gumballNumber;
        // 糖果即状态变化的行为委托给状态类，需要状态变化则调用对应状态类的方法进行修改
        this.noQuarterState = new NoQuarterState(this);
        this.hasQuarterState = new HasQuarterState(this);
        this.soldState = new SoldState(this);
        this.soldOutState = new SoldOutState(this);
        this.winnerState = new WinnerState(this);
        this.state = noQuarterState;
    }

    public void setState(State state) {
        this.state = state;
    }

    // 投入硬币
    public void insertQuarter() {
        this.state.insertQuarter();
    }

    // 退出硬币
    public void ejectQuarter() {
        this.state.ejectQuarter();
    }

    // 转动手柄，释放糖果
    public void turnCrank() {
        this.state.turnCrank();
        this.state.dispense();
    }

    // 释放糖果，糖果数量减少
    public void releaseBall() {
        log.info("machine release gumball");
        this.GumballNumber--;
    }

    // 重填糖果机，设置糖果数量， 然后初始化糖果机状态
    public void refill(int gumballNumber) {
        this.GumballNumber = gumballNumber;
        this.state = this.noQuarterState;
    }

    public int getGumballNumber() {
        return GumballNumber;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public State getSoldOutState() {
        log.info("gumball sold out, please refill gumball");
        return soldOutState;
    }

    public State getWinnerState() {
        return winnerState;
    }
}
