package om.nhsoft.nanjing.design.LSP;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2019-03-29 20:02
 * @Modified By：
 */
public class TestRectangle {
    public void resize(Rectangle objRect) {
        while(objRect.getWidth() <= objRect.getLength()  ) {
            objRect.setWidth(  objRect.getWidth () + 1 );
        }
    }
}