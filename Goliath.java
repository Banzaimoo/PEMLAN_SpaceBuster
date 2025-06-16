import javax.swing.ImageIcon; // Pastikan ini ada
public class Goliath extends Pesawat {

    private long lastShootTime = 0; // Waktu tembakan terakhir
    private final int fireRate = 400; // Fire rate dalam milidetik


    public Goliath() {
        this.setIcon(new ImageIcon("assets/goliath.png"));
        this.setBounds(477, 550, 120, 100);
    }

    @Override
  public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= fireRate) { // Cek apakah sudah waktunya menembak
            lastShootTime = currentTime; // Update waktu tembakan terakhir
        // Implementasi khusus untuk Goliath
        int baseX = this.getX();
        int baseY = this.getY();
        for (int i = 0; i < 5; i++) {
            int bulletX = baseX + 10 + i * 25; // Spasi antar peluru
            Bullet bullet = new Bullet(this, 10, bulletX - baseX);
            bullet.start();
            Window.addComponent(bullet, 3);
        }
    }
}
}
