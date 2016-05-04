import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CoE {
    private String[] elements;
    private ScheduledExecutorService executor;
    private int i = 0;

    public CoE(String[] elements) {
        this.elements = elements;
    }

    public void start() {
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);
    }

    public void stop() {
        executor.shutdown();
    }

    Runnable helloRunnable = new Runnable() {
        public void run() {
            System.out.println("Current Element: "+elements[i]);
            i++;
            if(i == elements.length) i=0;
        }
    };
}
