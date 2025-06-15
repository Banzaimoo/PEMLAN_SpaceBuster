import java.awt.Color;
import java.util.ArrayList;
import javax.swing.*;

public class Bullet extends JLabel implements Runnable {

    int speed = 10;

    static public ArrayList<Bullet> bullets = new ArrayList<>();

    public Bullet(Pesawat pesawat, int speed, int x){
        int pesawatX = pesawat.getX();
        int pesawatY = pesawat.getY();
        this.speed = speed;
        this.setBounds(pesawatX + x, pesawatY, 10, 10);
        this.setBackground(Color.RED);
        this.setOpaque(true);     
        this.setVisible(true);     
    }

    public void move() {
        int x = this.getX();
        int y = this.getY();
        this.setLocation(x, y - speed);
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
        bullets.add(this);
        while (this.getY() > 0) {
            move();
            sleep(16);
        }
        Window.removeComponent(this);
        bullets.remove(this);
    }
}
