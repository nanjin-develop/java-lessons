package om.nhsoft.nanjing.design.LSP;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-03-26 20:41
 * @Modified By：
 */
public class MailServer {

    public static void main(String args[]){



        MailSender sender = new MailSender();

        Customer customer = new VIPCustomer("vip客户", "jjj@vip.com");
        sender.send(customer);

        customer = new CommonCustomer("普通客户", "jjj@common.com");
        sender.send(customer);

    }
}
