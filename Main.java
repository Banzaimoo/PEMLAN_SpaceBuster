import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JLabel;

public class Main {
    static Window window = new Window();
    static Pesawat pesawat = new Pesawat();

    public static void main(String[] args) {
        Window.mainPanel.addKeyListener(pesawat);
        Window.addComponent(pesawat, 1);
        window.setVisible(true);

        pesawat.start();

        long lastsec = System.currentTimeMillis();
        long lastEnemySpawn = System.currentTimeMillis();

        while (true) {
            long millis = System.currentTimeMillis();
            while (millis - lastsec >= 16f) {
                //System.out.println("Enemies: " + Enemy.enemies.size() + ", Bullets: " + Bullet.bullets.size());

                ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
                ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

                for (Enemy enemy : Enemy.enemies) {
                    for (Bullet bullet : Bullet.bullets) {
                        if (CheckCollsion(enemy, bullet)) {
                            Explosion explosion = new Explosion(enemy.getX(), enemy.getY());
                            Window.addComponent(explosion, 3);
                            explosion.start();
                            Window.removeComponent(enemy);
                            Window.removeComponent(bullet);
                            bulletsToRemove.add(bullet);
                            enemiesToRemove.add(enemy);
                            break;
                        }
                    }
                }

                // Remove bullets and enemies that collided
                for (Bullet bullet : bulletsToRemove) {
                    Bullet.bullets.remove(bullet);
                } 
                for (Enemy enemy : enemiesToRemove) {
                    Enemy.enemies.remove(enemy);
                }

                // Spawn enemy every 2 seconds
                if (millis - lastEnemySpawn >= 500) {
                    Enemy enemy = new Enemy();
                    enemy.start();
                    Window.addComponent(enemy, 2);
                    lastEnemySpawn = millis;
                }

                lastsec = millis;
            }
        }
    }

    public static boolean CheckCollsion(JLabel a, JLabel b) {
        Rectangle rectA = a.getBounds();
        Rectangle rectB = b.getBounds();
        return rectA.intersects(rectB);
    }
}
