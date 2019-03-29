package om.nhsoft.nanjing.design.LSP;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-03-29 20:02
 * @Modified By：
 */
public class Square extends Rectangle {
    public void setWidth(double width) {
        super.setLength(width);
        super.setWidth(width);
    }
    public void setLength(double length) {
        super.setLength(length);
        super.setWidth(length);
    }
}
