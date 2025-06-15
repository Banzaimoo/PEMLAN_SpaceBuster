import javax.swing.*;

public class Window extends JFrame {
    public static JLayeredPane mainPanel;

    public Window() {
        this.setTitle("Space Shooter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024, 768);
        this.setResizable(false);

        ImageIcon bg = new ImageIcon("assets/bg.png");
        JLabel bgLabel = new JLabel(bg);
        bgLabel.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());

        mainPanel = new JLayeredPane();
        mainPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        mainPanel.add(bgLabel, Integer.valueOf(0));

        this.add(mainPanel);
        this.setLayout(null);
    }

    public static void addComponent(JLabel label, int layer) {
        mainPanel.add(label, Integer.valueOf(layer));
        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();
    }

    public static void removeComponent(JLabel label) {
        mainPanel.remove(label);
        mainPanel.repaint();
    }
}