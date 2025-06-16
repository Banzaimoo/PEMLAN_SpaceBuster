import javax.swing.ImageIcon; // Pastikan ini ada

public class Stinger extends Pesawat {
    private long lastShootTime = 0; // Waktu tembakan terakhir
    private final int fireRate = 20; // Fire rate dalam milidetik

    public Stinger() {
        this.setIcon(new ImageIcon("assets/stinger.png"));
        this.setBounds(477, 550, 100, 100);
    }

    @Override

    public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= fireRate) { // Cek apakah sudah waktunya menembak
            lastShootTime = currentTime; // Update waktu tembakan terakhir
            // Implementasi khusus untuk Stinger
            int baseX = this.getX();
            int baseY = this.getY();
            for (int i = 0; i < 2; i++) {
                int bulletX = baseX + 15 + i * 25; // Spasi antar peluru
                Bullet bullet = new Bullet(this, 20, bulletX - baseX);
                bullet.start();
                Window.addComponent(bullet, 3);
            }
        }
    }

}
