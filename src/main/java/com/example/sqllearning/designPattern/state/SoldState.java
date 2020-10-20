package com.example.sqllearning.designPattern.state;

public class SoldState extends State {

    private GumballMachine gumballMachine;

    public SoldState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void dispense() {
        this.gumballMachine.releaseBall();
        if (this.gumballMachine.getGumballNumber() > 0) {
            // 如果还有糖果，重新设置糖果为可以投币状态（尚未投币状态）
            this.gumballMachine.setState(this.gumballMachine.getNoQuarterState());
        } else {
            this.gumballMachine.setState(this.gumballMachine.getSoldOutState());
        }
    }
}
