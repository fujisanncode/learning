package ink.fujisann.learning.base.designPattern.factory.integrationFactory;

public class ItIntegration implements Integration {

    @Override
    public String createDough() {
        return "意大利面粉";
    }

    @Override
    public String createSauce() {
        return "意大利大酱";
    }
}
