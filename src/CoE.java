import javax.swing.*;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CoE {
    private final int COE_ELEMENT_DURATION = 4;
    private MP3 mp3;
    private GUI gui;
    private ArrayList<String> elements;
    private ScheduledExecutorService executor;
    private int i = 0;

    public CoE(ArrayList<String> elements, GUI GUI, String pathToMp3) {
        this.elements = elements;
        this.gui = GUI;
        mp3 = new MP3(pathToMp3);
    }

    public void start() {
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(runnable, 0, COE_ELEMENT_DURATION, TimeUnit.SECONDS);
    }

    public void stop() {
        executor.shutdown();
    }

    Runnable runnable = new Runnable() {
        public void run() {
            System.out.println("Current Element: "+elements.get(i));
            System.out.println("Main Element: "+gui.getMainElement());
            gui.setCurrentElementText(elements.get(i));
            if(elements.get(i).equals(Config.getExemplar().getMainElement())) mp3.play();
            i++;
            if(i == elements.size()) i=0;
        }
    };
}
