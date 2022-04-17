package src;
import java.time.LocalDateTime;
import java.lang.StringBuilder;

/**
 * A classe SmartDevice é um contactor simples.
 * Permite ligar ou desligar circuitos. 
 *
 */
public class SmartDevice{

    private final String id;
    private boolean on;
    private float consumo;
    private LocalDateTime ultimaVezLigado;

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

    public SmartDevice(SmartDevice sd){
        this.id = sd.getID();
        this.on = sd.getOn();
        this.consumo=sd.getConsumo();
        this.ultimaVezLigado=sd.getUltimaVezLigado();
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

    public LocalDateTime getUltimaVezLigado(){return this.ultimaVezLigado;}

    public void setConsumo(float consumo){ this.consumo = consumo; }

    public float getConsumo(){ return this.consumo; }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id do dispositivo: ");
        sb.append(this.id);
        sb.append("\nConsumo do dispositivo: ");
        sb.append(this.consumo);
        sb.append("\n");
        return sb.toString();
    }

    public SmartDevice clone(){
        return new SmartDevice(this);
    }


    //rever
    public boolean equals(Object obj){
        if (this==obj)
            return true;
        
        if (obj==null||obj.getClass()!=this.getClass())
            return false;
        
        SmartDevice newSD = (SmartDevice) obj;
        return (this.id.equals(newSD.getID())  &&  this.on==newSD.getOn()  &&  this.consumo==newSD.getConsumo());
    }

}
