import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Pesawat extends JLabel implements KeyListener, Runnable {
    int moveDir = 0;
    int speed = 10;
    boolean isDead = false;

    public Pesawat() {
        this.setIcon(new ImageIcon("assets/pesawat.png"));
        this.setBounds(477, 550, 100, 100);
    }

    public void moveControl(int moveDirection) {
        if (moveDirection == 1) moveRight();
        else if (moveDirection == 2) moveLeft();
    }

    public void moveRight() {
        this.setLocation(getX() + speed, getY());
    }

    public void moveLeft() {
        this.setLocation(getX() - speed, getY());
    }

    // ini di override buat setiap subclass
    public void shoot() {
        Bullet bullet = new Bullet(this, 10, 30);
        bullet.start();
        Window.addComponent(bullet, 3);
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'd':
                moveDir = 1;
                break;
            case 'a':
                moveDir = 2;
                break;
            case ' ':
                System.out.println("Space pressed, shooting bullet...");
                shoot();
                break;
            case 'q':
                System.exit(0);
            case 'f':
                System.out.println("Retrying...");
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'd' -> moveDir = 0; 
            case 'a' -> moveDir = 0; 
            default -> {
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(!isDead){
            moveControl(moveDir);
            sleep(16);
        }
    }
}