import java.awt.Color;
import javax.swing.*;

public class Bullet extends JLabel implements Runnable {
    public Bullet(Pesawat pesawat){
        int pesawatX = pesawat.getX();
        int pesawatY = pesawat.getY();
        this.setBounds(pesawatX + 30, pesawatY, 10, 10);
        this.setBackground(Color.RED);
        this.setOpaque(true);     
        this.setVisible(true);     
    }

    public void move() {
        int x = this.getX();
        int y = this.getY();
        this.setLocation(x, y - 10);
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (this.getY() > 0) {
            move();
            sleep(16);
        }

        Window.removeComponent(this);
    }
}
