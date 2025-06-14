

public class Main{

    //GLobal Variable
    static Window window = new Window();
    static Pesawat pesawat = new Pesawat();

    public static void main(String[] args) {
        Window.mainPanel.addKeyListener(pesawat);

        Window.addComponent(pesawat, 1);
        window.setVisible(true);

        long lastsec = System.currentTimeMillis();
        while(true){
            long millis = System.currentTimeMillis();
            //Constant 62,5 fps
            while(millis - lastsec >= 16f){
                if(pesawat.moveDir != 0){
                    pesawat.moveControl(pesawat.moveDir);
                }
                lastsec = millis;

                // GameLogic below
            }
            
        }
    }
}
