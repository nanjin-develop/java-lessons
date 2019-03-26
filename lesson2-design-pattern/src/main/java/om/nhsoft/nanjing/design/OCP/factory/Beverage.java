package om.nhsoft.nanjing.design.OCP.factory;

import java.math.BigDecimal;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-03-26 19:50
 * @Modified By：
 */
public interface Beverage {

    /**
     * 描述
     * @return
     */
     String getDescription();

    /**
     * 售价
     * @return
     */
     BigDecimal getPrice();
}
