package om.nhsoft.nanjing.design.OCP.factory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-03-26 19:59
 * @Modified By：
 */
public class Saller {

    private static final String MOCHA = "MOCHA";
    private static final String CAPPUCCINO = "CAPPUCCINO";

    public static void main(String[] args)throws Exception{


        InputStream is = System.in;

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String type = br.readLine();

        Beverage beverage = null;

        if(type.equals(MOCHA)){
            beverage = new Mocha();
            System.out.println("咖啡描述：" + beverage.getDescription());
            System.out.println("咖啡售价：" + beverage.getPrice());
        }else if(type.equals(CAPPUCCINO)){
            beverage = new Cappuccino();
            System.out.println("咖啡描述：" + beverage.getDescription());
            System.out.println("咖啡售价：" + beverage.getPrice());
        }

    }
}
