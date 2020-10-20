package ink.fujisann.learning.designPattern.factory.integrationFactory;

public class NyIntegration implements Integration {

    @Override
    public String createDough() {
        return "纽约面粉";
    }

    @Override
    public String createSauce() {
        return "纽约大酱";
    }
}
