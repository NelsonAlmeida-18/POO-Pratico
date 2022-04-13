package src;

/**
 * A classe SmartDevice é um contactor simples.
 * Permite ligar ou desligar circuitos. 
 *
 */
public class SmartDevice {

    private final String id;
    private boolean on;
    private float consumo;

    /**
     * Constructor for objects of class SmartDevice
     */
    public SmartDevice() {
        this.id = "";
        this.on = false;
        this.consumo = 0;
    }

    public SmartDevice(String s) {
        this.id = s;
        this.on = false;
        this.consumo = 0;
    }

    public SmartDevice(String s, boolean b) {
        this.id = s;
        this.on = b;
        this.consumo = 0;
    }

    public void turnOn() {
        this.on = true;
    }
    
    public void turnOff() {
        this.on = false;
    }
    
    public boolean getOn() {return this.on;}
    
    public void setOn(boolean b) {this.on = b;}
    
    public String getID() {return this.id;}

    public void setConsumo(float consumo){ this.consumo = consumo; }

    public float getConsumo(){ return this.consumo; }

}
