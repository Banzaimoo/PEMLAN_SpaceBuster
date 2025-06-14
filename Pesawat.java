import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Pesawat extends JLabel implements KeyListener{

    int moveDir = 0;
    int speed = 10;

    public Pesawat(){
        this.setBackground(Color.CYAN);
        this.setBounds(477,550 , 70, 100);
        this.setOpaque(true);
    }

    public void moveControl(int moveDirection){
        switch (moveDirection) {
            case 1 -> // Move right
                this.moveRight();
            case 2 -> // Move left
                this.moveLeft();
            default -> {
            }
        }
    }

    public void moveRight(){
        int x = this.getX();
        int y = this.getY();
        this.setLocation(x + speed, y);
    }

    public void moveLeft(){
        int x = this.getX();
        int y = this.getY();
        this.setLocation(x - speed, y);
    }

    public void shoot() {
        Bullet bullet = new Bullet(this);
        Thread bulletThread = new Thread(bullet);
        bulletThread.start();
        Window.addComponent(bullet, 2);
        Window.mainPanel.repaint();
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
            case 'd' -> moveDir = 0; // Stop moving when the key is released
            case 'a' -> moveDir = 0; // Stop moving when the key is released
            default -> {
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}