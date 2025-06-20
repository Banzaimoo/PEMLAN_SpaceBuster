import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main {
    static Window window = new Window();
    static Pesawat pesawat;
    
    // Sistem Difficulty
    private static long gameStartTime;
    private static int currentDifficulty = 1;
    private static final int MAX_DIFFICULTY = 5;
    private static final long DIFFICULTY_INTERVAL = 30000; // 30 seconds
    
    // Sistem Skor
    private static int score = 0;
    private static final int SCORE_PER_SECOND = 5;
    private static final int SCORE_PER_KILL = 100;
    private static long lastScoreUpdate = 0;
    
    // Sistem Nyawa
    private static int health = 3;
    private static boolean gameActive = true;

    public static void main(String[] args) {
        window.setVisible(true);
        setupPlaneSelection();
    }

    private static void setupPlaneSelection() {
        // untuk panel grid option pemilihan pesawat
        JPanel selectionPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        selectionPanel.setBounds(300, 250, 400, 200);
        selectionPanel.setOpaque(false);

        // untuk gambar option pesawat
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

        gameStartTime = System.currentTimeMillis();
        lastScoreUpdate = gameStartTime;
        long lastUpdate = System.currentTimeMillis();
        long lastEnemySpawn = System.currentTimeMillis();
        int spawnInterval = 1000; 

        while (gameActive) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - gameStartTime;
            
            // Nambahin Difficulty
            currentDifficulty = 1 + (int)(elapsedTime / DIFFICULTY_INTERVAL);
            if (currentDifficulty > MAX_DIFFICULTY) {
                currentDifficulty = MAX_DIFFICULTY;
            }
            
            // Tambahan Skor per detik
            if (currentTime - lastScoreUpdate >= 1000) {
                score += SCORE_PER_SECOND;
                Window.scoreLabel.setText("Score: " + score);
                lastScoreUpdate = currentTime;
            }
            
            // Mengatur banyak munculnya musuh
            spawnInterval = Math.max(200, 1000 - (currentDifficulty * 150));
            
            if (currentTime - lastUpdate >= 16) {
                checkCollisions();
                checkEnemyReachedBottom();
                
                if (currentTime - lastEnemySpawn >= spawnInterval) {
                    spawnEnemies(currentDifficulty);
                    lastEnemySpawn = currentTime;
                }
                
                lastUpdate = currentTime;
            }
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void checkEnemyReachedBottom() {
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        
        for (Enemy enemy : Enemy.enemies) {
            if (enemy.getY() + enemy.getHeight() >= 768) { // Cek kalau sudah sampai bawah
                loseHealth();
                Window.removeComponent(enemy);
                enemiesToRemove.add(enemy);
            }
        }
        
        Enemy.enemies.removeAll(enemiesToRemove);
    }

    private static void loseHealth() {
        if (health > 0) {
            health--;
            Window.removeHealthIcon(health);
            
            if (health <= 0) {
                endGame();
            }
        }
    }

    private static void endGame() {
        gameActive = false;
        Window.showGameOver(score);
        pesawat.isDead = true;
        
        Window.mainPanel.removeKeyListener(pesawat);
        
        // Menghilangkan Musuh jika selesai
        for (Enemy enemy : new ArrayList<>(Enemy.enemies)) {
            Window.removeComponent(enemy);
        }
        Enemy.enemies.clear();
        
        // Menghilangkan peluru
        for (Bullet bullet : new ArrayList<>(Bullet.bullets)) {
            Window.removeComponent(bullet);
        }
        Bullet.bullets.clear();
    }

    private static void checkCollisions() {
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        for (Enemy enemy : Enemy.enemies) {
            // Game berakhir jika menabrak musuh
            if (CheckCollision(enemy, pesawat)) {
                instantGameOver();
                return; 
            }
            
            for (Bullet bullet : Bullet.bullets) {
                if (CheckCollision(enemy, bullet)) {
                    score += SCORE_PER_KILL;
                    Window.scoreLabel.setText("Score: " + score);
                    
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

    private static void instantGameOver() {
        for (int i = 0; i < Window.healthIcons.size(); i++) {
            Window.mainPanel.remove(Window.healthIcons.get(i));
        }
        Window.mainPanel.repaint();
        
        health = 0; 
        endGame(); 
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

    private static void spawnEnemies(int count) {
        for (int i = 0; i < count; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            Enemy enemy = new Enemy();
            enemy.speed = 2 + (currentDifficulty / 2);
            enemy.start();
            Window.addComponent(enemy, 2);
        }
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
