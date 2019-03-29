package om.nhsoft.nanjing.design.DIP;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-03-26 20:36
 * @Modified By：
 */
public class MailSender {

    void send(Customer customer){

        System.out.println(String.format("发给[%s]的一份邮件，邮件地址[%s]", customer.getName(), customer.getEmail()));
    }
}
