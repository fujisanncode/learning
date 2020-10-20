package com.example.sqllearning.designPattern.state;

// 赢家状态， 赢家状态和售出状态类似，区别在于赢家状态需要释放两个糖果
public class WinnerState extends State {

    private GumballMachine gumballMachine;

    public WinnerState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void dispense() {
        // 释放第一颗糖
        this.gumballMachine.releaseBall();
        if (this.gumballMachine.getGumballNumber() > 0) {
            // 释放第二课糖
            this.gumballMachine.releaseBall();
            if (this.gumballMachine.getGumballNumber() > 0) {
                this.gumballMachine.setState(this.gumballMachine.getNoQuarterState());
            } else {
                this.gumballMachine.setState(this.gumballMachine.getSoldOutState());
            }
        } else {
            this.gumballMachine.setState(this.gumballMachine.getSoldOutState());
        }
    }
}
