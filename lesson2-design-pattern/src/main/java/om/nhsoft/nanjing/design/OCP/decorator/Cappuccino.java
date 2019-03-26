package om.nhsoft.nanjing.design.OCP.decorator;

import om.nhsoft.nanjing.design.OCP.factory.Coffee;

import java.math.BigDecimal;

/**
 * @Author: nhsoft.lsd
 * @Description: 卡布奇诺
 * @Date:Create：in 2019-03-26 19:58
 * @Modified By：
 */
public class Cappuccino implements Beverage {

    @Override
    public BigDecimal getPrice() {
        return new BigDecimal(25);
    }

    @Override
    public String getDescription() {
        return "Cappuccino";
    }
}
