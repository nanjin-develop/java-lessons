package om.nhsoft.nanjing.design.DIP;


import lombok.Data;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-03-26 20:38
 * @Modified By：
 */
@Data
public class VIPCustomer extends Customer {

    private String header;
    private String flag;

    public VIPCustomer(String name, String email) {
        super(name, email);
    }
}
