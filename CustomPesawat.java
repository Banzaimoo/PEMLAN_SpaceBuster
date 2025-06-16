import javax.swing.*;

public class CustomPesawat extends Pesawat {
    private int laserCount;
    private int fireRate;  // in milliseconds

    private long lastShootTime = 0;

    /**
     * Constructor to create a custom ship with specific fire rate and number of lasers
     * @param fireRateMs  Fire interval in milliseconds
     * @param laserCount  Number of lasers to shoot at once
     * @param iconPath    Path to the image of the ship
     * @param startX     Starting X coordinate
     * @param startY     Starting Y coordinate
     */
    public CustomPesawat(int fireRateMs, int laserCount, String iconPath, int startX, int startY) {
        super();
        this.fireRate = fireRateMs;
        this.laserCount = laserCount;
        this.setIcon(new ImageIcon(iconPath));
        this.setBounds(startX, startY, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
    }

    @Override
    public void shoot() {
        int baseX = this.getX();
        int baseY = this.getY();

        int totalWidth = (laserCount - 1) * 15; // total horizontal spread assuming 15px spacing
        int startXOffset = (this.getWidth() - totalWidth) / 2;

        for (int i = 0; i < laserCount; i++) {
            int bulletX = baseX + startXOffset + i * 15;
            Bullet bullet = new Bullet(this, 10, bulletX - baseX);
            bullet.start();
            Window.addComponent(bullet, 3);
        }
    }

    @Override
    public void run() {
        lastShootTime = System.currentTimeMillis();

        while (!isDead) {
            moveControl(moveDir);

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShootTime >= fireRate) {
                shoot();
                lastShootTime = currentTime;
            }

            sleep(16);
        }
    }

    // Inherit key listener behavior from Pesawat or override if needed
}
