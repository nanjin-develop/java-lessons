package om.nhsoft.nanjing.design.OCP.factory;

import java.math.BigDecimal;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-03-26 19:47
 * @Modified By：
 */
public abstract class Coffee implements Beverage {

    private String description = "unkwnown description";

    @Override
    public String getDescription(){
        return description;
    }

    @Override
    public abstract BigDecimal getPrice();
}
