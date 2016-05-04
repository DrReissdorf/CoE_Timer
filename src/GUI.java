import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JPanel mainPanel;

    public GUI(int guiSizeX, int guiSizeY) {
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();

        //setIconImage(loadImageResource(IMAGE_PROTOBUFMICRO_PATH));

        initGuiProperties(guiSizeX,guiSizeY);
    }

    private void initGuiProperties(int guiSizeX, int guiSizeY) {
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Convention of Elements Timer");
        Dimension d = new Dimension();
        d.setSize(guiSizeX,guiSizeY);
        setMinimumSize(d);
        setVisible(true);
    }
}
