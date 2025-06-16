import javax.swing.ImageIcon;

public class Fighter extends Pesawat {
    private long lastShootTime = 0; // Waktu tembakan terakhir
    private final int fireRate = 100; // Fire rate dalam milidetik

    public Fighter() {
        this.setIcon(new ImageIcon("assets/pesawat.png"));
        this.setBounds(477, 550, 100, 100);
    }

    @Override
    public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= fireRate) { // Cek apakah sudah waktunya menembak
            lastShootTime = currentTime; // Update waktu tembakan terakhir
            // Implementasi khusus untuk Fighter
            int baseX = this.getX();
            int baseY = this.getY();
            for (int i = 0; i < 3; i++) {
                int bulletX = baseX + i * 40; // Spasi antar peluru
                Bullet bullet = new Bullet(this, 12, bulletX - baseX);
                bullet.start();
                Window.addComponent(bullet, 3);
            }
        }
    }
}
