import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Enemy extends JLabel implements Runnable {
    int speed = 3;
    static Random rand = new Random();

    static public ArrayList<Enemy> enemies = new ArrayList<>();

    public Enemy() {
        this.setIcon(new ImageIcon("assets/enemy.png"));
        int startX = rand.nextInt(1024 - this.getIcon().getIconWidth());
        this.setBounds(startX, 0, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
    }

    public void move() {
        this.setLocation(getX(), getY() + speed);
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        enemies.add(this);
        while(getY() < 768) {
            move();
            sleep(16);
        }
        Window.removeComponent(this);
        enemies.remove(this);
    }
}
