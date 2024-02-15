package GUIcomponents;

import java.awt.Font;

import javax.swing.JLabel;

public class MyLabel extends JLabel {

    public MyLabel(String text,Font font,int x,int y,int w, int h){
        setText(text);
        setFont(font);
        setBounds(x, y, w, h);
    }
    
}
