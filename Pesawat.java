import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Pesawat extends JLabel implements KeyListener, Runnable {
    int moveDir = 0;
    int speed = 10;
    public boolean isDead = false;

    // Shooting interval in milliseconds
    private final int shootInterval = 500;

    // Timestamp of last shot fired
    private long lastShootTime = 0;

    public Pesawat() {
        this.setIcon(new ImageIcon("assets/pesawat.png"));
        this.setBounds(477, 550, 100, 100);
    }

    public void moveControl(int moveDirection) {
        if (moveDirection == 1) moveRight();
        else if (moveDirection == 2) moveLeft();
    }

    public void moveRight() {
        // Keep ship within window bounds (0 to window width - width)
        int newX = Math.min(getX() + speed, 1024 - this.getWidth());
        this.setLocation(newX, getY());
    }

    public void moveLeft() {
        int newX = Math.max(getX() - speed, 0);
        this.setLocation(newX, getY());
    }

    // Modified shoot method to fire 4 lasers
    public void shoot() {
        int baseX = this.getX();
        int baseY = this.getY();

        int laserSpacing = 25;  // spacing between lasers

        // Fire 4 bullets horizontally spaced
        for (int i = 0; i < 4; i++) {
            int bulletX = baseX + 10 + i * laserSpacing;
            Bullet bullet = new Bullet(this, 10, bulletX - baseX);
            bullet.start();
            Window.addComponent(bullet, 3);
        }
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
                // Optional: still allow manual shot on space
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
            default -> {}
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
        lastShootTime = System.currentTimeMillis();
        while (!isDead) {
            moveControl(moveDir);

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShootTime >= shootInterval) {
                shoot();
                lastShootTime = currentTime;
            }

            sleep(16);
        }
    }
}

