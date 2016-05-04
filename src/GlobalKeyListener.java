import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {
    private GUI gui;
    private final int firstKey = NativeKeyEvent.VC_MINUS;
    private final int secondKey = NativeKeyEvent.VC_KP_SUBTRACT;

    public GlobalKeyListener(GUI gui) {
        this.gui = gui;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        if(e.getKeyCode()==firstKey || e.getKeyCode()==secondKey) {
            if(!gui.isCoeRunning()) gui.startCoE();
            else gui.stopCoE();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {}
}