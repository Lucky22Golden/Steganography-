import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.text.Document;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import GUIcomponents.MyButton;
import GUIcomponents.MyDialogBox;
import GUIcomponents.MyLabel;
import GUIcomponents.MyPanel;
import java.awt.Font;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

public class MyFrame extends JFrame{
    public static ArrayList<Character> listBits = new ArrayList<>();
      
    MyFrame(){

        Font titleFont = new Font("Times New Roman", Font.BOLD, 42);
        Font buttonFont = new Font("Arial", Font.BOLD,20);

        //Title
        MyLabel l = new MyLabel("Steganography for FREE!!!",titleFont,250, 40, 360, 40);

        //First label for pic
        MyLabel pic1 = new MyLabel(null,null,10, 160, 360, 380);

        //Second label for pic with hidden text
        MyLabel pic2 = new MyLabel(null,null,420, 160, 360, 380);
        
        //Container on top
        MyPanel p = new MyPanel(new Color(247,138,5), 0, 0, 800, 100);
        p.setLayout(new BorderLayout());
        p.add(l,BorderLayout.WEST);

        MyLabel txtCount = new MyLabel("", buttonFont,10, 340, 160, 40);
        
        //Text for input
        JTextArea txt = new JTextArea();
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setBounds(10,10,160,200);
        txt.getDocument().addDocumentListener(new DocumentListener() {
           
            @Override
            public void changedUpdate(DocumentEvent e) { 
                onInputText(txt,txtCount);
                
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                onInputText(txt,txtCount);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onInputText(txt,txtCount);
            }
        });
        
        //Scroll for textArea
        JScrollPane scroller = new JScrollPane(txt);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.getViewport().setBackground(Color.black);
        scroller.getViewport().add(txt);
        scroller.setBounds(10,10,160,200);
        
        //Button for input
        MyButton entermsg = new MyButton(160, "Enter text", 10, 220,f ->putTextInPic(txt, pic1,pic2,this),buttonFont);

        //Button to read
        MyButton readmsg = new MyButton(160,"Read text",10,280,f->readFromImage(pic1, this),buttonFont);

        //Side Container
        MyPanel p2 = new MyPanel(Color.orange, 800,0,200,600);
        p2.add(scroller);
        p2.add(entermsg);
        p2.add(readmsg);
        p2.add(txtCount);

        //Button to select image
        MyButton b1 = new MyButton(200, "Select Image", 100, 120, label -> onClikSelect(pic1),buttonFont);

        //Button for download
        MyButton b2 = new MyButton(200, "Download Image", 500, 120,label ->onClickDownload(this, pic2,pic1),buttonFont);
        
        ImageIcon image = new ImageIcon("logo1.png");
        setIconImage(image.getImage());
        setTitle("SteganographyFREE");
        setSize(1000,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        this.add(b1);this.add(b2);this.add(pic1);this.add(pic2);this.add(p2); this.add(p);
        setLocationRelativeTo(null);
        setVisible(true);
    }
        
          





    // Download generated image +
    public static void onClickDownload(JFrame frame,JLabel label, JLabel label2){
               if(label.getIcon()==null){
                JOptionPane.showMessageDialog(frame, "Picture generated!");
                return;
               }
               ImageIcon icon = (ImageIcon) label.getIcon();
               Image img = icon.getImage();

               BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
               bufferedImage.getGraphics().drawImage(img, 0, 0, null);

               LocalDateTime now = LocalDateTime.now();
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
               String formattedDateTime = now.format(formatter);
               String nameoffile = JOptionPane.showInputDialog("Enter file name:");
               if(nameoffile != null && !nameoffile.trim().isEmpty()){
                
               String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + nameoffile+"_"+formattedDateTime+".png";
               try {
                    File fileToSave = new File(downloadsPath);
                    ImageIO.write(bufferedImage, "png", fileToSave);
                    JOptionPane.showMessageDialog(frame, "Image saved to: " + fileToSave.getAbsolutePath());
                    label.setIcon(null);
                    label2.setIcon(null);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error saving image.");
                }
    }
    }
        
     // Select image +
    public static void onClikSelect(JLabel label){
        if (label == null) {
            return;
        }else{
         JFileChooser fileChooser = new JFileChooser();
         FileNameExtensionFilter filter = new FileNameExtensionFilter("Images","png","jpg");
         fileChooser.setFileFilter(filter);
         int result =fileChooser.showOpenDialog(null);
         if(result == JFileChooser.APPROVE_OPTION){
            
            File seletedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
            ImageIcon imageIcon = new ImageIcon(seletedFile.getAbsolutePath());
            Image img = imageIcon.getImage();
            Image imgScale = img.getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_SMOOTH);
            ImageIcon imgiconscaled = new ImageIcon(imgScale);
            label.setIcon(imgiconscaled);
            
         }
        }
    }
           
