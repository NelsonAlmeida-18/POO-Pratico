package src;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

/**
 * A SmartHouse faz a gestão dos SmartDevices que existem e dos
 * espaços (as salas) que existem na casa.
 *
 */
public class SmartHouse {
   
    private String morada;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices

    /**
     * Constructor for objects of class SmartHouse
     */
    public SmartHouse() {
        // initialise instance variables
        this.morada = "";
        this.devices = new HashMap();
        this.locations = new HashMap();
    }

    public SmartHouse(String morada) {
        // initialise instance variables
        this.morada = morada;
        this.devices = new HashMap();
        this.locations = new HashMap();
    }

    
    public void setDeviceOn(String devCode) {
        this.devices.get(devCode).turnOn();
    }
    
    public boolean existsDevice(String id) {return false;}
    
    public void addDevice(SmartDevice s) {}
    
    public SmartDevice getDevice(String s) {return null;}
    
    public void setOn(String s, boolean b) {}
    
    public void setAllOn(boolean b) {}
    
    public void addRoom(String s) {}
    
    public boolean hasRoom(String s) {return false;}
    
    public void addToRoom (String s1, String s2) {}
    
    public boolean roomHasDevice (String s1, String s2) {return false;}
    
}
