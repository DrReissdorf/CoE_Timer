import java.io.*;
import java.util.*;

public class Config {
    private static Config config;
    private final String filePath = "data.cfg";

    public HashMap<String, String> currentSettingsMap = new LinkedHashMap<>();
    public HashMap<String, String> standartSettingsMap = new LinkedHashMap<>();

    private Config() {
        initStandartSettingsMap();
        readConfigToMap();
        writeCurrentConfig();
    }

    /**
     * Diese Methode liefert den Singleton zurueck. Wurde er noch
     * nicht initialisiert, so wird er automatisch initialisiert.
     * @return Singleton Object, ClientConfig
     */
    public static Config getExemplar() {
        if(config == null) config = new Config();
        return config;
    }

    /**
     * Schreibt die aktuelle Einstellungen auf die Festplatte.
     * Dies geschieht mit Hilfe der writeConfig() Methode.
     */
    public void writeCurrentConfig() {
        writeConfig(currentSettingsMap);
    }

    /**
     * Diese Methode liest die aktuellen Einstellungen von der Festplatte und
     * schreibt diese in die zugehörige HashMap.
     * Existiert keine Einstellungs-Datei, so wird diese mit den
     * Standard-Einstellungen erzeugt (mit Hilfe der writeStandartConfig() Methode).
     */
    public void readConfigToMap() {
        BufferedReader br;
        File config = new File(filePath);
        ArrayList<String> standartKeys = new ArrayList<>(standartSettingsMap.keySet());
        ArrayList<String> currentKeys;
        String temp;
        int equalsPos;

        if(!config.exists()) writeStandartConfig();

        try {
            br = new BufferedReader(new FileReader(config));
            while((temp = br.readLine()) != null) {
                equalsPos = temp.indexOf("=");
                if(standartSettingsMap.containsKey(temp.substring(0,equalsPos))) currentSettingsMap.put(temp.substring(0,equalsPos),temp.substring(equalsPos+1,temp.length()));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentKeys = new ArrayList(currentSettingsMap.keySet());

        if(compareStringLists(standartKeys,currentKeys)) {
            return;
        }
        else {
            for(String s : standartKeys) {
                if(!currentSettingsMap.containsValue(s)) currentSettingsMap.put(s,standartSettingsMap.get(s));
            }
        }
    }

    /**
     * Diese Methode ueberprueft, ob zwei Listen(String) exakt gleich sind.
     * @param list1 Erste Liste.
     * @param list2 Zweite Liste.
     * @return Wenn die Listen gleich sind: True. Sonst False.
     */
    private boolean compareStringLists(List<String> list1, List<String> list2) {
        if(list1.size() != list2.size()) return false;
        for(int i=0 ; i<list1.size() ; i++) if(!list1.get(i).equals(list2.get(i))) return false;
        return true;
    }

    /**
     * Diese Methode schreibt die Standard-Einstellungen in eine Datei und speichert diese.
     */
    public void writeStandartConfig() {
        writeConfig(standartSettingsMap);
    }

    /**
     * Diese Methode nimmt die aktuellen Einstellungen und schreibt diese in eine Datei.
     * Diese Datei wird dann auf der Festplatte abgelegt.
     * @param map HashMap, die die zu speichernden Einstellungen liefert.
     */
    private void writeConfig(HashMap<String, String> map) {
        PrintWriter printWriter;
        ArrayList<String> valueList;
        File config = new File(filePath);

        if(config.exists()) config.delete();

        try {
            config.createNewFile();
            printWriter = new PrintWriter(config);
            valueList = new ArrayList<>(map.keySet());
            for(int i=0 ; i<valueList.size() ; i++) {
                printWriter.println(valueList.get(i)+"="+map.get(valueList.get(i)));
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Diese Methode bestimmt, welche Einstellungen existieren und setzt deren
     * Standard-Werte.
     */
    private void initStandartSettingsMap() {
        standartSettingsMap.put("arcane","false");
        standartSettingsMap.put("cold","false");
        standartSettingsMap.put("fire","false");
        standartSettingsMap.put("holy","false");
        standartSettingsMap.put("lightning","false");
        standartSettingsMap.put("physical","false");
        standartSettingsMap.put("poison","false");
        standartSettingsMap.put("mainElement","Arcane");
    }

    public boolean getArcane() {
        return Boolean.valueOf(currentSettingsMap.get("arcane"));
    }
    public void setArcane(boolean bool) {
        currentSettingsMap.put("arcane", String.valueOf(bool));
    }

    public boolean getCold() {
        return Boolean.valueOf(currentSettingsMap.get("cold"));
    }
    public void setCold(boolean bool) {
        currentSettingsMap.put("cold", String.valueOf(bool));
    }

    public boolean getFire() {
        return Boolean.valueOf(currentSettingsMap.get("fire"));
    }
    public void setFire(boolean bool) {
        currentSettingsMap.put("fire", String.valueOf(bool));
    }

    public boolean getHoly() {
        return Boolean.valueOf(currentSettingsMap.get("holy"));
    }
    public void setHoly(boolean bool) {
        currentSettingsMap.put("holy", String.valueOf(bool));
    }

    public boolean getLightning() {
        return Boolean.valueOf(currentSettingsMap.get("lightning"));
    }
    public void setLightning(boolean bool) {
        currentSettingsMap.put("lightning", String.valueOf(bool));
    }

    public boolean getPhysical() {
        return Boolean.valueOf(currentSettingsMap.get("physical"));
    }
    public void setPhysical(boolean bool) {
        currentSettingsMap.put("physical", String.valueOf(bool));
    }

    public boolean getPoison() {
        return Boolean.valueOf(currentSettingsMap.get("poison"));
    }
    public void setPoison(boolean bool) {
        currentSettingsMap.put("poison", String.valueOf(bool));
    }

    public void setMainElement(String mainElement) {
        currentSettingsMap.put("mainElement", mainElement);
    }

    public String getMainElement() {
        return currentSettingsMap.get("mainElement");
    }
}
