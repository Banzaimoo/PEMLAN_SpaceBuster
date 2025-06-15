import java.util.Random;
import javax.swing.*;

public class Enemy extends JLabel {
    int speed = 3;
    static Random rand = new Random();

    public Enemy() {
        this.setIcon(new ImageIcon("assets/enemy.png"));
        int startX = rand.nextInt(1024 - this.getIcon().getIconWidth());
        this.setBounds(startX, 0, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
    }

    public void move() {
        this.setLocation(getX(), getY() + speed);
        if (getY() > 768) {
            Window.removeComponent(this);
            Main.enemyList.remove(this);
        }
    }
}