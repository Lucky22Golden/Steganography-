package GUIcomponents;

import java.awt.Color;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
    
    public MyPanel(Color col,int x, int y, int w, int h){
        setBackground(col);
        setLayout(null);
        setBounds(x, y, w, h);
    }
}
