import java.util.ArrayList;
import javax.swing.*;

public class Bullet extends JLabel {
    static ArrayList<Bullet> bullets = new ArrayList<>();
    int speed = 10;

    public Bullet(Pesawat pesawat) {
        this.setIcon(new ImageIcon("assets/peluru.png"));
        this.setBounds(pesawat.getX() + 30, pesawat.getY(), 40, 30);
        bullets.add(this);
    }

    public void move() {
        this.setLocation(getX(), getY() - speed);
        if (getY() + getHeight() < 0) {
            Window.removeComponent(this);
            bullets.remove(this);
        }
    }
}
