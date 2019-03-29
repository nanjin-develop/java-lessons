package om.nhsoft.nanjing.design.DIP;

import lombok.Data;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-03-26 20:36
 * @Modified By：
 */
@Data
public abstract class Customer {

    private String name;
    private String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