    //To limit the number of chars in TextArea+
    public static void onInputText(JTextArea txt, JLabel l){

            Document document = txt.getDocument();
            int maxChars = 200;

            if (document.getLength() > maxChars) {
            SwingUtilities.invokeLater(() -> {
            try {
                // If it exceeds, remove the excess characters
                document.remove(maxChars, document.getLength() - maxChars);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        });
    }

            int countWords = document.getLength();
            l.setText("Words:" + countWords + "/200");
    }
    
    public static int checki(int i){
        return i==0 ? 8:0;
    }

//Convert text to bits
public static void convertToBits(JTextArea txt){
            
    listBits.clear();
    for (char letter : txt.getText().toCharArray()) {
        int asciiValue = (int) letter;
        String binaryString = Integer.toBinaryString(asciiValue);

        int leadingZeros = 8 - binaryString.length();
        StringBuilder zerosBuilder = new StringBuilder();
        for (int i = 0; i < leadingZeros; i++) {
            zerosBuilder.append("0");
        }
        String finString = zerosBuilder.toString() + binaryString;

        for (int i = 0; i < finString.length(); i++) {
            listBits.add(finString.charAt(i));
        }
    }

}

public static String getByte(int size) {

    String sizeBinary = Integer.toBinaryString(size);

    int leadingZeros = 8 - sizeBinary.length();
    StringBuilder zerosBuilder = new StringBuilder();
    for (int i = 0; i < leadingZeros; i++) {
        zerosBuilder.append("0");
    }

    return zerosBuilder.toString() + sizeBinary;
}





    //Put text into image+
    public static void putTextInPic(JTextArea txt,JLabel label,JLabel label2,JFrame frame){

            if (label.getIcon() == null) {
                JOptionPane.showMessageDialog(frame, "Picture not selected!");
                return;
            } else if (txt.getText().length()==0){
                JOptionPane.showMessageDialog(frame, "No text!");
                return;
            }
            
            String formattedString = getByte(txt.getText().length());
            convertToBits(txt);
            txt.setText(null);

            ImageIcon icon = (ImageIcon) label.getIcon();
            BufferedImage img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = img.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose(); 
            BufferedImage img2 = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);

            int width = img.getWidth();
            int height = img.getHeight();
            int counter=0;

            for (int i = 0; i < 8; i++) { 
                int p = img.getRGB(i, 0);
                int red = (p >> 16) & 0xFF;
        
                if (formattedString.charAt(i) == '0') {
                    if (red % 2 == 1) {
                        red--; // Flip the least significant bit if it's 1
                    }
                } else {
                    if (red % 2 == 0) {
                        red++; // Flip the least significant bit if it's 0
                    }
                }
        
                Color newColor = new Color(red, (p >> 8) & 0xFF, p & 0xFF);
                img2.setRGB(i, 0, newColor.getRGB());
            }
        
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    
                    if (i == 0 && j < 8) {
                        continue;
                    }
        
                    int p = img.getRGB(j, i);
                    int red = (p >> 16) & 0xFF;
                    int green = (p >> 8) & 0xFF;
                    int blue = p & 0xFF;
        
                    if (counter < listBits.size()) {
                        if (listBits.get(counter) == '1') {
                            if (red % 2 == 0) {
                                red++;
                            }
                        } else {
                            if (red % 2 == 1) {
                                red--;
                            }
                        }
                        counter++;
                    }
        
                    Color newColor = new Color(red, green, blue);
                    img2.setRGB(j, i, newColor.getRGB());
                }
            }

            label2.setIcon(new ImageIcon(img2));    
        } 

    public static void readFromImage(JLabel label, JFrame frame) {
        if (label.getIcon() == null) {
            JOptionPane.showMessageDialog(frame, "Picture not selected!");
            return;
        }
    
        ImageIcon icon = (ImageIcon) label.getIcon();
        BufferedImage img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = img.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
    
        int width = img.getWidth();
        int height = img.getHeight();

        StringBuilder sizeBuilder = new StringBuilder("");
        
        for (int i = 0; i < 8; i++) {
            int p = img.getRGB(i, 0);
            int red = (p >> 16) & 0xFF;
            if (red % 2 == 0) {
                sizeBuilder.append('0');
                
            } else {
                sizeBuilder.append('1');
                
            }
            
        }
    
        String sizeBinary = sizeBuilder.toString();
        int messageSize = Integer.parseInt(sizeBinary, 2);
    
        int counter = 0;
        StringBuilder textBuilder = new StringBuilder("");
        StringBuilder text = new StringBuilder("");
    
        // Continue reading the message bits
        for (int i = 0; i < height; i++) {
            for (int j = checki(i); j < width; j++) {
                int p = img.getRGB(j, i);
    
                int red = (p >> 16) & 0xFF;
                if (counter % 8 == 0 && counter != 0) {
            
                    int asciiValue = Integer.parseInt(textBuilder.toString(), 2);
                    char character = (char) asciiValue;
                    textBuilder.setLength(0);
                    text.append(character);
                    
                }
                if (red % 2 == 0) {
                    textBuilder.append('0');
                } else {
                    textBuilder.append('1');
                }
    
                
                if (counter >= messageSize*8 ) {
                    break;
                }
                counter++;

            }
            if (counter >= messageSize*8 ) {
                break;
            }
        }
    
        String mainText = text.toString();
        new MyDialogBox(frame, mainText);
    }

    
}