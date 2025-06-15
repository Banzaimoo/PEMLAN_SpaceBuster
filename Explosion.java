import javax.swing.*;

public class Explosion extends JLabel implements Runnable {
    public Explosion(int x, int y) {
        this.setIcon(new ImageIcon("assets/hancur.png"));
        this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Window.removeComponent(this);
    }
}
