
import javax.swing.*;
public class Window extends JFrame {
    
    public static JLayeredPane mainPanel;

    public Window(){
        this.setTitle("Flappy Plane");
        // ImageIcon logo = new ImageIcon("Logo.png");
        // window.setIconImage(logo.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024,768);
        this.setResizable(false);

        ImageIcon image = new ImageIcon("assets/bg.png");
        JLabel label = new JLabel(image);
        label.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());

        mainPanel = new JLayeredPane();
        mainPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        mainPanel.add(label, Integer.valueOf(0));

        this.add(mainPanel);
        this.setLayout(null);
    }

    public static void addComponent(JLabel label, int layer){
        mainPanel.add(label, Integer.valueOf(layer));
        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();
    }

    public static void removeComponent(JLabel label){
        mainPanel.remove(label);
        mainPanel.repaint();
    }
}
