package ink.fujisann.learning.base.designPattern.state;

// 糖果售罄，不需要实现任何行为，不需要糖果机状态变化，只需要重新调用糖果机的重填糖果的方法
public class SoldOutState extends State {

    private GumballMachine gumballMachine;

    public SoldOutState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

}
