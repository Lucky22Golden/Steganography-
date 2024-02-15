package GUIcomponents;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.function.Consumer;


public class MyButton extends JButton{

    public MyButton(int width, String text, int x, int y, Consumer<JLabel> f,Font font) {
        
         setText(text);
         setBounds(x, y, width, 40);
         setBackground(new Color(247, 138, 5));
         setCursor(new Cursor(Cursor.HAND_CURSOR));
         addActionListener(e -> f.accept(null));
         setFont(font);
    }

   
}