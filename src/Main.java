import javax.swing.*;

public class Main {
    public static void main(String args[]) throws InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        GUI gui = new GUI(400,200);

        String[] lol = {"Fire","Frost","Arcane","Lightning"};

        new CoE(lol).start();
    }
}
