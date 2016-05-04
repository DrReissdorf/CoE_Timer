import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JPanel mainPanel;
    private JCheckBox arcaneCheckBox;
    private JCheckBox coldCheckBox;
    private JCheckBox fireCheckBox;
    private JCheckBox holyCheckBox;
    private JCheckBox lightningCheckBox;
    private JCheckBox physicalCheckBox;
    private JCheckBox poisonCheckBox;
    private JButton startButton;
    private JComboBox mainElementComboBox;
    private JTextField currentElementText;

    private CoE coe;
    private GUI thisGui;
    private Config config;
    private JCheckBox[] allCheckBoxes;

    private final String IMAGEFOLDER = "/resources/";
    private final String PROG_ICON_PATH = IMAGEFOLDER+"coe.png";

    private boolean isCoeRunning = false;

    public GUI(int guiSizeX, int guiSizeY) {
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();

        setIconImage(loadImageResource(PROG_ICON_PATH));

        thisGui = this;
        config = Config.getExemplar();


        initGuiProperties(guiSizeX,guiSizeY);
        initCheckBoxes();
        initMainElementComboBox();

        startButton.addActionListener(e -> {
            if(!isCoeRunning) {
                startCoE();
            } else {
                stopCoE();
            }
        });
        mainElementComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = String.valueOf( ((JComboBox)e.getSource()).getSelectedItem() );
                if(selectedItem != null) {
                    config.setMainElement(selectedItem);
                    config.writeCurrentConfig();
                }
            }
        });
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

    public String getMainElement() {
        return (String)mainElementComboBox.getSelectedItem();
    }

    private void initCheckBoxes() {
        allCheckBoxes = new JCheckBox[7];
        allCheckBoxes[0] = arcaneCheckBox;
        allCheckBoxes[1] = coldCheckBox;
        allCheckBoxes[2] = fireCheckBox;
        allCheckBoxes[3] = holyCheckBox;
        allCheckBoxes[4] = lightningCheckBox;
        allCheckBoxes[5] = physicalCheckBox;
        allCheckBoxes[6] = poisonCheckBox;

        for(int i=0 ; i<allCheckBoxes.length ; i++) {
            allCheckBoxes[i].addActionListener(new checkBoxListener());
        }

        arcaneCheckBox.setSelected(config.getArcane());
        coldCheckBox.setSelected(config.getCold());
        fireCheckBox.setSelected(config.getFire());
        holyCheckBox.setSelected(config.getHoly());
        lightningCheckBox.setSelected(config.getLightning());
        physicalCheckBox.setSelected(config.getPhysical());
        poisonCheckBox.setSelected(config.getPoison());
    }

    private void initMainElementComboBox() {
        mainElementComboBox.removeAllItems();
        for(int i=0 ; i<allCheckBoxes.length ; i++) {
            if(allCheckBoxes[i].isSelected()) {
                mainElementComboBox.addItem(allCheckBoxes[i].getText());
            }
        }

        mainElementComboBox.setSelectedItem(config.getMainElement());
    }

    public void startCoE() {
        isCoeRunning = true;
        startButton.setText("Stop");
        mainElementComboBox.setEnabled(false);
        for(int i=0 ; i<allCheckBoxes.length ; i++) allCheckBoxes[i].setEnabled(false);
        coe = new CoE(getElementsFromCheckboxes(),thisGui,"beep.mp3");
        coe.start();
    }

    public void stopCoE() {
        isCoeRunning = false;
        startButton.setText("Start");
        mainElementComboBox.setEnabled(true);
        coe.stop();
        for(int i=0 ; i<allCheckBoxes.length ; i++) allCheckBoxes[i].setEnabled(true);
    }

    public void setCurrentElementText(String text) {
        currentElementText.setText("Current Element: "+text);
    }

    private ArrayList<String> getElementsFromCheckboxes() {
        ArrayList<String> strings = new ArrayList<>();

        for(int i=0 ; i<allCheckBoxes.length ; i++) {
            if(allCheckBoxes[i].isSelected()) strings.add(allCheckBoxes[i].getText());
        }

        return strings;
    }

    private BufferedImage loadImageResource(String filePath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(getClass().getResource(filePath));
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class checkBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            initMainElementComboBox();

            switch (checkBox.getText()) {
                case "Arcane":
                    config.setArcane(checkBox.isSelected());
                    break;

                case "Cold":
                    config.setCold(checkBox.isSelected());
                    break;

                case "Fire":
                    config.setFire(checkBox.isSelected());
                    break;

                case "Holy":
                    config.setHoly(checkBox.isSelected());
                    break;

                case "Lightning":
                    config.setLightning(checkBox.isSelected());
                    break;

                case "Physical":
                    config.setPhysical(checkBox.isSelected());
                    break;

                case "Poison":
                    config.setPoison(checkBox.isSelected());
                    break;
            }

            config.writeCurrentConfig();
        }
    }

    public boolean isCoeRunning() {
        return isCoeRunning;
    }
}
