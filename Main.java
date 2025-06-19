import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main {
    static Window window = new Window();
    static Pesawat pesawat;

    public static void main(String[] args) {
        window.setVisible(true);
        
        // untuk panel grid option pemilihan pesawat
        JPanel selectionPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        selectionPanel.setBounds(300, 250, 400, 200);
        selectionPanel.setOpaque(false); // Transparent background

        //untuk gambar option pesawat
        ImageIcon fighterIcon = scaleImage(new ImageIcon("./assets/pesawat.png"), 80, 80);
        ImageIcon stingerIcon = scaleImage(new ImageIcon("./assets/stinger.png"), 80, 80);
        ImageIcon goliathIcon = scaleImage(new ImageIcon("./assets/goliath.png"), 80, 80);

        // membuat tombol pada gambar untuk option pesaawat
        JButton fighterBtn = createSelectionButton("Fighter", fighterIcon);
        JButton stingerBtn = createSelectionButton("Stinger", stingerIcon);
        JButton goliathBtn = createSelectionButton("Goliath", goliathIcon);

        // menambah tombol ke panel
        selectionPanel.add(fighterBtn);
        selectionPanel.add(stingerBtn);
        selectionPanel.add(goliathBtn);

        // menambahkan panel plane selection ke window
        Window.mainPanel.add(selectionPanel, Integer.valueOf(10));

        // menambah logic agar game tidak dimulai sampai pesawat dipilih
        while (pesawat == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //menutup panel selection setelah pesawat dipilih
        Window.mainPanel.remove(selectionPanel);
        Window.mainPanel.repaint();

        //memulai game
        startGame();
    }

//game logic ku pindahin jadi method di sini biar di psvm nya gak kepanjangan
    private static void startGame() {
        Window.mainPanel.addKeyListener(pesawat);
        Window.addComponent(pesawat, 1);
        pesawat.start();

        long lastUpdate = System.currentTimeMillis();
        long lastEnemySpawn = System.currentTimeMillis();

        while (true) {
            long currentTime = System.currentTimeMillis();
            
            if (currentTime - lastUpdate >= 16) {
                checkCollisions();
                
                if (currentTime - lastEnemySpawn >= 500) {
                    spawnEnemy();
                    lastEnemySpawn = currentTime;
                }
                
                lastUpdate = currentTime;
            }
        }
    }

    //untuk membuat tombolnya
    private static JButton createSelectionButton(String planeType, ImageIcon icon) {
        JButton btn = new JButton(planeType, icon);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(70, 70, 70));
        btn.setFocusPainted(false);
        
        btn.addActionListener(e -> {
            switch (planeType) {
                case "Fighter": pesawat = new Fighter(); break;
                case "Stinger": pesawat = new Stinger(); break;
                case "Goliath": pesawat = new Goliath(); break;
            }
        });
        
        return btn;
    }

    private static void checkCollisions() {
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        for (Enemy enemy : Enemy.enemies) {
            for (Bullet bullet : Bullet.bullets) {
                if (CheckCollision(enemy, bullet)) {
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

        Bullet.bullets.removeAll(bulletsToRemove);
        Enemy.enemies.removeAll(enemiesToRemove);
    }

    private static void spawnEnemy() {
        Enemy enemy = new Enemy();
        enemy.start();
        Window.addComponent(enemy, 2);
    }

    private static ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public static boolean CheckCollision(JLabel a, JLabel b) {
        return a.getBounds().intersects(b.getBounds());
    }
}
