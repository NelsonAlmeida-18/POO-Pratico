package src;

import java.time.LocalDateTime;
import java.lang.StringBuilder;


/**
 * Uma SmartBulb é uma lâmpada inteligente que além de ligar e desligar (já que
 * é subclasse de SmartDevice) também permite escolher a intensidade da iluminação 
 * (a cor da mesma).
 *
 */
public class SmartBulb extends SmartDevice {
    public static final int WARM = 1;
    public static final int NEUTRAL = 0;
    public static final int COLD = -1;

    private int tone;
    private LocalDateTime ultimaVezLigado;
    private String dimensions;

    /**
     * Constructor for objects of class SmartBulb
     */
    public SmartBulb() {
        // initialise instance variables
        super();
        this.tone = NEUTRAL;
    }

    public SmartBulb(String id, int tone) {
        // initialise instance variables
        super(id);
        this.tone = tone;
    }

    public SmartBulb(String id) {
        // initialise instance variables
        super(id);
        this.tone = NEUTRAL;
    }

    public void setTone(int t) { // modificar para consumo futuramente
        if (t>=WARM) this.tone = WARM;
        else if (t<=COLD) this.tone = COLD;
        else this.tone = t;
    }

    public int getTone() { return this.tone; }

    public void setUltimaVezLigado(LocalDateTime ult){this.ultimaVezLigado=ult;}

    public LocalDateTime getUltimaVezLigado(){
        return this.ultimaVezLigado;
    }

    public void setDimensions(String dimensions){this.dimensions=dimensions;}

    public String getDimensions(){return this.dimensions;}

    public void turnON(){

    }

    public boolean equals(Object obj){
        if (this==obj)
            return true;

        if (this.getClass()!=obj.getClass() || obj==null)
            return false;

        SmartBulb lampada = (SmartBulb) obj;
        return (this.tone==lampada.getTone() &&
                this.dimensions==lampada.getDimensions() &&
                this.ultimaVezLigado.equals(lampada.getUltimaVezLigado()));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Estado da lâmpada: ");
        sb.append(this.tone);
        sb.append("\nModo de Funcionamento da lâmpada: ");
        //sb.append(super(getOn())); // help on this
        sb.append("\nDimensões da lâmpada: ");
        sb.append(this.dimensions);
        sb.append("\nÚltima vez ligada: ");
        sb.append(this.ultimaVezLigado.toString());
        sb.append("\n");
        return sb.toString();
    }

<<<<<<< HEAD
    public float getConsumo() {
        return this.consumo;
=======
    public SmartBulb clone(){
        return new SmartBulb();
>>>>>>> 2432c4625ae74b14aac938965c6758ac2c2b7f9b
    }

}

