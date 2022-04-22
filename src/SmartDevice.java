package src;
import java.lang.StringBuilder;

/**
 * A classe SmartDevice é um construtor simples.
 * Permite ligar ou desligar circuitos. 
 *
 */
public abstract class SmartDevice{

    private final String id;

    /**
     * Constructor for objects of class SmartDevice
     */
    public SmartDevice() {
        this.id = "";
    }

    public SmartDevice(String s) {
        this.id = s;
    }

    public SmartDevice(SmartDevice sd){
        this.id = sd.getID();
    }
    
    public String getID() {return this.id;}

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id do dispositivo: ");
        sb.append(this.id);
        sb.append("\n");
        return sb.toString();
    }

    // public SmartDevice clone(){
    //     return new SmartDevice(this);
    // }


    //rever
    public boolean equals(Object obj){
        if (this==obj)
            return true;
        
        if (obj==null||obj.getClass()!=this.getClass())
            return false;
        
        SmartDevice newSD = (SmartDevice) obj;

        //equals?? não seria também abstrato e compararia as subclasses?
        return (this.id.equals(newSD.getID()));
    }

    public abstract double getConsumo();

    public abstract void turnOn();

    public abstract void turnOff();


}
