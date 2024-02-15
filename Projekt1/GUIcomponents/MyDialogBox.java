package GUIcomponents;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Font;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class MyDialogBox extends JDialog{
    
    public MyDialogBox(JFrame frame,String msg){
        
        super(frame,"Text from picture,",true);
        Font font = new Font("Arial", Font.PLAIN, 12);
        setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea(msg);
        textArea.setFont(font);
        textArea.setEditable(false);
        textArea.setLineWrap(true); // Enable line wrap
        textArea.setWrapStyleWord(true); // Wrap at word boundaries

        JScrollPane scrollPane = new JScrollPane(textArea);

        MyButton button = new MyButton(1, "Exit", 1, 1, f->dispose(),font );
        button.addActionListener(e->dispose());
        add(scrollPane,BorderLayout.CENTER);
        add(button,BorderLayout.SOUTH);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
