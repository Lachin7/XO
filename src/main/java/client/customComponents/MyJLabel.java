package client.customComponents;

import javax.swing.*;
import java.awt.*;

public class MyJLabel extends JLabel {

    public MyJLabel(String text, Color foreGround, int fontSize, Container container, int x , int y, int width, int height){
        setText(text);
        setForeground(foreGround);
        Font font = new Font("Ariel",Font.BOLD,fontSize);
        setFont(font);
        container.add(this);
//        AffineTransform affinetransform = new AffineTransform();
//        font.getTransform();
//        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        setBounds(x,y, width,height);
    }

}
