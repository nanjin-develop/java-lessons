package om.nhsoft.nanjing.design.OCP.decorator;

import om.nhsoft.nanjing.design.OCP.factory.Coffee;

import java.math.BigDecimal;

/**
 * @Author: nhsoft.lsd
 * @Description: 摩卡
 * @Date:Create：in 2019-03-26 19:55
 * @Modified By：
 */
public class Mocha implements Beverage  {

    @Override
    public BigDecimal getPrice() {
        return new BigDecimal(24);
    }

    @Override
    public String getDescription() {
        return "Mocha";
    }
}
