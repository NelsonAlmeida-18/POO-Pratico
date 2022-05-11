//package src;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * A classe SmartDevice é um construtor simples.
 * Permite ligar ou desligar circuitos. 
 *
 */
public abstract class SmartDevice implements Serializable{
/* 
    public String getID() {return this.id;}

    public void setID(String id){this.id=id;}

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

    // public SmartDevice clone(){
    //    SmartDevice sc = new SmartDevice();
    //} */

    public abstract int getID(); //para definir nas subclasses

    public abstract void goToData(LocalDate data);

    public abstract double getConsumo();

    public abstract double getConsumo(LocalDate data);

    public abstract void turnOn();

    public abstract void turnOff();

    public abstract SmartDevice clone();

    public abstract String toString();

}
