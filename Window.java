import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {
    public static JLayeredPane mainPanel;
    public static JLabel scoreLabel;
    public static ArrayList<JLabel> healthIcons = new ArrayList<>();
    public static JPanel gameOverPanel;  

    public Window() {
        this.setTitle("Space Shooter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1024, 768);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        ImageIcon bg = new ImageIcon("assets/bg.png");
        JLabel bgLabel = new JLabel(bg);
        bgLabel.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());

        mainPanel = new JLayeredPane();
        mainPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        mainPanel.add(bgLabel, Integer.valueOf(0));

        // Skor kiri atas
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(20, 20, 300, 40);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        mainPanel.add(scoreLabel, Integer.valueOf(10));

        // Nyawa
        initializeHealthIcons();

        // Game Over
        setupGameOverScreen();

        this.add(mainPanel);
    }

    private void initializeHealthIcons() {
        ImageIcon healthIcon = new ImageIcon("assets/health.png");
        healthIcon = scaleImage(healthIcon, 40, 40);

        for (int i = 0; i < 3; i++) {
            JLabel healthLabel = new JLabel(healthIcon);
            healthLabel.setBounds(20 + (i * 50), 670, 40, 40);
            mainPanel.add(healthLabel, Integer.valueOf(10));
            healthIcons.add(healthLabel);
        }
    }

    private void setupGameOverScreen() {
        gameOverPanel = new JPanel();
        gameOverPanel.setBounds(0, 0, 1024, 768);
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
        gameOverPanel.setOpaque(false);
        gameOverPanel.setVisible(false);

        // Teks Game Over
        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 72));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Skor Akhir
        JLabel finalScoreLabel = new JLabel();
        finalScoreLabel.setName("finalScore");
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 36));
        finalScoreLabel.setForeground(Color.WHITE);
        finalScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gameOverPanel.add(Box.createVerticalGlue());
        gameOverPanel.add(gameOverLabel);
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        gameOverPanel.add(finalScoreLabel);
        gameOverPanel.add(Box.createVerticalGlue());

        mainPanel.add(gameOverPanel, Integer.valueOf(20));
    }

    public static void showGameOver(int score) {
        // Menyesuaikan text skor dengan skor asli
        JLabel finalScoreLabel = (JLabel) gameOverPanel.getComponent(3);
        finalScoreLabel.setText("Final Score: " + score);

        gameOverPanel.setVisible(true);
        mainPanel.setComponentZOrder(gameOverPanel, 0);  
    }

    public static void removeHealthIcon(int index) {
        if (index >= 0 && index < healthIcons.size()) {
            mainPanel.remove(healthIcons.get(index));
            mainPanel.repaint();
        }
    }

    private ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
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
