import java.awt.*;
import java.util.ArrayList;

public class Main {
    static Window window = new Window();
    static Pesawat pesawat = new Pesawat();
    static ArrayList<Enemy> enemyList = new ArrayList<>();

    public static void main(String[] args) {
        Window.mainPanel.addKeyListener(pesawat);
        Window.addComponent(pesawat, 1);
        window.setVisible(true);

        long lastsec = System.currentTimeMillis();
        long lastEnemySpawn = System.currentTimeMillis();

        while (true) {
            long millis = System.currentTimeMillis();
            while (millis - lastsec >= 16f) {
                if (pesawat.moveDir != 0) pesawat.moveControl(pesawat.moveDir);

                // Move bullets
                ArrayList<Bullet> bulletsCopy = new ArrayList<>(Bullet.bullets);
                for (Bullet b : bulletsCopy) b.move();

                // Move enemies
                ArrayList<Enemy> enemiesCopy = new ArrayList<>(enemyList);
                for (Enemy e : enemiesCopy) e.move();

                // Collision detection
                for (Enemy enemy : new ArrayList<>(enemyList)) {
                    Rectangle enemyBounds = enemy.getBounds();
                    for (Bullet bullet : new ArrayList<>(Bullet.bullets)) {
                        if (enemyBounds.intersects(bullet.getBounds())) {
                            Explosion explosion = new Explosion(enemy.getX(), enemy.getY());
                            Window.addComponent(explosion, 3);
                            explosion.start();
                            Window.removeComponent(enemy);
                            Window.removeComponent(bullet);
                            enemyList.remove(enemy);
                            Bullet.bullets.remove(bullet);
                            break;
                        }
                    }
                }

                // Spawn enemy every 2 seconds
                if (millis - lastEnemySpawn >= 2000) {
                    Enemy enemy = new Enemy();
                    Window.addComponent(enemy, 2);
                    enemyList.add(enemy);
                    lastEnemySpawn = millis;
                }

                lastsec = millis;
            }
        }
    }
}
