package om.nhsoft.nanjing.design.DIP;

import lombok.Data;

/**
 * @Author: nhsoft.lsd
 * @Description: 普通用户
 * @Date:Create：in 2019-03-26 20:38
 * @Modified By：
 */
@Data
public class CommonCustomer extends Customer{

    public CommonCustomer(String name, String email) {
        super(name, email);
    }
}
