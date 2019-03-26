package om.nhsoft.nanjing.design.OCP.factory;

import java.math.BigDecimal;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-03-26 19:55
 * @Modified By：
 */
public class Mocha extends Coffee {

    @Override
    public BigDecimal getPrice() {
        return new BigDecimal(24);
    }

    @Override
    public String getDescription() {
        return "Mocha";
    }
}
