package om.nhsoft.nanjing.design.OCP.decorator;

import java.math.BigDecimal;

/**
 * @Author: nhsoft.lsd
 * @Description: 弄咖啡
 * @Date:Create：in 2019-03-26 20:13
 * @Modified By：
 */
public class Espresso implements Beverage {

    private Beverage target;

    public Espresso(Beverage target) {
        this.target = target;
    }

    @Override
    public String getDescription() {
        return "我是浓咖啡，" + target.getDescription();
    }

    @Override
    public BigDecimal getPrice() {
        return target.getPrice();
    }
}
