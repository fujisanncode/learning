package com.example.sqllearning.designPattern.state;

import lombok.extern.slf4j.Slf4j;

// 顶层抽象类中设置默认的调用错误的消息
@Slf4j
public abstract class State {

    // 投入硬币，没有硬币状态转为有硬币状态
    public void insertQuarter() {
        log.info("can not insert quarter");
    }

    // 退出硬币，有硬币状态转为没有硬币状态
    public void ejectQuarter() {
        log.info("can not eject quarter");
    }

    // 转动曲柄，有硬币状态转为售出状态
    public void turnCrank() {
        log.info("can not turn crank");
    }

    // 发糖，售出状态转为没有硬币状态或者售罄状态
    public void dispense() {
        log.info("can not dispense gumball");
    }
}
